package com.ne.csedu.haatprotidinnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class DokanSearh extends AppCompatActivity implements AsyncResponse{
    TextView textSearch;
    ListView listDokan;

    private RecyclerView htRecyclerView;
    private HorTagRecyclerViewAdapter htAdapter;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    StaggeredAdapter staggeredRecyclerViewAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    Context context;
    ArrayList<String> currentFilters = new ArrayList<>();
    ArrayList<String> categoryOptions = new ArrayList<String>();
    ArrayList<Dokaninfo> dokaninfos = new ArrayList<Dokaninfo>();
    Spinner edittype;
    Button SearchDokan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokan_searh);

        context = this;

        //hortag start
        SearchDokan =findViewById(R.id.btDokanSearch);
        textSearch = findViewById(R.id.etSearchDokan);
//duibar ken?


        edittype = findViewById(R.id.ettype_v2);
        listDokan = findViewById(R.id.listdokan);

        Intent fromhomeintent = getIntent();

        ArrayList<String> temp = new ArrayList<>();
        temp = fromhomeintent.getStringArrayListExtra("category");
        categoryOptions.add(0,"Any");
        categoryOptions.add("Wholeseller");
        categoryOptions.add("Retailer");


        currentFilters = getIntent().getStringArrayListExtra("currentFilters");
        if(currentFilters == null) currentFilters = new ArrayList<>();
        System.out.println(currentFilters.size()+"saddasdasds\n\n\n");
        if(currentFilters.size()>0)
        {
            for(int ind=0;ind<currentFilters.size();ind++)
                System.out.println(currentFilters.get(ind));
        }
        Collections.sort(currentFilters);


        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categoryOptions);
        // Specify layout to be used when list of choices appears
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        edittype.setAdapter(typeAdapter);


        edittype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(currentFilters.contains(parent.getItemAtPosition(position)) && position !=0){
                        Toast.makeText(DokanSearh.this, categoryOptions.get(position) + " Already Selected", Toast.LENGTH_SHORT).show();
                    }else{
                        System.out.println(parent.getItemAtPosition(position));
                        currentFilters.clear();
                        Toast.makeText(DokanSearh.this, categoryOptions.get(position) + " selected", Toast.LENGTH_SHORT).show();
                        currentFilters.add(categoryOptions.get(position));
                        //parent.setSelection(0);
                    }

                System.out.println(currentFilters);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SearchDokan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("aste " + currentFilters);
                int categoryIndex = 0;
                String attributes[] = new String[50000];
                String values[] = new String[50000];

                attributes[0] = "search_text";
                if(textSearch.getText().toString().equals(""))values[0]="";
                values[0] = textSearch.getText().toString();

                attributes[1] = "dokan_type";
                if(currentFilters.size() == 0)values[1] = "";
                else values[1] = currentFilters.get(0);

                currentFilters.clear();




                for(int j=0; j<2;j++){
                    System.out.println("aguuun" + attributes[j] + " " + values[j]);
                }
                GetMethodHandler productRetriever = new GetMethodHandler(attributes, values, 2, "https://haatprotidin.com/php_an/dokan_list.php", context);
                try {
                    productRetriever.execute().get();
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
        System.out.println(output);
        try {
            JSONArray jsonArray= new JSONArray(output);
            //System.out.println(jsonArray.toString());
            int jsonlength = jsonArray.length();
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            System.out.println(jsonObject.toString() +"   " + jsonlength);
            if(jsonObject.get("status").toString().equals("-1")){
                Toast.makeText(DokanSearh.this, "Search Failed, Please try again.", Toast.LENGTH_SHORT).show();
            }else if (jsonlength == 1){
                Toast.makeText(DokanSearh.this, "No such Dokan!", Toast.LENGTH_SHORT).show();
            } else {

                for (int i = 1; i < jsonlength; i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    Dokaninfo newdokan = new Dokaninfo(jsonObject1.getString("name"), jsonObject1.getString("address"));
                    dokaninfos.add(newdokan);
                    System.out.println(jsonObject1.getString("name") + jsonObject1.getString("address"));
                    System.out.println(dokaninfos.get(i-1).name + dokaninfos.get(i-1).address);
                }
            }

            listviewadapter adapter = new listviewadapter(this,dokaninfos);
            listDokan.setAdapter(adapter);



        }catch (Exception err){
            Log.d("Error", err.toString());
        }
    }
}
