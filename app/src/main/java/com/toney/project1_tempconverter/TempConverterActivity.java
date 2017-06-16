package com.toney.project1_tempconverter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.text.NumberFormat;


public class TempConverterActivity extends AppCompatActivity implements OnEditorActionListener{

    // define widget variables
    private EditText farenheitET;
    private TextView celciusTV;

    // define instance variable
    private String farenheit = "";
    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_converter);

        // get reference to the widgets
        farenheitET = (EditText) findViewById(R.id.farenheitET);
        celciusTV = (TextView) findViewById(R.id.celciusTV);

        // set the action listener for the event
        farenheitET.setOnEditorActionListener(this);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent keyEvent) {
        if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            calculateAndDisplay();
        }
        return false;
    }

    private void calculateAndDisplay() {
        // get input from user
        farenheit = farenheitET.getText().toString();
        float faren;
        if(farenheit.equals("")) {
            faren = 0;
        } else {
            faren = Float.parseFloat(farenheit);
        }

        // calculate celcius
        float celcius = (faren - 32) * 5/9;

        NumberFormat degrees = NumberFormat.getNumberInstance();
        celciusTV.setText(degrees.format(celcius));
    }

    @Override
    protected void onPause() {
        SharedPreferences.Editor edit = savedValues.edit();
        edit.putString("farenheit", farenheit);
        edit.commit();

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        farenheit = savedValues.getString("farenheit", "");
        farenheitET.setText(farenheit);
        calculateAndDisplay();
    }
}
