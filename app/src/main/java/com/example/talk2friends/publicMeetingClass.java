package com.example.talk2friends;
import java.util.ArrayList;
import java.util.List;
public class publicMeetingClass {
    private String id; // Unique ID for the meeting
    private String topic;
    private String date;
    private String startTime;
    private String endTime;
    private String location;
    private List<String> participants;

    // Constructors, getters, setters, and other methods
   public publicMeetingClass(){
       participants = new ArrayList<>();
   };
    // Constructor
    public publicMeetingClass(String id,String topic, String date, String startTime, String endTime, String location) {
        this.id = id;
        this.topic = topic;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.participants = new ArrayList<>();
    }
    //getID
    public String getId() {
        return id;
    }
    //getTopic
    public String getTopic() {
        return topic;
    }
    //getDate
    public String getDate() {
        return date;
    }
    //get start time
    public String getStartTime() {
        return startTime;
    }
    //get end time
    public String getEndTime() {
        return endTime;
    }
    //get location or zoom link
    public String getLocation() {
        return location;
    }
    // Add a participant to the list
    public void addParticipant(String participant) {
        participants.add(participant);
    }
    // Remove a participant from the list
    public void removeParticipant(String participant) {
        participants.remove(participant);
    }
    // Get the list of participants
    public List<String> getParticipants() {
        return participants;
    }
}
