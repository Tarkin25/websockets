package ch.noseryoung.websockets.domain.user;

import ch.noseryoung.websockets.domain.chat.Chat;
import ch.noseryoung.websockets.core.generic.AbstractEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {

    private String username;

    private String password;

    @ManyToMany(mappedBy = "users")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Chat> chats = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Set<Chat> getChats() {
        return chats;
    }

    public User setChats(Set<Chat> chats) {
        this.chats = chats;
        return this;
    }
}
