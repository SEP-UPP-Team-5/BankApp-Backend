package tim5.bank.model;

import jakarta.persistence.*;

@Entity
@Table(name="merchant")
public class Merchant {

    @Id
    @SequenceGenerator(name = "m_sequence_generator", sequenceName = "m_sequence", initialValue = 100, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_sequence_generator")
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "merchant_id", unique = true)
    private String merchantId;

    @Column(name = "merchant_password")
    private String merchantPassword;

    @Column(name = "balance")
    private double balance;

    @Column(name = "reserved_funds")
    private double reservedFunds;

}
