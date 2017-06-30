package ua.gordeichuk.payments.entity;

import java.util.ArrayList;
import java.util.List;

public class User implements Entity {
    private Long id;
    private String firstName;
    private String lastName;
    private Long taxCode;
    private UserAuth userAuth;
    private List<Card> cards = new ArrayList<>();

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

    public Long getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(Long taxCode) {
        this.taxCode = taxCode;
    }

    public UserAuth getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
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
        public Builder setTaxCode(Long taxCode) {
            user.taxCode = taxCode;
            return this;
        }


        public Builder setUserAuth(UserAuth userAuth) {
            user.userAuth = userAuth;
            return this;
        }

        public Builder setCards(List<Card> cards) {
            user.cards = cards;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (taxCode != null ? !taxCode.equals(user.taxCode) : user.taxCode != null) return false;
        if (userAuth != null ? !userAuth.equals(user.userAuth) : user.userAuth != null) return false;
        return cards != null ? cards.equals(user.cards) : user.cards == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (taxCode != null ? taxCode.hashCode() : 0);
        result = 31 * result + (userAuth != null ? userAuth.hashCode() : 0);
        result = 31 * result + (cards != null ? cards.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", taxCode=" + taxCode +
                ", cards=" + cards +
                ", userAuth=" + userAuth +
                '}';
    }
}
