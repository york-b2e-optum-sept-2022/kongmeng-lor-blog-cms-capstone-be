package net.yorksolutions.kongmenglorblogcmscapstonebe.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BlogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @JsonProperty
    private String title;
    @JsonProperty
    private String body;
    @JsonProperty
    private String create_Date;
    @JsonProperty
    private String update_Date;
    @JsonProperty
    private String owner_Email;
    @JsonProperty
    private Long owner_Id;

    public BlogEntity() {}

    public BlogEntity(String title, String body, String create_Date, String update_Date, String owner_Email, Long owner_Id) {
        this.title = title;
        this.body = body;
        this.create_Date = create_Date;
        this.update_Date = update_Date;
        this.owner_Email = owner_Email;
        this.owner_Id = owner_Id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreate_Date() {
        return create_Date;
    }

    public void setCreate_Date(String create_Date) {
        this.create_Date = create_Date;
    }

    public String getUpdate_Date() {
        return update_Date;
    }

    public void setUpdate_Date(String update_Date) {
        this.update_Date = update_Date;
    }

    public String getOwner_Email() {
        return owner_Email;
    }

    public void setOwner_Email(String owner_Email) {
        this.owner_Email = owner_Email;
    }

    public Long getOwner_Id() {
        return owner_Id;
    }

    public void setOwner_Id(Long owner_Id) {
        this.owner_Id = owner_Id;
    }
}
