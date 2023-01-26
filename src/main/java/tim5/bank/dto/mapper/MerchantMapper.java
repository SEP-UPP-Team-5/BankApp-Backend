package tim5.bank.dto.mapper;

import tim5.bank.dto.MerchantDto;
import tim5.bank.model.Merchant;

public class MerchantMapper {
    public Merchant DtoToMerchant(MerchantDto dto){
        return new Merchant(dto.getId(), dto.getMerchantId(), dto.getMerchantPassword(), dto.getBalance(), dto.getReservedFunds());
    }

    public MerchantDto MerchantToDto(Merchant merchant){
        return new MerchantDto(merchant.getId(), merchant.getMerchantId(), merchant.getMerchantPassword(), merchant.getBalance(), merchant.getReservedFunds());
    }
}
