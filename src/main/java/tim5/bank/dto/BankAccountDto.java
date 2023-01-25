package tim5.bank.dto;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {
    private Long id;
    private String panNumber;
    private String securityCode;
    @NotEmpty(message = "Card holder name cannot be null or empty!")
    private String cardHolderName;
    private LocalDate validUntil;
    @NotEmpty(message = "Balance cannot be null or empty!")
    private double balance;
    private double reservedFunds;
}
