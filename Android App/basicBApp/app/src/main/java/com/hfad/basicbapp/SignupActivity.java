package com.hfad.basicbapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "SignupActivity";
      String password;
      String email;
      EditText _emailText;
      EditText _passwordText;
      Button _signupButton;
      TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        initializeElements();
        setOnClickListeners();
    }


    public void signup() throws ExecutionException, InterruptedException {
        Log.i("TAT", "Signup");
/**
        if (!validate()) {
            onSignupFailed();
            Log.i("TAG", "ValidationError");
            return;
        }
**/
        _signupButton.setEnabled(false);

        // informing the user of the account creation process upon the signup button click.

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        //String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();




        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);

        GetPage task = new GetPage();
        try {

            Log.i("TAT", "http://172.16.11.22:5000/Register/"+email+"/"+password);
            String test = task.execute(new String[] {"http://172.16.11.22:5000/Register/"+email+"/"+password}).get();
            Log.i("TAT", test);
            Log.i("Pass",password);
            Log.i("User",email);


        }
        catch(Exception e){
            Log.i("TAT", "Failed");
            //display.setText(e.toString());

        }
        setResult(RESULT_OK, null);
        Intent backToLogin = new Intent(this,LoginActivity.class);
        backToLogin.putExtra("passwordToLogin",password);
        backToLogin.putExtra("emailToLogin",email);
        startActivity(backToLogin);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        /*if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }*/

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    // this method sets onclick listeners to the elements in the layout. called in onCreate.

    private void initializeElements(){
        _emailText = (EditText)findViewById(R.id.input_email);
        _passwordText = (EditText)findViewById(R.id.input_password);
        _signupButton = (Button)findViewById(R.id.btn_signup);
        _loginLink = (TextView)findViewById(R.id.link_login);
        email = _emailText.getText().toString();
        password = _passwordText.getText().toString();
    }

    private void setOnClickListeners(){
        _signupButton.setOnClickListener(this);
        _loginLink.setOnClickListener(this);
    }

    //overriding the onCLick method from the interface that is implemented in the class declaration.
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_signup:
                try {
                    signup();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.link_login:
                finish();
                break;
        }
    }
}
