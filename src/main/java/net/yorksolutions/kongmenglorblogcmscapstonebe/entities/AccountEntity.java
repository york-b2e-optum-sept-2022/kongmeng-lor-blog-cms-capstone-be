package net.yorksolutions.kongmenglorblogcmscapstonebe.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
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

    @JsonIgnore
    private Boolean messageCreated;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<MessageEntity> messageEntities = new ArrayList<MessageEntity>();


    public AccountEntity() {}

    public AccountEntity(String email, String password, String name, List<MessageEntity> messageEntities) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.messageEntities = messageEntities;
        this.messageCreated = false;
    }

    public List<MessageEntity> getMessageEntities() {
        return messageEntities;
    }
    public void addMessage(MessageEntity message) {
        this.messageEntities.add(message);
    }

    public Boolean getMessageCreated() {
        return messageCreated;
    }

    public void setMessageCreated(Boolean messageCreated) {
        this.messageCreated = messageCreated;
    }

    public void setMessageEntities(List<MessageEntity> messageEntities) {
        this.messageEntities = messageEntities;
        this.messageCreated = true;
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
