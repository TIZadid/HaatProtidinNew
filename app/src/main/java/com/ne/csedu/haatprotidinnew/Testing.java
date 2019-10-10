package com.ne.csedu.haatprotidinnew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ExecutionException;

public class Testing extends AppCompatActivity implements AsyncResponse2{

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        context = this;

        Button Janina = findViewById(R.id.btnjanina);

        Janina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetMethodHandler2 gt = new GetMethodHandler2("https://haatprotidin.com/php_an/hudai.php",context);
                try {
                    gt.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void processFinish(String output) {

    }
}
