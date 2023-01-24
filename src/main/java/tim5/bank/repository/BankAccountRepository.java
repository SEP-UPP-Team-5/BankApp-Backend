package tim5.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim5.bank.model.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

}
