package tim5.bank.service.template;

import tim5.bank.model.Merchant;

import java.util.List;

public interface MerchantService {

    Merchant create(Merchant merchant);
    Merchant getById(Long id);
    List<Merchant> getAll();
    Merchant update(Merchant merchant);
    Merchant delete(Long id);
    boolean verifyIdAndPassword(String merchantId, String merchantPassword);
}
