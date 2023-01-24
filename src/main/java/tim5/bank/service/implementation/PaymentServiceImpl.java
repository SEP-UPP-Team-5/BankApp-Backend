package tim5.bank.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim5.bank.dto.CreatePaymentDto;
import tim5.bank.dto.ExecutePaymentDto;
import tim5.bank.dto.UpdatePaymentDto;
import tim5.bank.model.BankAccount;
import tim5.bank.model.InternalTransaction;
import tim5.bank.model.Merchant;
import tim5.bank.model.Payment;
import tim5.bank.repository.PaymentRepository;
import tim5.bank.service.template.BankAccountService;
import tim5.bank.service.template.InternalTransactionService;
import tim5.bank.service.template.MerchantService;
import tim5.bank.service.template.PaymentService;

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
    public Payment update(UpdatePaymentDto updatePaymentDto) {
        return null;
    }

    @Override
    public Payment delete(Long id) {
        return null;
    }

    @Override
    public String execute(ExecutePaymentDto executePaymentDto) {
        if(bankAccountService.issuerBankSameAsAcquirer(executePaymentDto.getPAN())){
            // check if bank account and payment id are valid
            Payment payment = getById(executePaymentDto.getPaymentId());
            if(payment==null)
                return ERROR_URL;
            if(!bankAccountService.verifyInputData(executePaymentDto))
                return payment.getErrorUrl();
            BankAccount bankAccount = bankAccountService.getByPanNumber(executePaymentDto.getPAN());
            // create internal transaction
            internalTransactionService.create(new InternalTransaction(null, bankAccount, payment));
            // try reserving amount in payment
            if(bankAccountService.reserveAmount(bankAccount.getId(), payment.getAmount())){
                return payment.getSuccessUrl();
            }else{
                return payment.getFailedUrl();
            }
        }else{
            //external transaction case
            // generate ACQUIRER_ORDER_ID and ACQUIRER_TIMESTAMP

            // send it along form info to pcc

            // check response from pcc
            // handle response cases from response
            return "";
        }
    }
}
