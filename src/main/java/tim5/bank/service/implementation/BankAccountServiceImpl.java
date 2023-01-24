package tim5.bank.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import tim5.bank.dto.ExecutePaymentDto;
import tim5.bank.model.BankAccount;
import tim5.bank.repository.BankAccountRepository;
import tim5.bank.service.template.BankAccountService;

import java.util.List;
import java.util.Objects;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private Environment env;

    @Override
    public BankAccount create(BankAccount bankAccount) {
        return null;
    }

    @Override
    public BankAccount getById(Long id) {
        return bankAccountRepository.findById(id).orElse(null);
    }

    @Override
    public BankAccount getByPanNumber(String pan) {
        return bankAccountRepository.getByPanNumber(pan);
    }

    @Override
    public List<BankAccount> getAll() {
        return null;
    }

    @Override
    public BankAccount update(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public BankAccount delete(Long id) {
        return null;
    }

    @Override
    public boolean issuerBankSameAsAcquirer(String panNumber) {
        return panNumber.startsWith(Objects.requireNonNull(env.getProperty("bank.id.digits")));
    }

    @Override
    public boolean verifyInputData(ExecutePaymentDto executePaymentDto) {
        return bankAccountRepository.bankAccountValid(executePaymentDto.getCardHolderName(), executePaymentDto.getPAN(),
                executePaymentDto.getSecurityCode(), executePaymentDto.getValidUntil());
    }

    @Override
    public boolean reserveAmount(Long bankAccountId, double amount) {
        BankAccount bankAccount = getById(bankAccountId);
        if(bankAccount.reserveFunds(amount)){
            update(bankAccount);
            return true;
        }
        return false;
    }
}
