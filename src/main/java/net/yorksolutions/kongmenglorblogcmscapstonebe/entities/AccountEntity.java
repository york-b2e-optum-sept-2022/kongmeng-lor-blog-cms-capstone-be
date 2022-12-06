package net.yorksolutions.kongmenglorblogcmscapstonebe.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
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
    private Boolean sent = false;

    public Boolean getSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<MessageEntity> messageEntity;

    public List<MessageEntity> getMessageEntity() {
        return messageEntity;
    }

    public void setMessageEntity(List<MessageEntity> messageEntity) {
        this.messageEntity = messageEntity;
    }

    public AccountEntity() {
    }
    public AccountEntity(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.messageEntity = null;
        this.sent = false;
    }

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
