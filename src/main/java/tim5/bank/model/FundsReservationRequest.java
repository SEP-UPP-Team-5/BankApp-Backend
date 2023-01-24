package tim5.bank.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name="funds_reservation_request")
public class FundsReservationRequest {

    @Id
    @SequenceGenerator(name = "frr_sequence_generator", sequenceName = "frr_sequence", initialValue = 100, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frr_sequence_generator")
    @Column(name = "id", unique = true)
    private Long id;    // issuerOrderId

    @Column(name = "acquirer_order_id", unique = true)
    private String acquirerOrderId;

    @Column(name = "acquirer_timestamp")
    private LocalDateTime acquirerTimestamp;

    @Column(name = "issuer_timestamp")
    private LocalDateTime issuerTimestamp;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="client_id")
    private BankAccount client;

    @Column(name = "amount")
    private double amount;

    @Column(name = "status")
    private String status;
}
