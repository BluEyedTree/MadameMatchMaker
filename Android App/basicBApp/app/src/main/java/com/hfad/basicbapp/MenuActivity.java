package com.hfad.basicbapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Arrays;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        String message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
        String[] split_message = message.split("[@._]");
        String user_name = split_message[0];
        // Capture the layout's TextView and set the string as its text
        //Grabbing the Textview to display the username
        TextView display_user = (TextView) findViewById(R.id.username);
        display_user.setText("Welcome "+ user_name.toString());

    }
}
