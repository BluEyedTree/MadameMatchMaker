package com.hfad.basicbapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewMatches extends AppCompatActivity {
    ListView list;
    TextView signOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_matches);
        dealWithMatches();
        dealWithSignOutClickable();
    }

    private void dealWithSignOutClickable(){
        signOut = (TextView)findViewById(R.id.signOutViewMatches);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(backToLogin);
            }
        });
    }

    private void dealWithMatches(){
        String output = getMatches();
        String[] matched = output.split("&");
        List<String> finalList = new ArrayList<String>();
        for(String single : matched){
            String[] singleSplitted = single.split(";");

            for(int i = 0; i<singleSplitted.length;i++){
                if(i==0){
                    finalList.add(singleSplitted[0]);
                }
                else {
                    String NewString = "    "+singleSplitted[i];
                    finalList.add(NewString);
                }
            }
        }
        String[] finalS = finalList.toArray(new String[finalList.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,finalS);
        list = (ListView)findViewById(R.id.matchesList);
        list.setAdapter(adapter);
    }




    public String getMatches(){
        GetPage task = new GetPage();
        try {
            String email = User.getEmail();
            String BuildString = "http://10.0.2.2:5000/getMatches/"+email;
            Log.i("TAT", BuildString);
            String matches = task.execute(new String[] {BuildString}).get();
                Log.i("TAT", matches);
                return matches;

        }
        catch(Exception e){
            Log.i("TAT", "Failed");
            //display.setText(e.toString());
            return "Failed";

        }


    }

}
