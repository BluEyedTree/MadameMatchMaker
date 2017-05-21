package com.hfad.basicbapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    Button requestMatch;
    Button viewMatch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initializeElements();
        setListeners();


        Intent intent = getIntent();
        String message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
        String[] split_message = message.split("[@._]");
        String user_name = split_message[0];

        // Capture the layout's TextView and set the string as its text
        //Grabbing the Textview to display the username
        TextView display_user = (TextView) findViewById(R.id.username);
        display_user.setText("Welcome "+ user_name.toString());

    }

    // initializing the view elements.
    private void initializeElements(){
        requestMatch = (Button)findViewById(R.id.requestMatchButton);
        viewMatch = (Button)findViewById(R.id.viewMatchesButton);
    }

    // setting onClickListeners for buttons.

    private void setListeners(){
        viewMatch.setOnClickListener(this);
        //requestMatch.setOnClickListener(this);
    }

    // defines the button functionality.
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.viewMatchesButton:
                Intent toViewMatches = new Intent(this,ViewMatches.class);
                startActivity(toViewMatches);
                break;
            case R.id.requestMatchButton:
                Intent toViewMatches1 = new Intent(this,ViewMatches.class);
                startActivity(toViewMatches1);
                break;
        }
    }
}
