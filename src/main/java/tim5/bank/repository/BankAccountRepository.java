package tim5.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tim5.bank.model.BankAccount;

import java.time.LocalDateTime;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    @Query(value="SELECT CASE WHEN  COUNT(ba) > 0 THEN true ELSE false END FROM bank_account ba where card_holder_name=:name and pan_number=:pan " +
            "and security_code=:code and valid_until=:date)",nativeQuery = true)
    boolean bankAccountValid(@Param("name")String cardHolderName,@Param("pan")String panNumber,
                             @Param("code") String securityCode,  @Param("date") LocalDateTime date);

    @Query(value="SELECT * FROM bank_account where pan_number=:pan",nativeQuery = true)
    BankAccount getByPanNumber(@Param("pan")String panNumber);

    @Query(value="SELECT * FROM bank_account where security_code=:code",nativeQuery = true)
    BankAccount getBySecurityCode(@Param("code")String securityCode);

}
