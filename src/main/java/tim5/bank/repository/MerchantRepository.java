package tim5.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tim5.bank.model.Merchant;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    @Query(value="SELECT * FROM merchant where merchant_id=:id and merchant_password=:password",nativeQuery = true)
    Merchant getMerchantByMerchantIdAndMerchantPassword(@Param("id")String merchantId, @Param("password")String merchantPassword);
    Merchant getMerchantByMerchantId(String merchantId);
}
