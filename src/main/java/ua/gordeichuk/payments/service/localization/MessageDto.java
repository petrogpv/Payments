package ua.gordeichuk.payments.service.localization;

public class MessageDto {
    private String message;
    private String logMessage;

    public MessageDto() {
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }
}
