package project.util;

import java.io.*;

public class EmailGenerator {

    private static final String BASE_URL = "$baseURL$";
    private static final String USER = "$username$";
    private static final String LINK = "$link$";
    private static final String KEY = "$key$";
    private static final String BUTTON_TEXT = "$button_text$";
    private static final String ENCODING = "UTF-8";

    private EmailGenerator() {
    }

    public static String generateConfirmationMail(final String name, final String link, final String key, final String baseURL, String emailType, String buttonText) {
        try {
            return getMailTemplate(emailType).toString()
                    .replace(BASE_URL, baseURL)
                    .replace(USER, name)
                    .replace(LINK, link)
                    .replace(KEY, key)
                    .replace(BUTTON_TEXT, buttonText);
        } catch (final IOException e) {
            throw new RuntimeException("Failed to parse template .", e);
        }
    }

    public static String generateSuccessPasswordChangeMail(final String name, final String baseURL, String emailType, String buttonText){
        try {
            return getMailTemplate(emailType).toString()
                    .replace(BASE_URL, baseURL)
                    .replace(USER, name)
                    .replace(BUTTON_TEXT, buttonText);
        } catch (final IOException e) {
            throw new RuntimeException("Failed to parse template .", e);
        }
    }

    private static StringBuilder getMailTemplate(String emailType) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(getFileReader(emailType));
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
        return stringBuilder;
    }

    private static Reader getFileReader(final String fileName) {
        try {
            return new InputStreamReader(EmailGenerator.class.getResourceAsStream(fileName), ENCODING);
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("Wrong encoding " + fileName, e);
        }
    }


}

