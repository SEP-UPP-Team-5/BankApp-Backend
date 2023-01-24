package tim5.bank.service.template;

import tim5.bank.dto.FundsReservationRequestDto;
import tim5.bank.model.FundsReservationRequest;

import java.util.List;

public interface FundsReservationRequestService {

    FundsReservationRequest create(FundsReservationRequest fundsReservationRequest);
    FundsReservationRequest getById(FundsReservationRequest fundsReservationRequest);
    List<FundsReservationRequest> getAll();
    FundsReservationRequest update(FundsReservationRequest fundsReservationRequest);
    FundsReservationRequest delete(Long id);
    FundsReservationRequest execute(FundsReservationRequestDto fundsReservationRequestDto);
}
