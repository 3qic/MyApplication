package com.example.myapplication;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private Toolbar toolbar;

    private RadioGroup ColorSettings;
    private RadioGroup CharSizeSettings;
    private RadioButton CharSize1;
    private RadioButton CharSize2;
    private RadioButton CharSize3;
    private RadioButton Color1;
    private RadioButton Color2;
    private RadioButton Color3;
    private RadioButton Color4;

    private Button buttonSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupToolbar();
        findViews();


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

    }

    public void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void findViews() {
        ColorSettings = findViewById(R.id.radio_group_colors);
        CharSizeSettings = findViewById(R.id.radio_group_char_size);
        buttonSave = findViewById(R.id.save_button);
        CharSize1 = findViewById(R.id.radio_button_charsize_1);
        CharSize2 = findViewById(R.id.radio_button_charsize_2);
        CharSize3 = findViewById(R.id.radio_button_charsize_3);
        Color1 = findViewById(R.id.radio_button_color_1);
        Color2 = findViewById(R.id.radio_button_color_2);
        Color3 = findViewById(R.id.radio_button_color_3);
        Color4 = findViewById(R.id.radio_button_color_4);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == ColorSettings && checkedId == ColorSettings.getId()) {
            changeColor(checkedId);

        } else if (group == CharSizeSettings && checkedId == CharSizeSettings.getId()) {
            changeCharSize(checkedId);

        }
    }

    private void changeCharSize(int checkedid) {
        if (checkedid == CharSize1.getId()) {


        } else if (checkedid == CharSize2.getId()) {


        } else if (checkedid == CharSize3.getId()) {

        }
    }


    private void changeColor(int checkedid) {
        if (checkedid == Color1.getId()) {
            setTheme(R.style.AppThemeYellow);
            setContentView(R.layout.activity_main);
            setContentView(R.layout.activity_settings);

        } else if (checkedid == Color2.getId()) {
            setTheme(R.style.AppThemeGreen);
            setContentView(R.layout.activity_main);
            setContentView(R.layout.activity_settings);

        } else if (checkedid == Color3.getId()) {


        } else if (checkedid == Color4.getId()) {

        }

    }


    private void save() {
        //hier soll der Button die Einstellungen speichern
    }
}
