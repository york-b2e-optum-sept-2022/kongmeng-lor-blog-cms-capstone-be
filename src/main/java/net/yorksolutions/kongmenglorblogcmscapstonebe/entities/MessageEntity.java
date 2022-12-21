package net.yorksolutions.kongmenglorblogcmscapstonebe.entities;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Entity
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @JsonProperty
    private String current_Message;
    @JsonProperty
    private String email_From;
    @JsonProperty
    private String email_To;
    @JsonProperty
    private String owner_To_Name;
    @JsonProperty
    private String owner_From_Name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    List<HistoryEntity> historyEntities = new ArrayList<>();

    public MessageEntity(String current_Message, String email_From, String email_To, String owner_To_Name, String owner_From_Name, List<HistoryEntity> historyEntities) {
        this.current_Message = current_Message;
        this.email_From = email_From;
        this.email_To = email_To;
        this.owner_To_Name = owner_To_Name;
        this.owner_From_Name = owner_From_Name;
        this.historyEntities = historyEntities;
    }

    public String getOwner_To_Name() {
        return owner_To_Name;
    }

    public void setOwner_To_Name(String owner_To_Name) {
        this.owner_To_Name = owner_To_Name;
    }

    public String getOwner_From_Name() {
        return owner_From_Name;
    }

    public void setOwner_From_Name(String owner_From_Name) {
        this.owner_From_Name = owner_From_Name;
    }

    public List<HistoryEntity> getHistoryEntities() {
        return historyEntities;
    }

    public void setHistoryEntities(List<HistoryEntity> historyEntities) {
        this.historyEntities = historyEntities;
    }

    public MessageEntity() {}

    public Long getId() {
        return id;
    }

    public String getCurrent_Message() {
        return current_Message;
    }

    public void setCurrent_Message(String current_Message) {
        this.current_Message = current_Message;
    }

    public String getEmail_From() {
        return email_From;
    }

    public void setEmail_From(String email_From) {
        this.email_From = email_From;
    }

    public String getEmail_To() {
        return email_To;
    }

    public void setEmail_To(String email_To) {
        this.email_To = email_To;
    }

}
