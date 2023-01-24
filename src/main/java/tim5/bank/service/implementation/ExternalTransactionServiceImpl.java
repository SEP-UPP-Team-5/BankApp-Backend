package tim5.bank.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim5.bank.model.ExternalTransaction;
import tim5.bank.repository.ExternalTransactionRepository;
import tim5.bank.service.template.ExternalTransactionService;

import java.util.List;

@Service
public class ExternalTransactionServiceImpl implements ExternalTransactionService {

    @Autowired
    private ExternalTransactionRepository externalTransactionRepository;

    @Override
    public ExternalTransaction create(ExternalTransaction externalTransaction) {
        return null;
    }

    @Override
    public ExternalTransaction getById(Long id) {
        return null;
    }

    @Override
    public List<ExternalTransaction> getAll() {
        return null;
    }

    @Override
    public ExternalTransaction update(ExternalTransaction externalTransaction) {
        return null;
    }

    @Override
    public ExternalTransaction delete(Long id) {
        return null;
    }
}
