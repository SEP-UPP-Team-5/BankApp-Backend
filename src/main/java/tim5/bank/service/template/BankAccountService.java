package tim5.bank.service.template;

import org.springframework.stereotype.Service;
import tim5.bank.dto.ExecutePaymentDto;
import tim5.bank.dto.FundsReservationRequestDto;
import tim5.bank.model.BankAccount;

import java.util.List;

public interface BankAccountService {

    BankAccount create(BankAccount bankAccount);
    BankAccount getById(Long id);
    BankAccount getByPanNumber(String pan);
    List<BankAccount> getAll();
    BankAccount update(BankAccount bankAccount);
    void delete(Long id);
    boolean issuerBankSameAsAcquirer(String panNumber);
    boolean verifyInputData(ExecutePaymentDto executePaymentDto);
    boolean verifyInputData(FundsReservationRequestDto fundsReservationRequestDto);
    boolean reserveAmount(Long bankAccountId, double amount);
    boolean reserveAmount(String panNumber, double amount);
    String panNumberGenerator();
    String securityCodeGenerator();
}
