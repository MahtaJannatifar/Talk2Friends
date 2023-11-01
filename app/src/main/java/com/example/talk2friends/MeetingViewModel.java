package com.example.talk2friends;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

public class MeetingViewModel {
    public ObservableField<String> topicAnswer = new ObservableField<>();
    public ObservableField<String> date = new ObservableField<>();
    public ObservableField<String> startTime = new ObservableField<>();
    public ObservableField<String> endTime = new ObservableField<>();
    public ObservableField<String> locationAnswer = new ObservableField<>();
}
