package tim5.bank.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tim5.bank.model.Merchant;
import tim5.bank.repository.MerchantRepository;
import tim5.bank.service.template.MerchantService;

import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    @Override
    public Merchant create(Merchant merchant) {
        return null;
    }

    @Override
    public Merchant getById(Long id) {
        return null;
    }

    @Override
    public Merchant getByMerchantId(String merchantId) {
        return merchantRepository.getMerchantByMerchantId(merchantId);
    }

    @Override
    public List<Merchant> getAll() {
        return null;
    }

    @Override
    public Merchant update(Merchant merchant) {
        return null;
    }

    @Override
    public Merchant delete(Long id) {
        return null;
    }

    @Override
    public boolean verifyIdAndPassword(String merchantId, String merchantPassword) {
        return merchantRepository.getMerchantByMerchantIdAndMerchantPassword(merchantId, passwordEncoder.encode(merchantPassword)) != null;
    }
}
