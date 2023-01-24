package tim5.bank.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim5.bank.dto.FundsReservationRequestDto;
import tim5.bank.model.FundsReservationRequest;
import tim5.bank.repository.FundsReservationRequestRepository;
import tim5.bank.service.template.BankAccountService;
import tim5.bank.service.template.FundsReservationRequestService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FundsReservationRequestServiceImpl implements FundsReservationRequestService {

    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private FundsReservationRequestRepository fundsReservationRequestRepository;

    @Override
    public FundsReservationRequest create(FundsReservationRequest fundsReservationRequest) {
        return fundsReservationRequestRepository.save(fundsReservationRequest);
    }

    @Override
    public FundsReservationRequest getById(FundsReservationRequest fundsReservationRequest) {
        return null;
    }

    @Override
    public List<FundsReservationRequest> getAll() {
        return null;
    }

    @Override
    public FundsReservationRequest update(FundsReservationRequest fundsReservationRequest) {
        return fundsReservationRequestRepository.save(fundsReservationRequest);
    }

    @Override
    public FundsReservationRequest delete(Long id) {
        return null;
    }

    @Override
    public FundsReservationRequest execute(FundsReservationRequestDto fundsReservationRequestDto) {
        FundsReservationRequest fundsReservationRequest = new FundsReservationRequest(null,
                fundsReservationRequestDto.getACQUIRER_ORDER_ID(), fundsReservationRequestDto.getACQUIRER_TIMESTAMP(),
                LocalDateTime.now(), bankAccountService.getByPanNumber(fundsReservationRequestDto.getPAN()),
                fundsReservationRequestDto.getAmount(), "CREATED");

        fundsReservationRequest.setStatus(bankAccountService.reserveAmount(fundsReservationRequestDto.getPAN(),
                fundsReservationRequestDto.getAmount()) ? "SUCCESS" : "FAILED");
        return create(fundsReservationRequest);

    }
}
