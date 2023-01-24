package tim5.bank.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim5.bank.dto.CreatePaymentDto;
import tim5.bank.dto.UpdatePaymentDto;
import tim5.bank.model.Merchant;
import tim5.bank.model.Payment;
import tim5.bank.repository.PaymentRepository;
import tim5.bank.service.template.MerchantService;
import tim5.bank.service.template.PaymentService;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    MerchantService merchantService;
    @Autowired
    private PaymentRepository paymentRepository;

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
        return null;
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
}
