package net.yorksolutions.kongmenglorblogcmscapstonebe.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String email_From;
    private String email_To;
    private Long email_To_Id;
    private String message;
    @ElementCollection
    @JoinColumn
    //Whoever send/words
    private List<HashMap> history_Messages;



    public Message() {}
    public Message(String email_From, String email_To, Long email_To_Id, String message, List<HashMap> history_Messages) {
        this.email_From = email_From;
        this.email_To = email_To;
        this.email_To_Id = email_To_Id;
        this.message = message;
        this.history_Messages = history_Messages;
    }
    public Long getId() {
        return id;
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
    public Long getSend_Id() {
        return email_To_Id;
    }
    public void setSend_Id(Long email_To_Id) {
        this.email_To_Id = email_To_Id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public List<HashMap> getHistory_Messages() {
        return history_Messages;
    }
    public void setHistory_Messages(List<HashMap> history_Messages) {
        this.history_Messages = history_Messages;
    }
}
