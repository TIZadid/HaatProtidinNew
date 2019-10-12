package com.ne.csedu.haatprotidinnew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Profile extends Activity implements AsyncResponse{
    TextView textUsername,textUseremail,textcontact,textbalance;
    ListView Dokanlist;
    String Dokantype;
    String Phone_no;
    Intent fromhomeintent;
    Context context;
    String name="",email="",phone="",balance="";
    ArrayList<String> Dokanname = new ArrayList<String>();
    ArrayList<String> Dokanaddress = new ArrayList<String>();

    ArrayList<Dokaninfo> dokaninfos = new ArrayList<Dokaninfo>();

    String URL= "https://haatprotidin.com/php_an/user_profile.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textUsername = findViewById(R.id.textusername);
        textUseremail = findViewById(R.id.profileMail);
        textcontact = findViewById(R.id.profilePhone);
        textbalance = findViewById(R.id.Balance);
        Dokanlist = findViewById(R.id.listdokan);
        context = this;
        fromhomeintent = getIntent();

        Dokantype = fromhomeintent.getStringExtra("dokan_type");
        Phone_no = fromhomeintent.getStringExtra("phone_no");
        System.out.println(Phone_no);

        String attributes[] = new String[5000];
        String values[] = new String[5000];

        attributes[0] = "phone_no";
        values[0] = "123";
        GetMethodHandler request = new GetMethodHandler(attributes,values,1,URL,context);
        try {
            request.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void processFinish(String output) {
        System.out.println(output);
        try {
            JSONArray jsonArray= new JSONArray(output);
            //System.out.println(jsonArray.toString());
            int jsonlength = jsonArray.length();
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            System.out.println(jsonObject.toString() +"   " + jsonlength);
            if(jsonObject.get("status").toString().equals("-1")){
                Toast.makeText(Profile.this, "Search Failed, Please try again.", Toast.LENGTH_SHORT).show();
            }else if (jsonlength == 1){
                Toast.makeText(Profile.this, "No such Dokan!", Toast.LENGTH_SHORT).show();
            } else {
                jsonObject = jsonArray.getJSONObject(1);
                name = jsonObject.getString("name");
                email = jsonObject.getString("email");
                phone = jsonObject.getString("phone_no");
                balance = jsonObject.getString("balance");

                System.out.println("habijabi "+name+"   " +email+ "   "+ phone+ "   " +balance);

                for (int i = 2; i < jsonlength; i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    Dokaninfo newdokan = new Dokaninfo(jsonObject1.getString("name"), jsonObject1.getString("address"));
                    Dokanname.add(jsonObject1.getString("name"));
                    Dokanname.add(jsonObject1.getString("address"));
                    dokaninfos.add(newdokan);
                    System.out.println(jsonObject1.getString("name") + jsonObject1.getString("address"));
                    System.out.println(dokaninfos.get(i-2).name + dokaninfos.get(i-2).address);
                }
            }
            System.out.println("habijabi "+name+"   " +email+ "   "+ phone+ "   " +balance);
            textUsername.setText(name);
            textUseremail.setText(email);
            textbalance.setText(balance);
            textcontact.setText(phone);
            int size = dokaninfos.size();
            for(int i=0;i<size;i++){
                System.out.println(dokaninfos.get(i).name + dokaninfos.get(i).address);
            }

            listviewadapter adapter = new listviewadapter(this,dokaninfos);
            Dokanlist.setAdapter(adapter);



        }catch (Exception err){
            Log.d("Error", err.toString());
        }

    }
}
