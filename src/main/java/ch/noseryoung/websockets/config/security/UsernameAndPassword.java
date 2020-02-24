package ch.noseryoung.websockets.config.security;

public class UsernameAndPassword {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public UsernameAndPassword setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UsernameAndPassword setPassword(String password) {
        this.password = password;
        return this;
    }
}
