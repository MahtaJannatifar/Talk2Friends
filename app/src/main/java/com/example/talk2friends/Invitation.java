package com.example.talk2friends;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import com.google.api.services.gmail.Gmail;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
// App pasword: hpwb qoqo mewj dvag
// To write python scripts to send emails from java
// TODO: inviting to a specific meeting, make sure to pass meeting ID
public class Invitation {

    private static final String GMAIL_SCOPE = "https://www.googleapis.com/auth/gmail.send";
    private String receiverUserEmail;
    private Auth auth;
    private GoogleAccountCredential credential;
    private Gmail gmail;
    private String systemEmail;
    private static Context context;

    public Invitation(Auth auth, String receiverUserEmail, Context context) {
        systemEmail = "talk2friends310@gmail.com";  // Your app's dedicated email for sending invites
        this.auth = auth;
        this.receiverUserEmail = receiverUserEmail;
        Invitation.context = context;

        // Initialize the GoogleAccountCredential and Gmail service
        credential = GoogleAccountCredential.usingOAuth2(
                context, Collections.singletonList(GMAIL_SCOPE));
        credential.setSelectedAccountName(systemEmail);
        gmail = new Gmail.Builder(new NetHttpTransport(), new GsonFactory(), credential)
                .setApplicationName("Talk2Friends")
                .build();
    }

    @SuppressLint("StaticFieldLeak")
    public void sendEmailVerificationCode(final String verificationCode) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                String subject = "Invitation to join a meeting in Talk2Friends";
                String body = "You've been invited to join a meeting. Please use the following verification code to complete your registration: " + verificationCode;
                try {
                    createEmail(receiverUserEmail, systemEmail, subject, body);
                    return true;
                } catch (Exception e) {
                    Log.e("EMAIL_ERROR", "Exception while sending email: " + e.getMessage());
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    Log.d("EMAIL", "Email sent successfully.");
                } else {
                    Log.e("EMAIL", "Failed to send email.");
                }
            }
        }.execute();
    }



    private String generateVerificationCode() {
        //Generate a random 6-digit number
        return String.valueOf((int)(Math.random() * 1000000));
    }
    private class SendEmailTask extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... verificationCodes) {
            sendEmailVerificationCode(verificationCodes[0]);
            return null;
        }
    }

    public void sendInvitationEmail(){
        if(auth.isLoggedIn()){
            // send invite
            String verificationCode =  generateVerificationCode();
            new SendEmailTask().execute(verificationCode);
            Log.d("CODE IS ", verificationCode);
        }
        else{
            System.out.println("Error, User is not authenticated.");
        }
    }

    public static void createEmail(String to, String from, String subject, String bodyText) throws MessagingException, IOException {
        final String username = "talk2friends310@gmail.com"; // Your Gmail address
        final String appPassword = "hpwbqoqomewjdvag"; // Your 16-character App password

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, appPassword);
                    }
                });

        try {
            String body = bodyText;
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email Sent Successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
