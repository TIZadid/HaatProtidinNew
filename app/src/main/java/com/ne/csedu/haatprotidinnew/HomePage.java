package com.ne.csedu.haatprotidinnew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ExecutionException;

public class HomePage extends Activity implements AsyncResponse{
    Context context;
    Button btnWholeseller,btnRetailer,btnagentsearch;
    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        String URL= "http://haatprotidin.com/php_an/sendTags.php";
        context = this;
        btnWholeseller=(Button)findViewById(R.id.btnWholeseller);
        btnRetailer=(Button)findViewById(R.id.btnRetailer);
        btnagentsearch = findViewById(R.id.btnAgentSearch);
        String attributes[]={};
        String values[]={};
        GetMethodHandler request = new GetMethodHandler(attributes,values,0,"https://haatprotidin.com/php_an/sendTags.php",context);
        try {
            request.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        btnWholeseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, SearchPage.class);
                startActivity(intent);
            }
        });
        btnagentsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this,Agentsearch.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void processFinish(String output) {

    }
}
