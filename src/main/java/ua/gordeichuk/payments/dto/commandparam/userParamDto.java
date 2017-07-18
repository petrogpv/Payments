package ua.gordeichuk.payments.dto.commandparam;

/**
 * Created by Валерий on 11.07.2017.
 */
public class userParamDto {
    private String firstName;
    private String lastName;
    private String login;


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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public class Builder {
        userParamDto userParamDto = new userParamDto();

        public Builder setFirstName(String firstName) {
            userParamDto.setFirstName(firstName);
            return this;
        }

        public Builder setLastName(String lastName) {
            userParamDto.setLastName(lastName);
            return this;
        }

        public Builder setLogin(String login) {
            userParamDto.setLogin(login);
            return this;
        }

        public userParamDto build() {
            return userParamDto;
        }
    }
}
