package tim5.bank.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim5.bank.model.InternalTransaction;
import tim5.bank.repository.InternalTransactionRepository;
import tim5.bank.service.template.InternalTransactionService;

import java.util.List;

@Service
public class InternalTransactionServiceImpl implements InternalTransactionService {

    @Autowired
    private InternalTransactionRepository internalTransactionRepository;
    
    @Override
    public InternalTransaction create(InternalTransaction internalTransaction) {
        return internalTransactionRepository.save(internalTransaction);
    }

    @Override
    public InternalTransaction getById(Long id) {
        return null;
    }

    @Override
    public List<InternalTransaction> getAll() {
        return null;
    }

    @Override
    public InternalTransaction update(InternalTransaction internalTransaction) {
        return null;
    }

    @Override
    public InternalTransaction delete(Long id) {
        return null;
    }
}
