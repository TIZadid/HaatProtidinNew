package com.ne.csedu.haatprotidinnew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;

public class LoginPage extends Activity implements AsyncResponse{


    EditText editUserID, editPassword;
    Button btnSignIn, btnRegister;
    Context context;

    String URL= "http://haatprotidin.com/php_an/login.php";
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        context = this;
        editUserID =(EditText)findViewById(R.id.etLoginUserID);
        editPassword=(EditText)findViewById(R.id.etLoginPassword);

        btnSignIn=(Button)findViewById(R.id.btnLogin);
        btnRegister=(Button)findViewById(R.id.btnLoginRegister);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String attributes[] = new String[2];
                String values[] = new String[2];
                attributes[0] = "username";
                attributes[1] = "password";
                values[0] = editUserID.getText().toString();
                values[1] = editPassword.getText().toString();
                GetMethodHandler request = new GetMethodHandler(attributes,values,2,"https://haatprotidin.com/php_an/login.php",context);
                //GetMethodHandler.delegate = this;
                try {
                    request.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String response = request.res;
                System.out.println("login theke " + response);
                //Intent intent = new Intent(LoginPage.this, Register.class);
                //startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, Register.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public void processFinish(String output) {
        System.out.println(output);
        try {
            JSONObject jsonObject = new JSONObject(output);
            System.out.println(jsonObject);
            String jsonstring = jsonObject.get("success").toString();
            if(jsonstring.equals("1")){
                Intent intent = new Intent(LoginPage.this,HomePage.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(LoginPage.this,"Wrong Information",Toast.LENGTH_LONG).show();
            }


        }catch (Exception err){
            Log.d("Error", err.toString());
        }

    }
}