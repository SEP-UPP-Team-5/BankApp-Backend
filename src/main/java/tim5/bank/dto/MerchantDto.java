package tim5.bank.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDto {
    private Long id;
    private String merchantId;
    private String merchantPassword;
    private double balance;
    @NotEmpty(message = "Reserved funds cannot be empty!")
    private double reservedFunds;
}
