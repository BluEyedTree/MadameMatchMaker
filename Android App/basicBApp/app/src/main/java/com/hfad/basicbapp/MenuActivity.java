package com.hfad.basicbapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    Button requestMatchTest;
    Button viewMatch;
    TextView signOut;
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
        requestMatchTest = (Button)findViewById(R.id.toRequestMatchActivity);
        viewMatch = (Button)findViewById(R.id.viewMatchesButton);
        signOut  = (TextView)findViewById(R.id.signMeOut);
    }

    // setting onClickListeners for buttons.

    private void setListeners(){
        viewMatch.setOnClickListener(this);
        requestMatchTest.setOnClickListener(this);
        signOut.setOnClickListener(this);
    }

    // defines the button functionality.
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.viewMatchesButton:
                Intent toViewMatches = new Intent(this,ViewMatches.class);
                startActivity(toViewMatches);
                break;
            case R.id.toRequestMatchActivity:
                Intent toRequestIntent = new Intent(this,RequestMatchActivity.class);
                startActivity(toRequestIntent);
                break;
            case R.id.signMeOut:
                Intent backToLogin = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(backToLogin);
                User.setEmail("");
                break;
        }
    }
}
