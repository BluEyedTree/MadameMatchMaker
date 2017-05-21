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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    //private static final int REQUEST_MENU = 0;
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";


    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeElements();
        setListeners();
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(true);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
               /**
                GetPage task = new GetPage();
                try {
                    String test = task.execute(new String[] {"http://172.16.11.22:5000/Users"}).get();
                    Log.i("TEST", test);
                }
                catch(Exception e){
                    Log.i(TAG, "Failed");
                    //display.setText(e.toString());

                }
                **/
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

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


    // if it's invoked by signup activity, it's gonna display the data user plugged in.

    private void initializeElements(){
        _emailText = (EditText)findViewById(R.id.input_email_login);
        _passwordText = (EditText)findViewById(R.id.input_password_login);
        _loginButton = (Button)findViewById(R.id.btn_login_login);
        _signupLink = (TextView) findViewById(R.id.link_signup_login);
    }

    private void setListeners(){
        _loginButton.setOnClickListener(this);
        _signupLink.setOnClickListener(this);
    }

    @Override
    protected void onRestart() {
        Log.i("onResume","Has been run");
        super.onRestart();
        Intent startedThis = getIntent();
        String passwordFromSignup = startedThis.getExtras().getString("passwordToLogin");
        String emailFromSignup  = startedThis.getExtras().getString("emailToLogin");


        _emailText.setText(passwordFromSignup);
        _passwordText.setText(emailFromSignup);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_login_login:
                login();
                Intent toMenuActivity = new Intent(getApplicationContext(), MenuActivity.class);
                //Grabbing the username to display in the Menu activity
                String email = _emailText.getText().toString();
                String password = _passwordText.getText().toString();
                //Starting the Menu activity
                toMenuActivity.putExtra(EXTRA_MESSAGE, email);
                startActivity(toMenuActivity);
                break;
            case R.id.link_signup_login:
                //Starting the Signup activity
                Intent toSignupActivity = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(toSignupActivity, REQUEST_SIGNUP);
                break;
        }
    }
}
