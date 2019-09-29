package com.ne.csedu.haatprotidinnew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        Button btnWholeseller=(Button)findViewById(R.id.btnWholeseller);
        Button btnRetailer=(Button)findViewById(R.id.btnRetailer);
        Button btnCustomer = findViewById(R.id.btnCustomer);

        btnWholeseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, SearchPage.class);
                startActivity(intent);
            }
        });
    }
}
