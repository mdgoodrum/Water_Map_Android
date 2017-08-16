package edu.gatech.thundercats.watermap.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.thundercats.watermap.R;
import edu.gatech.thundercats.watermap.domain.User;
import edu.gatech.thundercats.watermap.services.RemoteUserService;
import edu.gatech.thundercats.watermap.util.Callback;

public class CreateAccount extends AppCompatActivity {

    private Button backButton;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;

    private Spinner spinnerUsers;

    private RemoteUserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userService = RemoteUserService.getInstance();

        setContentView(R.layout.activity_create_account);
        backButton = (Button) findViewById(R.id.buttonBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccount.this, LoginActivity.class));
            }
        });

        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);

        ArrayAdapter<User.Role> adapter = new ArrayAdapter<User.Role>(this, android.R.layout.simple_spinner_item, User.Role.values());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUsers = (Spinner) findViewById(R.id.spinner);
        spinnerUsers.setAdapter(adapter);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userService.registerUser(new Callback<String>() {
                    @Override
                    public void call(final String message) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }, new Callback<Boolean>() {
                    @Override
                    public void call(Boolean success) {
                    }
                }).execute(new User(
                        editText2.getText().toString(),
                        editText3.getText().toString(),
                        editText4.getText().toString(),
                        (User.Role) spinnerUsers.getSelectedItem()));
            }
        });
    }
}
