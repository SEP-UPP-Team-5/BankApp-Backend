package tim5.bank.controller;

import com.google.zxing.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tim5.bank.dto.CreatePaymentDto;
import tim5.bank.dto.CreatePaymentResponseDto;
import tim5.bank.dto.ExecutePaymentDto;
import tim5.bank.dto.ExecutePaymentResponseDto;
import tim5.bank.model.Payment;
import tim5.bank.service.template.PaymentService;
import tim5.bank.service.template.QRCodeService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(value = "/payment", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private QRCodeService qrCodeService;

    @PostMapping
    public ResponseEntity<CreatePaymentResponseDto> createPayment(@RequestBody CreatePaymentDto createPaymentDto){
        Payment payment = paymentService.create(createPaymentDto);
        System.out.println(LocalDateTime.now().toString());
        if(payment == null){
            return new ResponseEntity<>((CreatePaymentResponseDto) null, HttpStatus.BAD_REQUEST);
        }else{
            CreatePaymentResponseDto createPaymentResponseDto =
                    new CreatePaymentResponseDto("ZAVISI OD FRONTA", payment.getId());
            return new ResponseEntity<>(createPaymentResponseDto, HttpStatus.OK);
        }
    }

    @PostMapping("/execute")
    public ResponseEntity<ExecutePaymentResponseDto> executePayment(@RequestBody ExecutePaymentDto executePaymentDto){
        ExecutePaymentResponseDto executePaymentResponseDto = new ExecutePaymentResponseDto(paymentService.execute(executePaymentDto));
        return new ResponseEntity<>(executePaymentResponseDto, HttpStatus.OK);
    }

    @PostMapping("/execute/qrPayment")
    public ResponseEntity<ExecutePaymentResponseDto> executeQRPayment(@RequestParam("image") MultipartFile file) throws IOException, NotFoundException {
        String data = qrCodeService.validateQR(file);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(data.split("\n")[7].replace("T", " "), formatter);

        ExecutePaymentDto dto = new ExecutePaymentDto(data.split("\n")[6], data.split("\n")[5], data.split("\n")[4], dateTime, Long.valueOf(Long.parseLong(data.split("\n")[8])));

        ExecutePaymentResponseDto executePaymentResponseDto = new ExecutePaymentResponseDto(paymentService.execute(dto));
        return new ResponseEntity<>( executePaymentResponseDto, HttpStatus.OK);
    }

}
