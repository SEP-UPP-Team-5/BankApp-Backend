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
public class CreatePaymentDto {

    private String MERCHANT_ID;
    private String MERCHANT_PASSWORD;
    private double AMOUNT;
    private Long MERCHANT_ORDER_ID;
    private LocalDateTime MERCHANT_TIMESTAMP;
    private String SUCCESS_URL;
    private String FAILED_URL;
    private String ERROR_URL;

}
