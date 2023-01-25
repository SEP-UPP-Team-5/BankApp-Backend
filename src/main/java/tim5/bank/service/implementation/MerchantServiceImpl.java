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
        String merchantId;
        do {
            merchantId = getAlphaNumericString(30);
        } while (merchantRepository.getMerchantByMerchantId(merchantId) != null);
        
        merchant.setMerchantId(merchantId);
        merchant.setMerchantPassword(getAlphaNumericString(100));
        return merchantRepository.save(merchant);
    }

    @Override
    public Merchant getById(Long id) {
        return merchantRepository.findById(id).orElse(null);
    }

    @Override
    public Merchant getByMerchantId(String merchantId) {
        return merchantRepository.getMerchantByMerchantId(merchantId);
    }

    @Override
    public List<Merchant> getAll() {
        return merchantRepository.findAll();
    }

    @Override
    public Merchant update(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    @Override
    public void delete(Long id) {
         merchantRepository.deleteById(id);
    }

    @Override
    public boolean verifyIdAndPassword(String merchantId, String merchantPassword) {
        return merchantRepository.getMerchantByMerchantIdAndMerchantPassword(merchantId, passwordEncoder.encode(merchantPassword)) != null;
    }

    static String getAlphaNumericString(int n)
    {

        // choose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
