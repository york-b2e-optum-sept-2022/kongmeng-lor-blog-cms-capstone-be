package net.yorksolutions.kongmenglorblogcmscapstonebe.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String comment;
    private Long sender;
    private String sender_Name;
    private String sender_Email;

    public CommentsEntity() {}

    public CommentsEntity(String comment, Long sender, String sender_Name, String sender_Email) {
        this.comment = comment;
        this.sender = sender;
        this.sender_Name = sender_Name;
        this.sender_Email = sender_Email;
    }

    public String getSender_Name() {
        return sender_Name;
    }

    public void setSender_Name(String sender_Name) {
        this.sender_Name = sender_Name;
    }

    public String getSender_Email() {
        return sender_Email;
    }

    public void setSender_Email(String sender_Email) {
        this.sender_Email = sender_Email;
    }

    public Long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }
}
