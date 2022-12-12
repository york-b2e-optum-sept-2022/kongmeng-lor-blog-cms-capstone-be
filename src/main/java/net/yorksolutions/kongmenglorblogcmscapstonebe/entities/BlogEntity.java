package net.yorksolutions.kongmenglorblogcmscapstonebe.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
    private LocalDate create_Date;
    @JsonProperty
    private LocalDate update_Date;
    @JsonProperty
    private String owner_Email;
    @JsonProperty
    private Long owner_Id;
    @JsonProperty
    private Integer view_Counts;

    @ElementCollection
    List<Long> view_Accounts = new ArrayList<>();

    public Integer getView_Counts() {
        return view_Counts;
    }

    public void setView_Counts(Integer view_Counts) {
        this.view_Counts = view_Counts;
    }

    public List<Long> getView_Accounts() {
        return view_Accounts;
    }

    public void setView_Accounts(List<Long> view_Accounts) {
        this.view_Accounts = view_Accounts;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<CommentsEntity> commentsLists = new ArrayList<>();


    public BlogEntity() {}

    public BlogEntity(String title, String body, String owner_Email, Long owner_Id) {
        this.title = title;
        this.body = body;
        this.create_Date = LocalDate.now();
        this.update_Date = LocalDate.now();
        this.owner_Email = owner_Email;
        this.owner_Id = owner_Id;
    }

    public List<CommentsEntity> getCommentsLists() {
        return commentsLists;
    }

    public void setCommentsLists(List<CommentsEntity> commentsLists) {
        this.commentsLists = commentsLists;
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

    public LocalDate getCreate_Date() {
        return create_Date;
    }

    public void setCreate_Date(LocalDate create_Date) {
        this.create_Date = create_Date;
    }

    public LocalDate getUpdate_Date() {
        return update_Date;
    }

    public void setUpdate_Date(LocalDate update_Date) {
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
