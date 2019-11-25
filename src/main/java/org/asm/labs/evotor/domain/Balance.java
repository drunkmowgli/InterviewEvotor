package org.asm.labs.evotor.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "balance")
public class Balance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "balance")
    private float balance;
    
    @OneToOne(mappedBy = "balance")
    private Account account;
    
    public Balance(float balance) {
        this.balance = balance;
    }
}
