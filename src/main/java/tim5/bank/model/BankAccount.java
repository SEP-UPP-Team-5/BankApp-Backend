package tim5.bank.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bankAccount")
public class BankAccount {

    @Id
    @SequenceGenerator(name = "ba_sequence_generator", sequenceName = "ba_sequence", initialValue = 100, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ba_sequence_generator")
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "pan_number")
    private String panNumber;

    @Column(name = "security_code")
    private double securityCode;

    @Column(name = "card_holder_name")
    private String cardHolderName;

    @Column(name = "valid_until")
    private LocalDateTime validUntil;

    @Column(name = "balance")
    private double balance;

    @Column(name = "reserved_funds")
    private double reservedFunds;
}
