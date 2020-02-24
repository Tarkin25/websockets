package ch.noseryoung.websockets.domain.chat;

import ch.noseryoung.websockets.domain.message.Message;
import ch.noseryoung.websockets.domain.user.User;
import ch.noseryoung.websockets.generic.AbstractEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "chat")
public class Chat extends AbstractEntity {

    @ManyToMany
    @JoinTable(
            name = "users_in_chat",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id")
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<User> users = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "chat_id")
    private List<Message> messages = new ArrayList<>();

    private String name;

    public Set<User> getUsers() {
        return users;
    }

    public Chat setUsers(Set<User> users) {
        this.users = users;
        return this;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Chat setMessages(List<Message> messages) {
        this.messages = messages;
        return this;
    }

    public String getName() {
        return name;
    }

    public Chat setName(String name) {
        this.name = name;
        return this;
    }
}
