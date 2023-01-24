package tim5.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim5.bank.model.FundsReservationRequest;

public interface FundsReservationRequestRepository extends JpaRepository<FundsReservationRequest, Long> {

}
