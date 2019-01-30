package com.example.gtmandroidapplication.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.toolbox.Volley;
import com.example.gtmandroidapplication.fragment.HomeFragment;
import com.example.gtmandroidapplication.LoginRequest;
import com.example.gtmandroidapplication.R;
import com.example.gtmandroidapplication.helper.SQLiteHandler;
import com.example.gtmandroidapplication.helper.SessionManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button bLogin;
    private Button registerLink;
    private EditText etEmail, etPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private TextView tvForgotPassword;
    private CheckBox cbRememberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        cbRememberMe = (CheckBox) findViewById(R.id.cbRememberMe);
        registerLink = (Button) findViewById(R.id.registerLink);

        bLogin.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        cbRememberMe.setOnClickListener(this);
        registerLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bLogin:
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                Intent intent = new Intent(LoginActivity.this, HomeFragment.class);
                                LoginActivity.this.startActivity(intent);
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed").setNegativeButton("Retry", null).create().show();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
                break;

            case R.id.registerLink:
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                break;

            case R.id.tvForgotPassword:
                Intent forgotPasswordIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(forgotPasswordIntent);
                break;

            case R.id.cbRememberMe:
                // Content goes here
                break;
        }
    }
}
