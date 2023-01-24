package tim5.bank.service.template;

import tim5.bank.model.BankAccount;

import java.util.List;

public interface BankAccountService {

    BankAccount create(BankAccount bankAccount);
    BankAccount getById(Long id);
    List<BankAccount> getAll();
    BankAccount update(BankAccount bankAccount);
    BankAccount delete(Long id);
    boolean issuerBankSameAsAcquirer(String panNumber);
}
