package tim5.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim5.bank.dto.BankAccountDto;
import tim5.bank.dto.MerchantDto;
import tim5.bank.dto.mapper.MerchantMapper;
import tim5.bank.model.BankAccount;
import tim5.bank.model.Merchant;
import tim5.bank.service.template.MerchantService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/merchants", produces = MediaType.APPLICATION_JSON_VALUE)

public class MerchantController {
    @Autowired
    private MerchantService merchantService;
    final private MerchantMapper mapper = new MerchantMapper();

    @GetMapping
    public List<MerchantDto> getAll() {
        List<MerchantDto> dtoList = new ArrayList<>();
        for (Merchant merchant : merchantService.getAll())
            dtoList.add(mapper.MerchantToDto(merchant));
        return dtoList;
    }
    @GetMapping("/{id}")
    public ResponseEntity<MerchantDto> getMerchantById(@PathVariable Long id) {
        Merchant merchant = merchantService.getById(id);
        if (merchant != null) {
            return new ResponseEntity<>(mapper.MerchantToDto(merchant), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/merchantId/{id}")
    public ResponseEntity<MerchantDto> getMerchantByMerchantId(@PathVariable String id) {
        Merchant merchant = merchantService.getByMerchantId(id);
        if (merchant != null) {
            return new ResponseEntity<>(mapper.MerchantToDto(merchant), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> saveMerchant(@RequestBody MerchantDto dto) {
        Merchant merchant = mapper.DtoToMerchant(dto);
        return new ResponseEntity<>("Added merchant with id " + merchantService.create(merchant).getId(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMerchant(@PathVariable Long id){
        Merchant merchant = merchantService.getById(id);
        if(merchant == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        merchantService.delete(id);
        return new ResponseEntity<>("Merchant deleted with id " + id, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchantDto> update(@PathVariable Long id, @RequestBody MerchantDto dto){
        if(!id.equals(dto.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Merchant merchant = mapper.DtoToMerchant(dto);
        Merchant saved = merchantService.update(merchant);
        if (saved == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(mapper.MerchantToDto(saved),HttpStatus.OK);
    }

}
