package com.example.myapplication;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
// https://www.youtube.com/watch?v=zmjfAcnosS0
import android.os.CountDownTimer;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity);
        setupItems();
        setupListener();

    }


    private void setupItems() {
        textView = findViewById(R.id.test);
        startStopButton = findViewById(R.id.start);
        editText = findViewById(R.id.input);
        scroll = findViewById(R.id.textView);
        scroll.setMovementMethod((new ScrollingMovementMethod()));
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
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

        }

        countDownTimer = new CountDownTimer(timeLeftMilli, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftMilli = l;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();

        startStopButton.setText("Stop");
        isRunning = true;
    }


    public void stopTimer() {
        countDownTimer.cancel();
        startStopButton.setText("Start");
        isRunning = false;
    }

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





}
