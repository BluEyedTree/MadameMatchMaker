package com.hfad.basicbapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RequestMatchActivity extends AppCompatActivity {
    EditText emailRequested;
    Button requestMatch;
    CheckBox checkBox1,checkBox2,checkBox3,checkBox4;
    TextView SignOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_match);
        initializeElements();
        setOnClickListener();
    }

    private void initializeElements(){
        emailRequested = (EditText)findViewById(R.id.requestedEmail);
        requestMatch = (Button)findViewById(R.id.requestMatchButton);
        checkBox1 = (CheckBox)findViewById(R.id.activity1);
        checkBox2 = (CheckBox)findViewById(R.id.activity2);
        checkBox3 = (CheckBox)findViewById(R.id.activity3);
        checkBox4 = (CheckBox)findViewById(R.id.activity4);
        SignOut = (TextView)findViewById(R.id.signOut);
    }
    private void setOnClickListener(){
        requestMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String requestedEmail = emailRequested.getText().toString();
                String requester = User.email;
                String[] activitiesChoosen = getActivitiesChoosen();
                addRequestToDatabase(requester,requestedEmail,activitiesChoosen);
                Toast toast = Toast.makeText(getApplicationContext(),"Request sent, goodl luck !",Toast.LENGTH_LONG);
                toast.show();
                CheckBox[] cuties = {checkBox1,checkBox2,checkBox3,checkBox4};
                for(CheckBox cb:cuties){
                    cb.setChecked(false);
                }
                emailRequested.setText(" ");
            }
        });
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToLoginPage = new Intent(getApplicationContext(),LoginActivity.class);
                User.setEmail("");
                startActivity(backToLoginPage);
            }
        });

    }
    private String[] getActivitiesChoosen(){
        List<String> activities = new ArrayList<String>();
        CheckBox[] checkBoxes = {checkBox1,checkBox2,checkBox3,checkBox4};
        for(CheckBox cb : checkBoxes){
            if(cb.isChecked()){
                String checkedActivity = cb.getText().toString();
                activities.add(checkedActivity);
            }
        }
        String[] arrayToBeReturned = activities.toArray(new String[activities.size()]);
        return  arrayToBeReturned;
    }

    public String removeSpaces(String inputString){

        String outString = inputString.replaceAll(" ","%20");
        return outString;
    }


    public void addRequestToDatabase(String eMailRequester, String eMailDesired, String[] activies){

        for(String activity: activies) {
            GetPage task = new GetPage();
            ///requestNewMatch/<EmailRequester>/<EmailDesired>/<activityDescription>'
            try {
                String BuildString = "http://10.0.2.2:5000/requestNewMatch/" + eMailRequester + "/" + eMailDesired+"/"+removeSpaces(activity);
                Log.i("TAT", BuildString);
                String test = task.execute(new String[]{BuildString}).get();
                Log.i("TAT", test);
                Log.i("TAT", test.toString());

            } catch (Exception e) {
                Log.i("TAT", "Failed");
                //display.setText(e.toString());

            }
        }
    }
}
