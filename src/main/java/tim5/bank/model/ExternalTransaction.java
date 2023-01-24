package tim5.bank.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="external_transaction")
public class ExternalTransaction {

    @Id
    @SequenceGenerator(name = "et_sequence_generator", sequenceName = "et_sequence", initialValue = 100, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "et_sequence_generator")
    @Column(name = "id", unique = true)
    private Long id;    // acquirerOrderId

    @Column(name = "acquirer_timestamp")
    private LocalDateTime acquirerTimestamp;

    @Column(name = "issuer_order_id", unique = true)
    private Long issuerOrderId;

    @Column(name = "issuer_timestamp")
    private LocalDateTime issuerTimestamp;

    @Column(name = "pan_number")
    private String panNumber;

    @Column(name = "security_code")
    private String securityCode;

    @Column(name = "card_holder_name")
    private String cardHolderName;

    @Column(name = "valid_until")
    private LocalDateTime validUntil;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payment payment;

}
