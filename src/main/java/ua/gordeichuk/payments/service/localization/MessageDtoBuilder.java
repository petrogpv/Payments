package ua.gordeichuk.payments.service.localization;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageDtoBuilder {

    private static final String BUNDLE_NAME = "/localization/messages";
    private static final Locale ENGLISH_LOCALE = new Locale("en", "US");
    private ResourceBundle logBundle = ResourceBundle.getBundle(BUNDLE_NAME, ENGLISH_LOCALE);
    private ResourceBundle messagesBundle;
    private MessageDto messageDto = new MessageDto();
    private StringBuilder sbMessage = new StringBuilder();
    private StringBuilder sbLog = new StringBuilder();

    public MessageDtoBuilder() {
        messagesBundle = ResourceBundle.getBundle(BUNDLE_NAME, LocaleContext.getLocale());
    }

    public String getMessage(String key) {
        return messagesBundle.getString(key);
    }

    public MessageDtoBuilder addMessage(String messageKey) {
        sbMessage.append(getMessageForDto(messagesBundle, messageKey));
        sbLog.append(getMessageForDto(logBundle, messageKey));
        return this;
    }

    private String getMessageForDto(ResourceBundle bundle, String key) {
        String message;
        try {
            message = bundle.getString(key);
        } catch (MissingResourceException e) {
            message = key;
        }
        return message;
    }

    public MessageDto build() {
        messageDto.setMessage(sbMessage.toString());
        messageDto.setLogMessage(sbLog.toString());
        return messageDto;
    }
}
