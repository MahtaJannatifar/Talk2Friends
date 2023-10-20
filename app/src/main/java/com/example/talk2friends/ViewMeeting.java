package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.view.View;

import okhttp3.*;
import java.io.IOException;

public class ViewMeeting extends AppCompatActivity {
    private static final String CLIENT_ID = "MhUgUgn1QsafZNbiADTuBQ";
    private static final String CLIENT_SECRET = "BxpkUN548nDw3p8eXZit1CK65hl6RNKs";
    private static final String BASE_URL = "https://api.zoom.us/v2/users/me/meetings";

    // Define the Zoom OAuth URLs
    private static final String AUTH_URL = "https://zoom.us/oauth/authorize";
    private static final String TOKEN_URL = "https://zoom.us/oauth/token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meeting);
    }

    public static void createMeeting() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    // Step 1: Redirect user to Zoom OAuth authorization URL
                    String authorizationUrl = AUTH_URL +
                            "?response_type=token" +
                            "&client_id=" + CLIENT_ID +
                            "&state=your_state";
                    // Handle user interaction to open this URL.

                    // Step 2: Receive the authorization code after user grants permission.
                    String authorizationCode = "AUTHORIZATION_CODE";  // You'll need to retrieve this code from the user's response.

                    // Step 3: Exchange the authorization code for an access token
                    OkHttpClient client = new OkHttpClient();
                    RequestBody formBody = new FormBody.Builder()
                            .add("code", authorizationCode)
                            .add("grant_type", "authorization_code")
                            .build();

                    Request tokenRequest = new Request.Builder()
                            .url(TOKEN_URL)
                            .post(formBody)
                            .addHeader("Authorization", Credentials.basic(CLIENT_ID, CLIENT_SECRET))
                            .build();

                    Response tokenResponse = client.newCall(tokenRequest).execute();
                    if (tokenResponse.isSuccessful()) {
                        assert tokenResponse.body() != null;
                        JSONObject tokenData = new JSONObject(tokenResponse.body().string());
                        String accessToken = tokenData.getString("access_token");
                        // Use the access token in your API requests.
                        return makeZoomAPICall(accessToken);
                    } else {
                        return "Error: " + tokenResponse.code() + " - " + tokenResponse.message();
                    }
                } catch (Exception e) {
                    return "Error: " + e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                // Handle the result here, e.g., update UI or process the response.
                Log.d("HTTP POST Result", result);
            }
        }.execute();
    }

    private static String makeZoomAPICall(String accessToken) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // Define your meeting details and create a JSON request body
        JSONObject meetingDetails = new JSONObject();
        meetingDetails.put("topic", "The title of your zoom meeting");
        meetingDetails.put("type", 2);
        meetingDetails.put("start_time", "2023-10-20T10:00:00"); // Replace with your desired start time
        meetingDetails.put("duration", "45");
        meetingDetails.put("timezone", "Europe/Madrid");
        meetingDetails.put("agenda", "test");

        JSONObject recurrence = new JSONObject();
        recurrence.put("type", 1);
        recurrence.put("repeat_interval", 1);
        meetingDetails.put("recurrence", recurrence);

        JSONObject settings = new JSONObject();
        settings.put("host_video", "true");
        settings.put("participant_video", "true");
        settings.put("join_before_host", "False");
        settings.put("mute_upon_entry", "False");
        settings.put("watermark", "true");
        settings.put("audio", "voip");
        settings.put("auto_recording", "cloud");
        meetingDetails.put("settings", settings);

        RequestBody requestBody = RequestBody.create(JSON, meetingDetails.toString());

        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(requestBody)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            assert response.body() != null;
            return response.body().string();
        } else {
            return "Error: " + response.code() + " - " + response.message();
        }
    }
    public void onBackPressed(View view) {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
