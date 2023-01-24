package tim5.bank.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="internal_transaction")
public class InternalTransaction {

    @Id
    @SequenceGenerator(name = "it_sequence_generator", sequenceName = "it_sequence", initialValue = 100, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "it_sequence_generator")
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="client_id")
    private BankAccount client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payment payment;
}
