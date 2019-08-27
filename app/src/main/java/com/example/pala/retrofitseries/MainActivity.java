package com.example.pala.retrofitseries;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.regex.Pattern;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView image;
    EditText editTextEmail,editTextPassword,editTextRePassword,editTextName,editTextSchool;
    TextView txtViewLogin;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = findViewById(R.id.app_logo);
        image.setImageResource(R.drawable.logo);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPassword=findViewById(R.id.editTextPassword);
        editTextRePassword = findViewById(R.id.editTextRePassword);
        editTextName = findViewById(R.id.editTextName);
        editTextSchool = findViewById(R.id.editTextSchool);
        signUp = findViewById(R.id.buttonSignUp);

        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);

    }

    private void userSignUp(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repassword = editTextRePassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String school = editTextSchool.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextPassword.setError("Password should be atleast 6 character long");
            editTextPassword.requestFocus();
            return;
        }
        if(repassword.isEmpty()){
            editTextPassword.setError("Repassword is required");
            editTextPassword.requestFocus();
            return;
        }
        if(repassword.length() < 6){
            editTextPassword.setError("Password should be atleast 6 character long");
            editTextPassword.requestFocus();
            return;
        }
        if(name.isEmpty()){
            editTextPassword.setError("Name is required");
            editTextPassword.requestFocus();
            return;
        }
        if(school.isEmpty()){
            editTextPassword.setError("School is required");
            editTextPassword.requestFocus();
            return;
        }
        if(!password.equals(repassword)){
            editTextPassword.setError("Şifreler aynı olmak zorundadır.");
            editTextRePassword.setError("Şifreler aynı olmak zorundadır. ");
            editTextPassword.requestFocus();
            return;
        }

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .createUser(email, password, name, school);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonSignUp:
                userSignUp();
                break;
            case R.id.textViewLogin:

                break;

        }
    }
}
