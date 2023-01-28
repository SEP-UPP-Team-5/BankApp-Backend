package tim5.bank.controller;

import com.google.zxing.EncodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tim5.bank.dto.ExecutePaymentDto;
import tim5.bank.dto.ExecutePaymentResponseDto;
import tim5.bank.dto.QRCodeInputDto;
import tim5.bank.service.template.QRCodeService;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/qrCode", produces = MediaType.APPLICATION_JSON_VALUE)
public class QRCodeController {
    @Autowired
    private QRCodeService qrCodeService;

    @GetMapping(produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(@RequestBody QRCodeInputDto dto) throws WriterException, IOException {
        dto = new QRCodeInputDto(200, "USD", "123456789", "Aleksa Stojic",
                "1111254879865325", "326598", "Anja Pesic", LocalDateTime.of(2025, 6, 1, 0, 0), Long.valueOf(100));
        String data = dto.getRecipientName() + "\n"
                + dto.getRecipientAccountNumber() + "\n"
                + dto.getAmount() + "\n"
                + dto.getCurrency() + "\n"
                + dto.getCardHolderName() + "\n"
                + dto.getSecurityCode() + "\n"
                + dto.getPan() + "\n"
                + dto.getValidUntil() + "\n"
                + dto.getPaymentId();
        String path = "src/main/resources/image/demo.png";
        String charset = "UTF-8";

        Map<EncodeHintType, ErrorCorrectionLevel> hashMap
                = new HashMap<>();

        hashMap.put(EncodeHintType.ERROR_CORRECTION,
                ErrorCorrectionLevel.L);

        File file = qrCodeService.createQR(data, path, charset, hashMap, 200, 200);

        InputStream targetStream = new FileInputStream(file);
        byte[] bytes = StreamUtils.copyToByteArray(targetStream);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(bytes);
    }

    @PostMapping("/validator")
    public ResponseEntity<String> validateQRCode(@RequestParam("image") MultipartFile file) throws IOException, NotFoundException {
        String data = qrCodeService.validateQR(file);
        return new ResponseEntity<>( data, HttpStatus.OK);
    }

}
