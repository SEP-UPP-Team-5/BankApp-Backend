package tim5.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim5.bank.model.Merchant;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {

}
