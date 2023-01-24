package tim5.bank.service.template;

import tim5.bank.model.InternalTransaction;

import java.util.List;

public interface InternalTransactionService {

    InternalTransaction create(InternalTransaction internalTransaction);
    InternalTransaction getById(Long id);
    List<InternalTransaction> getAll();
    InternalTransaction update(InternalTransaction internalTransaction);
    InternalTransaction delete(Long id);

}
