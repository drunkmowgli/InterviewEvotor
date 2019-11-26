package org.asm.labs.evotor.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@NamedEntityGraph(name = "accountGraph", includeAllAttributes = true)
@Entity
@Table(name = "account")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @NotNull
    @Column(name = "login", unique = true, updatable = false)
    private String login;
    
    @NotNull
    @Column(name = "password")
    private String password;
    
    @Column(name = "balance")
    private float balance;
    
    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
