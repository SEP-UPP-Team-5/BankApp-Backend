package tim5.bank.service.template;

import tim5.bank.model.Merchant;

import java.util.List;

public interface MerchantService {

    Merchant create(Merchant merchant);
    Merchant getById(Long id);
    Merchant getByMerchantId(String merchantId);
    List<Merchant> getAll();
    Merchant update(Merchant merchant);
    void delete(Long id);
    boolean verifyIdAndPassword(String merchantId, String merchantPassword);
}
