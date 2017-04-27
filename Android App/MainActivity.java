package atomapp.httpclienttest2;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
          StrictMode.setThreadPolicy(policy);
          initializeElements();
    }







        //The idea is to use this method with your built strings to interact with the web service
        //For example: "http://172.16.12.163:5000/Users"

         private String sendGet(String inputURL) throws Exception {
         String USER_AGENT = "Mozilla/5.0";
         String url = inputURL;

         URL obj = new URL(url);
         HttpURLConnection con = (HttpURLConnection) obj.openConnection();

         // optional default is GET
         con.setRequestMethod("GET");

         //add request header
         con.setRequestProperty("User-Agent", USER_AGENT);

         int responseCode = con.getResponseCode();
         System.out.println("\nSending 'GET' request to URL : " + url);
         System.out.println("Response Code : " + responseCode);

         BufferedReader in = new BufferedReader(
         new InputStreamReader(con.getInputStream()));
         String inputLine;
         StringBuffer response = new StringBuffer();

         while ((inputLine = in.readLine()) != null) {
         response.append(inputLine);
         }
         in.close();

         //print result
         return response.toString();

         }


         private void initializeElements() {
         TextView display = (TextView) findViewById(R.id.testView);
         display.setText("damn");

         try {
         String test = sendGet("http://172.16.12.163:5000/Users");
         display.setText(test);
         }
         catch(Exception e){
         display.setText(e.toString());

         }
         }





    }


