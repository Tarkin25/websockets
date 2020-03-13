package ch.noseryoung.websockets.domain.user.dto;

import ch.noseryoung.websockets.core.generic.AbstractDTO;

public class UserDTO extends AbstractDTO {

    protected String username;

    public static class WithPassword extends UserDTO {

        private String password;

        public String getPassword() {
            return password;
        }

        public WithPassword setPassword(String password) {
            this.password = password;
            return this;
        }
    }

    public String getUsername() {
        return username;
    }

    public UserDTO setUsername(String username) {
        this.username = username;
        return this;
    }
}
