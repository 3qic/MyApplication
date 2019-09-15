package com.example.myapplication;
import android.app.NotificationManager;
import android.content.Context;

// https://www.youtube.com/watch?v=zmjfAcnosS0

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.CountDownTimer;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//Code für Shared Preferences: https://www.youtube.com/watch?v=GlR7wqWEomU

public class RecipeActivity extends AppCompatActivity {
    SharedPreferences app_preferences;
    int appTheme;
    int themeColor;
    int appColor;
    private String nameString;
    private String instructionString;
    private String ingrediantsString;
    private String recipeidString;

    private TextView textView, recipeIngrediants, recipeInstruction;


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
        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        appColor = app_preferences.getInt("color", 0);
        appTheme = app_preferences.getInt("theme", 0);
        themeColor = appColor;


        if (themeColor == 0) {
            setTheme(Constant.theme);
        } else if (appTheme == 0) {
            setTheme(Constant.theme);
        } else {
            setTheme(appTheme);
        }
        setContentView(R.layout.recipe_activity);
        setupItems();
        setupListener();

        nameString = getIntent().getExtras().get("Name").toString();
        ingrediantsString = getIntent().getExtras().get("Zutaten").toString();
        instructionString = getIntent().getExtras().get("Kochanleitung").toString();
       

        setInfo();

    }


    private void setupItems() {
        textView = findViewById(R.id.timer);
        startStopButton = findViewById(R.id.start);
        editText = findViewById(R.id.input);
        recipeIngrediants = findViewById(R.id.recipe_mainView_ingrediants);
        recipeInstruction = findViewById(R.id.recipe_mainView_instruction);
        recipeInstruction.setMovementMethod(new ScrollingMovementMethod());
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
    //https://stackoverflow.com/questions/2342620/how-to-hide-keyboard-after-typing-in-edittext-in-android
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

    public void setInfo(){
        recipeIngrediants.setText(ingrediantsString);
        recipeInstruction.setText(instructionString);
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
