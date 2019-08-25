package com.example.myapplication;

// https://www.youtube.com/watch?v=zmjfAcnosS0

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.CountDownTimer;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class RecipeActivity extends AppCompatActivity {


    private TextView textView, scroll;
    private Button startStopButton;
    private CountDownTimer countDownTimer;
    private long timeLeftMilli;
    private boolean isRunning = false;
    private EditText editText;
    private TimerNotification timerNotification;
    private Intent backToRecipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity);
        setupItems();
        setupListener();

    }


    private void setupItems() {
        textView = findViewById(R.id.timer);
        startStopButton = findViewById(R.id.start);
        editText = findViewById(R.id.input);
        scroll = findViewById(R.id.textView);
        scroll.setMovementMethod((new ScrollingMovementMethod()));
        timerNotification = new TimerNotification(this);
        backToRecipe = new Intent(RecipeActivity.this, RecipeActivity.class);

    }

    private void setupListener() {
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStop();
            }
        });


    }

    public void startStop() {
        if (isRunning) {
            stopTimer();
        } else {
            startTimer();
        }
    }

    public void startTimer() {
        if(!isEditEmpty(editText)) {
            int input = Integer.parseInt(editText.getText().toString());
            timeLeftMilli = input * 60000;
            editText.setText("");
        }

        countDownTimer = new CountDownTimer(timeLeftMilli, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftMilli = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timerDone(getString(R.string.timerDoneNotification), backToRecipe);
            }
        }.start();

        startStopButton.setText(R.string.stop);
        isRunning = true;
    }


    public void stopTimer() {
        countDownTimer.cancel();
        startStopButton.setText(R.string.start);
        isRunning = false;
    }



    //https://bestecode.com/question/3950481-countdown-timer-wird-mit-00-00-00-formatiert-zeig
    public void updateTimer() {
        String output;
        long seconds = timeLeftMilli / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds = seconds % 60;
        minutes = minutes % 60;
        hours = hours % 60;

        String secondsD = String.valueOf(seconds);
        String minutesD = String.valueOf(minutes);
        String hoursD = String.valueOf(hours);

        if (seconds < 10)
            secondsD = "0" + seconds;
        if (minutes < 10)
            minutesD = "0" + minutes;
        if (hours < 10)
            hoursD = "0" + hours;

        output = hoursD + " : " + minutesD + " : " + secondsD;
        textView.setText(output);
    }

    private boolean isEditEmpty(EditText editText){
        return editText.getText().toString().trim().length() == 0;
    }


    public void timerDone(String title, Intent i) {
        NotificationCompat.Builder nBuilder = timerNotification.getChannelNotification(title, i);
        timerNotification.getNotificationManager().notify(1, nBuilder.build());
    }


}
