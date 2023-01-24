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
public class ExecutePaymentDto {

    private String PAN;
    private String securityCode;
    private String cardHolderName;
    private LocalDateTime validUntil;
    private Long paymentId;

}
