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

    private String merchant_id;
    private String merchant_password;
    private double amount;
    private Long merchant_order_id;
    private LocalDateTime merchant_timestamp;
    private String success_url;
    private String failed_url;
    private String error_url;

}
