package tim5.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QRCodeInputDto {
    private double amount;
    private String currency;
    private String recipientAccountNumber;
    private String recipientName;

    private String pan;
    private String securityCode;
    private String cardHolderName;
    private LocalDateTime validUntil;
    private Long paymentId;
}
