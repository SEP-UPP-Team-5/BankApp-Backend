package tim5.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim5.bank.dto.BankAccountDto;
import tim5.bank.dto.mapper.BankAccountMapper;
import tim5.bank.model.BankAccount;
import tim5.bank.service.template.BankAccountService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/bankAccounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class BankAccountController {
    @Autowired
    private BankAccountService bankAccountService;

    final private BankAccountMapper mapper = new BankAccountMapper();

    @GetMapping
    public List<BankAccountDto> getAccounts() {
        List<BankAccountDto> dtoList = new ArrayList<>();
        for (BankAccount account : bankAccountService.getAll())
            dtoList.add(mapper.BankAccountToDto(account));
        return dtoList;
    }
    @GetMapping("/{id}")
    public ResponseEntity<BankAccountDto> getAccountById(@PathVariable Long id) {
        BankAccount account = bankAccountService.getById(id);
        if (account != null) {
            return new ResponseEntity<>(mapper.BankAccountToDto(account), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/pan/{pan}")
    public ResponseEntity<BankAccountDto> getAccountByPanNumber(@PathVariable String pan) {
        BankAccount account = bankAccountService.getByPanNumber(pan);
        if (account != null) {
            return new ResponseEntity<>(mapper.BankAccountToDto(account), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> saveAccount(@RequestBody BankAccountDto dto) {
        if (isNullOrEmpty(dto.getPanNumber(), dto.getSecurityCode(), dto.getCardHolderName()))
            return new ResponseEntity<>("None of fields cannot be empty!", HttpStatus.BAD_REQUEST);
        BankAccount account = mapper.DtoToBankAccount(dto);
        return new ResponseEntity<>("Added bank account with id " + bankAccountService.create(account).getId(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        BankAccount account = bankAccountService.getById(id);
        if(account == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        bankAccountService.delete(id);
        return new ResponseEntity<>("Account deleted with id " + id, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankAccountDto> update(@PathVariable Long id, @RequestBody BankAccountDto dto){
        if(!id.equals(dto.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BankAccount account = mapper.DtoToBankAccount(dto);
        BankAccount saved = bankAccountService.update(account);
        if (saved == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(mapper.BankAccountToDto(saved),HttpStatus.OK);
    }

    private static boolean isNullOrEmpty (String...strArr){
        for (String st : strArr) {
            if (st == null || st.equals(""))
                return true;

        }
        return false;
    }

}
