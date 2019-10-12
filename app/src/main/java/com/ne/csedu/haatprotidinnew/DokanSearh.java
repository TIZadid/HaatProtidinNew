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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class DokanSearh extends AppCompatActivity implements AsyncResponse{
    TextView textSearch;

    private RecyclerView htRecyclerView;
    private HorTagRecyclerViewAdapter htAdapter;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    StaggeredAdapter staggeredRecyclerViewAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    Context context;
    ArrayList<String> currentFilters = new ArrayList<>();
    ArrayList<String> categoryOptions = new ArrayList<String>();
    Spinner edittype;
    Button SearchDokan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokan_searh);

        context = this;

        //hortag start
        htRecyclerView = findViewById(R.id.hor_tag_recycler_view_v2);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        htRecyclerView.setLayoutManager(layoutManager);
        SearchDokan =findViewById(R.id.btDokanSearch);
        textSearch = findViewById(R.id.etSearchtext);

        htAdapter = new HorTagRecyclerViewAdapter(currentFilters,this);
        htRecyclerView.setAdapter(htAdapter);

        edittype = findViewById(R.id.ettype_v2);

        Intent fromhomeintent = getIntent();

        ArrayList<String> temp = new ArrayList<>();
        temp = fromhomeintent.getStringArrayListExtra("category");
        categoryOptions.add(0,"select a option");
        categoryOptions.add("Wholeseller");
        categoryOptions.add("Retailer");
        categoryOptions.add("Any");

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
                if(parent.getItemAtPosition(position).equals("Category")){
                    System.out.println("mammmmaaa mojaaaaa");
                }
                else {
                    if(currentFilters.contains(parent.getItemAtPosition(position))){
                        Toast.makeText(DokanSearh.this, categoryOptions.get(position) + " Already Selected", Toast.LENGTH_SHORT).show();
                    }else{
                        System.out.println(parent.getItemAtPosition(position));
                        Toast.makeText(DokanSearh.this, categoryOptions.get(position) + " selected", Toast.LENGTH_SHORT).show();
                        currentFilters.add(categoryOptions.get(position));
                        htAdapter.update(currentFilters);
                        parent.setSelection(0);
                    }
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

                attributes[1] = "search_text";
                if(edittype.getSelectedItem().toString().equals("select a option"))values[1] = "";
                else
                    values[1] = edittype.getSelectedItem().toString();




                for(int j=0; j<currentFilters.size()+1;j++){
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

    }
}
