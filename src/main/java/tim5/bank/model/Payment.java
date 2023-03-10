package tim5.bank.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="payment")
public class Payment {

    @Id
    @SequenceGenerator(name = "p_sequence_generator", sequenceName = "p_sequence", initialValue = 100, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "p_sequence_generator")
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="merchant_id")
    private Merchant merchant;

    @Column(name = "amount")
    private double amount;

    @Column(name = "merchant_order_id")
    private String merchantOrderId;

    @Column(name = "merchant_timestamp")
    private LocalDateTime merchantTimestamp;

    @Column(name = "success_url")
    private String successUrl;

    @Column(name = "failed_url")
    private String failedUrl;

    @Column(name = "error_url")
    private String errorUrl;

    @Column(name = "status")
    private String status;

}
