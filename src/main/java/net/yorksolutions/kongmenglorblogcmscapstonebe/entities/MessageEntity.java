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
    @ElementCollection
    private List<HashMap> history_Messages = new ArrayList<HashMap>();

    public MessageEntity() {}

    public MessageEntity(String current_Message, String email_From, String email_To, List<HashMap> history_Messages) {
        this.current_Message = current_Message;
        this.email_From = email_From;
        this.email_To = email_To;
        this.history_Messages = history_Messages;
    }

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

    public List<HashMap> getHistory_Messages() {
        return history_Messages;
    }

    public void setHistory_Messages(List<HashMap> history_Messages) {
        this.history_Messages = history_Messages;
    }
}