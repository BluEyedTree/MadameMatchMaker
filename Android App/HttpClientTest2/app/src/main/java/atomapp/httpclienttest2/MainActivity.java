package atomapp.httpclienttest2;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         Log.i("TAG", "FUCK");
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


         private void initializeElements(){
         Log.i("TAG", "InititializeElements");
         TextView display = (TextView) findViewById(R.id.testView);
         display.setText("damn");

         try {
         String test = sendGet("http://10.0.0.24/Users");
         Log.i("TAG", test);
         }
         catch(Exception e){
             Log.i("TAG", e.toString());

         }
         }





    }


