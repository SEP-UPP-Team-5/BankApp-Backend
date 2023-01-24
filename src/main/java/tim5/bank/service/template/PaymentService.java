package tim5.bank.service.template;

import tim5.bank.dto.CreatePaymentDto;
import tim5.bank.dto.UpdatePaymentDto;
import tim5.bank.model.Payment;

import java.util.List;

public interface PaymentService {

    Payment create(CreatePaymentDto createPaymentDto);
    Payment getById(Long id);
    List<Payment> getAll();
    Payment update(UpdatePaymentDto updatePaymentDto);
    Payment delete(Long id);

}
