package ua.gordeichuk.payments.entity;

import ua.gordeichuk.payments.entity.enums.UserRole;

public class UserAuth implements Entity {
    private Long id;
    private UserRole role;
    private String login;
    private String password;
    private String sole;

    private UserAuth(){};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSole() {
        return sole;
    }

    public void setSole(String sole) {
        this.sole = sole;
    }

    public static class Builder{
        private UserAuth userAuth = new UserAuth();

        public Builder setLogin(String login) {
            userAuth.login = login;
            return this;
        }

        public Builder setPassword(String password) {
            userAuth.password = password;
            return this;
        }
        public Builder setSole(String sole) {
            userAuth.sole = sole;
            return this;
        }


        public Builder setId(Long id) {
            userAuth.id = id;
            return this;
        }

        public UserAuth build() {
            return userAuth;
        }
    }
}
