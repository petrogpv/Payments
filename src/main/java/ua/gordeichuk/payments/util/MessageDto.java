package ua.gordeichuk.payments.util;

import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by Администратор on 09.07.2017.
 */
public class MessageDto {
    public static final Locale ENGLISH_LOCALE = new Locale("en", "US");
    public static final Locale UKRAINIAN_LOCALE = new Locale("uk", "UA");
    private static final Logger LOGGER = Logger.getLogger(Message.class);
    private static final String BUNDLE_NAME = "/localization/messages";
    private static ResourceBundle messagesBundle = ResourceBundle.getBundle(BUNDLE_NAME, ENGLISH_LOCALE);;
    private static ResourceBundle logBundle = ResourceBundle.getBundle(BUNDLE_NAME, ENGLISH_LOCALE);
    private String message;
    private String logMessage;

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

   public static String getMessage(String key){
       return messagesBundle.getString(key);
   }

    public static void setLocale(Locale locale) {

        try {
            messagesBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
        } catch (Exception e) {
            LOGGER.error(LogMessage.RB_READ_ERROR + BUNDLE_NAME + locale);
        }

    }

    public static class Builder {
        private MessageDto messageDto = new MessageDto();
        private StringBuilder sbMessage = new StringBuilder();
        private StringBuilder sbLog = new StringBuilder();

        public Builder addMessage(String messageKey){
            sbMessage.append(getMessageForDto(messagesBundle, messageKey));
            sbLog.append(getMessageForDto(logBundle, messageKey));
            return this;
        }
        private String getMessageForDto(ResourceBundle bundle, String key){
            String message;
            try {
                message = bundle.getString(key);
            } catch (MissingResourceException e) {
                message = key;
            }
            return message;
        }
        public MessageDto build(){
            messageDto.setMessage(sbMessage.toString());
            messageDto.setLogMessage(sbLog.toString());
            return messageDto;
        }
    }
}
