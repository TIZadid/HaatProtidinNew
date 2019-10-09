package com.ne.csedu.haatprotidinnew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HomePage extends Activity implements AsyncResponse{
    Context context;
    Button btnWholeseller,btnRetailer,btnagentsearch;
    String result;

    ArrayList<String> locationTagList =  new ArrayList<String>();
    ArrayList<String> colorTagLIst =  new ArrayList<String>();
    ArrayList<String> categoryTagList =  new ArrayList<String>();

    private Intent fromHomeIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        fromHomeIntent = getIntent();

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
                intent.putExtra("location", locationTagList);
                intent.putExtra("color",colorTagLIst);
                intent.putExtra("category",categoryTagList);
                intent.putExtra("dokan_type","Wholeseller");
                intent.putExtra("phone_no",fromHomeIntent.getStringExtra("phone_no"));

                startActivity(intent);
            }
        });
        btnRetailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this,SearchPage.class);
                intent.putExtra("location", locationTagList);
                intent.putExtra("color",colorTagLIst);
                intent.putExtra("category",categoryTagList);
                intent.putExtra("dokan_type","Retailer");
                intent.putExtra("phone_no",fromHomeIntent.getStringExtra("phone_no"));

                startActivity(intent);
            }
        });
        btnagentsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this,Agentsearch.class);
                intent.putExtra("location",locationTagList);
                startActivity(intent);
            }
        });
    }

    @Override
    public void processFinish(String output) {
        System.out.println(output);
        try {
            JSONArray jsonArray= new JSONArray(output);
            //System.out.println(jsonArray.toString());
            locationTagList.add(0,"select a category");
            colorTagLIst.add(0,"select a category");
            categoryTagList.add(0,"select a category");
            int j=0,k=0,l=0;
            int jsonlength = jsonArray.length();
            for(int i=0;i<jsonlength;i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.get("type").toString().equals("location")){
                    locationTagList.add(jsonObject.get("location_name").toString());
                }
                else if(jsonObject.get("type").toString().equals("color")){
                    colorTagLIst.add(jsonObject.get("color_name").toString());
                }
                else if(jsonObject.get("type").toString().equals("category")){
                    categoryTagList.add(jsonObject.get("category_name").toString());
                }
                j++;k++;l++;
            }


        }catch (Exception err){
            Log.d("Error", err.toString());
        }
    }
}
