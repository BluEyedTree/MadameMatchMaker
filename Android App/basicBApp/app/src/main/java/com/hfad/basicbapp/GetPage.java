package com.hfad.basicbapp;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tom on 5/13/17.
 */

public class GetPage extends AsyncTask<String, Void, String> {


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




    @Override
    protected String doInBackground(String... urls) {
        try {

            String Output = sendGet(urls[0]);

            return Output;
        }
        catch(Exception e){
            return "Caught_Exception_FAILED";

        }
    }

    @Override
    protected void onPostExecute(String result) {


    }
}