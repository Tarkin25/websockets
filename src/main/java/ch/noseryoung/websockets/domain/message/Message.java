package ch.noseryoung.websockets.domain.message;

import ch.noseryoung.websockets.domain.user.User;
import ch.noseryoung.websockets.generic.AbstractEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User from;

    @Column(length = 2000)
    private String content;

    public User getFrom() {
        return from;
    }

    public Message setFrom(User from) {
        this.from = from;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Message setContent(String content) {
        this.content = content;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return createdAt;
    }

    public Message setTimestamp(LocalDateTime timestamp) {
        return this;
    }
}
