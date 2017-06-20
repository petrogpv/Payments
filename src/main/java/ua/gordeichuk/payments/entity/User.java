package ua.gordeichuk.payments.entity;

public class User implements Entity {
    private Long id;
    private String firstName;
    private String lastName;

    private User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static class Builder{
        private User user = new User();

        public Builder setFirstName(String firstName) {
            user.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            user.lastName = lastName;
            return this;
        }


        public Builder setId(Long id) {
            user.id = id;
            return this;
        }

        public User build() {
            return user;
        }
    }

}
