package tim5.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tim5.bank.dto.CreatePaymentResponseDto;
import tim5.bank.dto.FundsReservationRequestDto;
import tim5.bank.dto.FundsReservationResponseDto;
import tim5.bank.model.FundsReservationRequest;
import tim5.bank.model.Payment;
import tim5.bank.service.template.BankAccountService;
import tim5.bank.service.template.FundsReservationRequestService;

@RestController
@RequestMapping(value = "/fundsReservation", produces = MediaType.APPLICATION_JSON_VALUE)
public class FundsReservationController {

    @Autowired
    private FundsReservationRequestService fundsReservationRequestService;
    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping()
    public ResponseEntity<FundsReservationResponseDto> reserve(@RequestBody FundsReservationRequestDto fundsReservationRequestDto){
        FundsReservationResponseDto fundsReservationResponseDto =
                new FundsReservationResponseDto(fundsReservationRequestDto.getACQUIRER_ORDER_ID(),
                        fundsReservationRequestDto.getACQUIRER_TIMESTAMP(),null, null,
                        "ERROR");
        if(bankAccountService.verifyInputData(fundsReservationRequestDto)){
            FundsReservationRequest fundsReservationRequest = fundsReservationRequestService.execute(fundsReservationRequestDto);
            fundsReservationResponseDto.setISSUER_ORDER_ID(fundsReservationRequest.getId());
            fundsReservationResponseDto.setISSUER_TIMESTAMP(fundsReservationRequest.getIssuerTimestamp());
            fundsReservationResponseDto.setSTATUS(fundsReservationRequest.getStatus());
            return new ResponseEntity<>(fundsReservationResponseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(fundsReservationResponseDto, HttpStatus.BAD_REQUEST);
        }
    }

}
