package ua.gordeichuk.payments.model.entity;

import ua.gordeichuk.payments.model.entity.enums.UserRole;

public class UserAuth implements Entity, Cloneable {
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

        public Builder setRole(UserRole role) {
            userAuth.role = role;
            return this;
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAuth userAuth = (UserAuth) o;

        if (id != null ? !id.equals(userAuth.id) : userAuth.id != null) return false;
        if (role != userAuth.role) return false;
        if (login != null ? !login.equals(userAuth.login) : userAuth.login != null) return false;
        if (password != null ? !password.equals(userAuth.password) : userAuth.password != null) return false;
        return sole != null ? sole.equals(userAuth.sole) : userAuth.sole == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (sole != null ? sole.hashCode() : 0);
        return result;
    }

    @Override
    public UserAuth clone() throws CloneNotSupportedException {
        return (UserAuth)super.clone();
    }

    @Override
    public String toString() {
        return "UserAuth{" +
                "id=" + id +
                ", role=" + role +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", sole='" + sole + '\'' +
                '}';
    }
}
