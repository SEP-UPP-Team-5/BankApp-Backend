package tim5.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim5.bank.model.InternalTransaction;

public interface InternalTransactionRepository extends JpaRepository<InternalTransaction, Long> {

}
