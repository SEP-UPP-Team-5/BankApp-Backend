package tim5.bank.service.implementation;

import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tim5.bank.dto.*;
import tim5.bank.model.*;
import tim5.bank.repository.PaymentRepository;
import tim5.bank.service.template.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private InternalTransactionService internalTransactionService;
    @Autowired
    private ExternalTransactionService externalTransactionService;
    @Autowired
    private PaymentRepository paymentRepository;

    private final String ERROR_URL = ""; //TODO:

    @Override
    public Payment create(CreatePaymentDto createPaymentDto) {
        if(!merchantService.verifyIdAndPassword(createPaymentDto.getMERCHANT_ID(), createPaymentDto.getMERCHANT_PASSWORD()))
            return null;

        Merchant merchant = merchantService.getByMerchantId(createPaymentDto.getMERCHANT_ID());
        return paymentRepository.save(new Payment(null, merchant, createPaymentDto.getAMOUNT(), createPaymentDto.getMERCHANT_ORDER_ID(),
                createPaymentDto.getMERCHANT_TIMESTAMP(), createPaymentDto.getSUCCESS_URL(),
                createPaymentDto.getFAILED_URL(), createPaymentDto.getERROR_URL(), "CREATED"));
    }

    @Override
    public Payment getById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Payment> getAll() {
        return null;
    }

    @Override
    public Payment update(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment delete(Long id) {
        return null;
    }

    @Override
    public String execute(ExecutePaymentDto executePaymentDto) {
        Payment payment = getById(executePaymentDto.getPaymentId());
        if(payment==null)
            return ERROR_URL;

        if(bankAccountService.issuerBankSameAsAcquirer(executePaymentDto.getPAN())){
            return executeInternalTransaction(executePaymentDto, payment);
        }else{
            return executeExternalTransaction(executePaymentDto, payment);
        }
    }

    private String executeInternalTransaction(ExecutePaymentDto executePaymentDto, Payment payment) {
        // check if bank account and payment id are valid
        if(!bankAccountService.verifyInputData(executePaymentDto)) {
            updateStatus(payment, null, null, "ERROR");
            return payment.getErrorUrl();
        }
        BankAccount bankAccount = bankAccountService.getByPanNumber(executePaymentDto.getPAN());
        // create internal transaction
        InternalTransaction internalTransaction = internalTransactionService.create(
                new InternalTransaction(null, LocalDateTime.now(), bankAccount, payment));
        // try reserving amount in payment
        if(bankAccountService.reserveAmount(bankAccount.getId(), payment.getAmount())){
            updateStatus(payment, internalTransaction.getId(), internalTransaction.getAcquirerTimestamp(), "SUCCESS");
            return payment.getSuccessUrl();
        }else{
            updateStatus(payment, internalTransaction.getId(), internalTransaction.getAcquirerTimestamp(), "FAILED");
            return payment.getFailedUrl();
        }
    }

    private void updateStatus(Payment payment, Long acquirerOrderId, LocalDateTime acquirerTimestamp, String status) {
        updatePaymentStatus(payment.getId(), status);
        sendUpdateToPsp(payment.getMerchantOrderId(), acquirerOrderId,
                acquirerTimestamp, payment.getId(), status);
    }

    private void updatePaymentStatus(Long paymentId, String status) {
        Payment payment = getById(paymentId);
        payment.setStatus(status);
        update(payment);
    }

    private void sendUpdateToPsp(Long merchantOrderId, Long acquirerOrderId, LocalDateTime acquirerTimestamp, Long paymentId, String status) {
        String pspUrl = "http://localhost:8082/orders/create"; // TODO: change to take PSP url from app properties probably
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject obj = new JSONObject();
        try {
            obj.put("MERCHANT_ORDER_ID", merchantOrderId);
            obj.put("ACQUIRER_ORDER_ID", acquirerOrderId);
            obj.put("ACQUIRER_TIMESTAMP", acquirerTimestamp);
            obj.put("PAYMENT_ID", paymentId);
            obj.put("STATUS", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpEntity<String> request = new HttpEntity<>(obj.toString(), headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(pspUrl, request, TransactionPspResponse.class);
    }

    private String executeExternalTransaction(ExecutePaymentDto executePaymentDto, Payment payment) {
        // generate ACQUIRER_ORDER_ID and ACQUIRER_TIMESTAMP
        ExternalTransaction externalTransaction = serializeNewExternalTransaction(executePaymentDto, payment);
        ExternalTransactionPccResponse pccResponse = sendTransactionInfoToPcc(executePaymentDto, externalTransaction);
        // check response from pcc
        if (checkExternalTransactionPccResponse(externalTransaction, pccResponse)){
            updateStatus(payment, null, null, "ERROR");
            return payment.getErrorUrl();
        }
        // update object with data from pcc
        updateExternalTransactionWithDataFromExternalTransactionPccResponse(externalTransaction, pccResponse);
        // handle response cases from response
        return handleExternalTransactionPccResponseStatus(payment, pccResponse);
    }

    private ExternalTransaction serializeNewExternalTransaction(ExecutePaymentDto executePaymentDto, Payment payment) {
        return externalTransactionService.create(new ExternalTransaction(null, LocalDateTime.now(), null, null,
                executePaymentDto.getPAN(), executePaymentDto.getSecurityCode(),
                executePaymentDto.getCardHolderName(), executePaymentDto.getValidUntil(), payment));
    }

    private static ExternalTransactionPccResponse sendTransactionInfoToPcc(ExecutePaymentDto executePaymentDto, ExternalTransaction externalTransaction) {
        // send it along form info to pcc
        String pccUrl = "http://localhost:8082/orders/create"; // TODO: change to take from app properties probably
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject obj = new JSONObject();
        try {
            obj.put("ACQUIRER_ORDER_ID", externalTransaction.getId());
            obj.put("ACQUIRER_TIMESTAMP", externalTransaction.getAcquirerTimestamp());
            obj.put("PAN", executePaymentDto.getPAN());
            obj.put("securityCode", executePaymentDto.getSecurityCode());
            obj.put("cardHolderName", executePaymentDto.getCardHolderName());
            obj.put("validUntil", executePaymentDto.getValidUntil());
            obj.put("amount", externalTransaction.getPayment().getAmount());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpEntity<String> request = new HttpEntity<>(obj.toString(), headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(pccUrl, request, ExternalTransactionPccResponse.class);
    }

    private boolean checkExternalTransactionPccResponse(ExternalTransaction externalTransaction, ExternalTransactionPccResponse pccResponse) {
        if(checkPccResponseFields(pccResponse) || pccResponse.getSTATUS().equals("ERROR"))
            return true;

        if(!externalTransaction.getId().equals(pccResponse.getACQUIRER_ORDER_ID()) || externalTransaction.getAcquirerTimestamp() != pccResponse.getACQUIRER_TIMESTAMP())
            return true;
        return false;
    }

    private boolean checkPccResponseFields(ExternalTransactionPccResponse pccResponse) {
        return pccResponse.getISSUER_ORDER_ID() == null ||
                pccResponse.getISSUER_TIMESTAMP() == null ||
                pccResponse.getACQUIRER_ORDER_ID() == null ||
                pccResponse.getACQUIRER_TIMESTAMP() == null ||
                pccResponse.getSTATUS() == null;
    }

    private String handleExternalTransactionPccResponseStatus(Payment payment, ExternalTransactionPccResponse pccResponse) {
        if(pccResponse.getSTATUS().equals("SUCCESS")) {
            updateStatus(payment, pccResponse.getACQUIRER_ORDER_ID(), pccResponse.getACQUIRER_TIMESTAMP(), "SUCCESS");
            return payment.getSuccessUrl();
        }else{
            updateStatus(payment, pccResponse.getACQUIRER_ORDER_ID(), pccResponse.getACQUIRER_TIMESTAMP(), "FAILED");
            return payment.getFailedUrl();
        }
    }

    private void updateExternalTransactionWithDataFromExternalTransactionPccResponse(ExternalTransaction externalTransaction, ExternalTransactionPccResponse pccResponse) {
        externalTransaction.setIssuerOrderId(pccResponse.getISSUER_ORDER_ID());
        externalTransaction.setIssuerTimestamp(pccResponse.getISSUER_TIMESTAMP());
        externalTransactionService.update(externalTransaction);
    }

}
