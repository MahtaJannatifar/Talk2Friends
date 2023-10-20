package com.example.talk2friends;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.api.services.gmail.model.Message;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import com.google.api.services.gmail.Gmail;

import org.apache.commons.codec.binary.Base64;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

// To write python scripts to send emails from java
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
        this.context = context;

        // Initialize the GoogleAccountCredential and Gmail service
        credential = GoogleAccountCredential.usingOAuth2(
                context, Collections.singletonList(GMAIL_SCOPE));
        credential.setSelectedAccountName(systemEmail);
        gmail = new Gmail.Builder(new NetHttpTransport(), new GsonFactory(), credential)
                .setApplicationName("Talk2Friends")
                .build();
    }

    public void sendEmailVerificationCode(String verificationCode){
        String subject = "com.example.talk2friends.Invitation to join a meeting in Talk2Friends";
        String body = "You've been invited to join a meeting. Please use the following verification code to complete your registration: " + verificationCode;

        try {
            Message message = createEmail(receiverUserEmail, systemEmail, subject, body);
            gmail.users().messages().send(systemEmail, message).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateVerificationCode() {
        //Generate a random 6-digit number
        return String.valueOf((int)(Math.random() * 1000000));
    }

    public void sendInvitationEmail(){
        if(auth.isLoggedIn()){
            // send invite
            String verificationCode =  generateVerificationCode();
            sendEmailVerificationCode(verificationCode);
            Log.d("CODE IS ", verificationCode);
        }
        else{
            System.out.println("Error, User is not authenticated.");
        }
    }

    private static Message createMessageWithEmail(MimeMessage emailContent) throws MessagingException, IOException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    public static Message createEmail(String to, String from, String subject, String bodyText) {
        // TODO: fix this part
        return null;
    }
}
