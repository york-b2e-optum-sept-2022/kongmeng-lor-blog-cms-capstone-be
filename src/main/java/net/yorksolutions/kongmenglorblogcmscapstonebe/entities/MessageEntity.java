package net.yorksolutions.kongmenglorblogcmscapstonebe.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Map;

@Entity
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @JsonProperty
    private String current_Message;
    @ElementCollection
    private Map<Long, String> history_Messages_Send;
    @ElementCollection
    private Map<Long, String> history_Messages_Receive;
    public MessageEntity() {}

    public MessageEntity(String current_Message, Map<Long, String> history_Messages_Send, Map<Long, String> history_Messages_Receive) {
        this.current_Message = current_Message;
        this.history_Messages_Send = history_Messages_Send;
        this.history_Messages_Receive = history_Messages_Receive;
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

    public Map<Long, String> getHistory_Messages_Send() {
        return history_Messages_Send;
    }

    public void setHistory_Messages_Send(Map<Long, String> history_Messages_Send) {
        this.history_Messages_Send = history_Messages_Send;
    }

    public Map<Long, String> getHistory_Messages_Receive() {
        return history_Messages_Receive;
    }

    public void setHistory_Messages_Receive(Map<Long, String> history_Messages_Receive) {
        this.history_Messages_Receive = history_Messages_Receive;
    }
}
