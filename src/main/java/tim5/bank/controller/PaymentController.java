package tim5.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tim5.bank.dto.CreatePaymentDto;
import tim5.bank.dto.CreatePaymentResponseDto;
import tim5.bank.model.Payment;
import tim5.bank.service.template.PaymentService;

@RestController
@RequestMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping()
    public ResponseEntity<CreatePaymentResponseDto> createPayment(@RequestBody CreatePaymentDto createPaymentDto){
        Payment payment = paymentService.create(createPaymentDto);
        if(payment == null){
            return new ResponseEntity<>((CreatePaymentResponseDto) null, HttpStatus.BAD_REQUEST);
        }else{
            CreatePaymentResponseDto createPaymentResponseDto =
                    new CreatePaymentResponseDto("ZAVISI OD FRONTA", payment.getId());
            return new ResponseEntity<>(createPaymentResponseDto, HttpStatus.OK);
        }
    }

}
