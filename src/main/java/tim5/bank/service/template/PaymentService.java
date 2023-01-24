package tim5.bank.service.template;

import tim5.bank.dto.CreatePaymentDto;
import tim5.bank.dto.ExecutePaymentDto;
import tim5.bank.dto.UpdatePaymentDto;
import tim5.bank.model.Payment;

import java.util.List;

public interface PaymentService {

    Payment create(CreatePaymentDto createPaymentDto);
    Payment getById(Long id);
    List<Payment> getAll();
    Payment update(Payment payment);
    Payment delete(Long id);
    String execute(ExecutePaymentDto executePaymentDto);
}
