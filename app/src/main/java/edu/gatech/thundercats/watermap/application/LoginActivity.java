package edu.gatech.thundercats.watermap.application;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.gatech.thundercats.watermap.R;
import edu.gatech.thundercats.watermap.network.APIServerConnection;
import edu.gatech.thundercats.watermap.services.RemoteUserService;
import edu.gatech.thundercats.watermap.util.Callback;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText login;
    private EditText pwd;

    private RemoteUserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userService = RemoteUserService.getInstance();

        setContentView(R.layout.activity_login);

        login = (EditText) findViewById(R.id.userName);
        pwd = (EditText) findViewById((R.id.password));

        Button mEmailSignInButton = (Button) findViewById(R.id.login_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                userService.authenticateUser(new Callback<String>() {
                    @Override
                    public void call(final String message) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }, new Callback<Boolean>() {
                    @Override
                    public void call(Boolean success) {
                        if (success) {
                            startActivity(new Intent(LoginActivity.this, MapsActivity.class));
                        }
                    }
                }).execute(login.getText().toString(), pwd.getText().toString());
            }
        });
        Button registrationButton = (Button) findViewById(R.id.registration_button);
        registrationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateAccount.class));
            }
        });
    }
}

