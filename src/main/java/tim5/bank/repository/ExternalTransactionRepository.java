package tim5.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim5.bank.model.ExternalTransaction;

public interface ExternalTransactionRepository extends JpaRepository<ExternalTransaction, Long> {

}
