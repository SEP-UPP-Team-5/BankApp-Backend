package tim5.bank.service.template;

import tim5.bank.model.ExternalTransaction;

import java.util.List;

public interface ExternalTransactionService {

    ExternalTransaction create(ExternalTransaction externalTransaction);
    ExternalTransaction getById(Long id);
    List<ExternalTransaction> getAll();
    ExternalTransaction update(ExternalTransaction externalTransaction);
    ExternalTransaction delete(Long id);

}
