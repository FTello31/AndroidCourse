package com.netlidev.seccion_12_notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private EditText editTextTitle;
    private EditText editTextMessage;
    private Switch switchImportance;
    private Button buttonSend;

    private String switchTextOn;
    private String switchTextOff;

    private boolean isHighImportance = false;
    private NotificationHandler notificationHandler;

    private int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextMessage = findViewById(R.id.editTextMessage);
        switchImportance = findViewById(R.id.switchImportance);
        buttonSend = findViewById(R.id.buttonSend);

        switchTextOn = getString(R.string.switch_notifications_on);
        switchTextOff = getString(R.string.switch_notifications_off);

        notificationHandler = new NotificationHandler(this);

        buttonSend.setOnClickListener(this);
        switchImportance.setOnCheckedChangeListener(this);
    }


    @Override
    public void onClick(View view) {
        sendNotification();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        isHighImportance = isChecked;
        switchImportance.setText((isChecked) ? switchTextOn : switchTextOff);
    }


    private void sendNotification() {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(message)) {
            Notification.Builder nb = notificationHandler.createNotification(title, message, isHighImportance);
            notificationHandler.getManager().notify(++counter, nb.build());
            notificationHandler.publishNotificationSummaryGroup(isHighImportance);
        }
    }
}
