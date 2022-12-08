package net.yorksolutions.kongmenglorblogcmscapstonebe.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;

@Entity
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty
    private String email;
    @JsonProperty
    private String password;
    @JsonProperty
    private String name;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<Message> messages;

    public AccountEntity(String email, String password, String name, List<Message> messages) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public AccountEntity() {}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
