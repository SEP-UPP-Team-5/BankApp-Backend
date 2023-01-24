package tim5.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim5.bank.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
}
