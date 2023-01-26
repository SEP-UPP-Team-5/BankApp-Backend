package tim5.bank.dto.mapper;

import tim5.bank.dto.BankAccountDto;
import tim5.bank.model.BankAccount;

public class BankAccountMapper {
    public BankAccount DtoToBankAccount(BankAccountDto dto){
        return new BankAccount(dto.getId(), dto.getPanNumber(), dto.getSecurityCode(), dto.getCardHolderName(), dto.getValidUntil(), dto.getBalance(), dto.getReservedFunds());
    }
    public BankAccountDto BankAccountToDto(BankAccount account){
        return new BankAccountDto(account.getId(), account.getPanNumber(), account.getSecurityCode(), account.getCardHolderName(), account.getValidUntil(),
                                    account.getBalance(), account.getReservedFunds());
    }
}
