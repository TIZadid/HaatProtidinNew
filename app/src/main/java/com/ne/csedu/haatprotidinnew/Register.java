package com.ne.csedu.haatprotidinnew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Register extends Activity implements AsyncResponse{

    Context context;
    EditText editUserID, editPassword, editEmail,editName;
    Button btnSignIn, btnRegister;

    String URL= "http://haatprotidin.com/php_an/register.php";

    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        context = this;
        editUserID =(EditText)findViewById(R.id.etUserID);
        editPassword=(EditText)findViewById(R.id.etPassword);
        editName =(EditText)findViewById(R.id.etFullName);
        editEmail=(EditText)findViewById(R.id.etUseremail);

        btnSignIn=(Button)findViewById(R.id.btnRegisterLogin);
        btnRegister=(Button)findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String attributes[] = new String[4];
                String values[] = new String[4];
                attributes[0] = "username";
                attributes[1] = "password";
                attributes[2] = "name";
                attributes[3] = "email";

                values[0] = editUserID.getText().toString();
                values[1] = editPassword.getText().toString();
                values[2] = editName.getText().toString();
                values[3] = editEmail.getText().toString();

                GetMethodHandler request = new GetMethodHandler(attributes,values,4,"https://haatprotidin.com/php_an/register.php", context);
                try {
                    request.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //String response = request.res;
                //System.out.println("login theke " + response);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, LoginPage.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void processFinish(String output) {
        System.out.println(output);
    }
}
