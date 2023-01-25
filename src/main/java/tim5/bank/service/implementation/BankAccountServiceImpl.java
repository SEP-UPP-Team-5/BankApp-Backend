package tim5.bank.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import tim5.bank.dto.ExecutePaymentDto;
import tim5.bank.dto.FundsReservationRequestDto;
import tim5.bank.model.BankAccount;
import tim5.bank.repository.BankAccountRepository;
import tim5.bank.service.template.BankAccountService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private Environment env;

    @Override
    public BankAccount create(BankAccount bankAccount) {
        String pan;
        String code;
        do {
            pan = panNumberGenerator();
            code = securityCodeGenerator();
        }
        while(bankAccountRepository.getByPanNumber(pan) != null || bankAccountRepository.getBySecurityCode(code) != null);

        bankAccount.setPanNumber(pan);
        bankAccount.setSecurityCode(code);
        bankAccount.setValidUntil(LocalDate.now().plusYears(2));
        bankAccount.setReservedFunds(0);
        return bankAccountRepository.save(bankAccount);
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
        return bankAccountRepository.findAll();
    }

    @Override
    public BankAccount update(BankAccount bankAccount) {
        BankAccount account = bankAccountRepository.findById(bankAccount.getId()).get();
        if(account == null)
            return null;
        bankAccount.setPanNumber(account.getPanNumber());
        bankAccount.setSecurityCode(account.getSecurityCode());
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public void delete(Long id) {
        bankAccountRepository.deleteById(id);
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
    public boolean verifyInputData(FundsReservationRequestDto fundsReservationRequestDto) {
        return bankAccountRepository.bankAccountValid(fundsReservationRequestDto.getCardHolderName(), fundsReservationRequestDto.getPAN(),
                fundsReservationRequestDto.getSecurityCode(), fundsReservationRequestDto.getValidUntil());
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

    @Override
    public boolean reserveAmount(String panNumber, double amount) {
        BankAccount bankAccount = getByPanNumber(panNumber);
        if(bankAccount.reserveFunds(amount)){
            update(bankAccount);
            return true;
        }
        return false;
    }

    @Override
    public String panNumberGenerator() {
        return env.getProperty("bank.id.digits") + numbGen();
    }

    @Override
    public String securityCodeGenerator() {
        return String.format("%04d", new Random().nextInt(10000));
    }

    public static long numbGen() {
        while (true) {
            long numb = (long)(Math.random() * 100000000 * 1000000);
            if (String.valueOf(numb).length() == 13)
                return numb;
        }
    }
}
