package com.example.talk2friends;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Invitation {
    private final String receiverUserEmail;
    private final Auth auth;
    private static Context context;
    private String verificationCode;

    public Invitation(Auth auth, String receiverUserEmail, Context context) {
        this.auth = auth;
        this.receiverUserEmail = receiverUserEmail;
        Invitation.context = context;
    }

    @SuppressLint("StaticFieldLeak")
    public void sendEmailVerificationCode(final String verificationCode) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                String subject = "Invitation to join a meeting in Talk2Friends";
                String body = "You've been invited to join a meeting. Please use the following verification code to complete your registration: " + verificationCode;
                try {
                    createEmail(receiverUserEmail, subject, body);
                    return true;
                } catch (Exception e) {
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
    public void sendInvitationEmail(String meetingId){
        if(auth.isLoggedIn()){
            // send invite
            verificationCode =  generateVerificationCode();
            new SendEmailTask().execute(verificationCode);
        }
        else{
            System.out.println("Error, User is not authenticated.");
        }
    }
    public String getVerificationCode(){
        return verificationCode;
    }
    public static void createEmail(String to, String subject, String bodyText) {
        final String username = "talk2friends310@gmail.com";
        final String appPassword = context.getString(R.string.APP_PASSWORD);

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
