package com.ne.csedu.haatprotidinnew;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
interface AsyncResponse {
    void processFinish(String output);
}
public class GetMethodHandler extends AsyncTask<String, String, String>{

    MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    String attribute[], value[];
    int numberOfComponents;
    String initUrl;
    String res; // the result is stored in this


    AsyncResponse delegate ;

    //public AsyncResponse delegate = null;

    //public GetMethodHandler(AsyncResponse delegate){
        //this.delegate = delegate;
    //}
    //delegate = null;
    public GetMethodHandler(String[] attribute, String[] value, int numberOfComponents, String initUrl, Context delegate){
        this.attribute = attribute;
        this.value = value;
        this.numberOfComponents = numberOfComponents;
        this.initUrl = initUrl;
        this.delegate = (AsyncResponse) delegate;
    }


    private static String bodyToString(final Request request){

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }


    @Override
    protected String doInBackground(String... strings) {
        JSONObject jsonObj = new JSONObject();

        /*for(int i=0; i<numberOfComponents; i++){
            try {
                jsonObj.put(attribute[i],value[i]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //RequestBody body = RequestBody.create(JSON,jsonObj.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        System.out.println("jacche " + bodyToString(request));*/

        HttpUrl.Builder urlBuilder = HttpUrl.parse(initUrl).newBuilder();
        for(int i=0; i<numberOfComponents; i++){
            urlBuilder.addQueryParameter(attribute[i], value[i]);
        }
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        System.out.println(url);

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                /*JSONObject object= null;
                String bobo="";
                try {
                    object = new JSONObject(response.body().string());
                    bobo=object.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(bobo.trim().length()<1) {
                    bobo = response.message();
                    System.out.println(" -> " + bobo);
                }
                else{
                    System.out.println("__ " + bobo);
                }*/
                return "response was unsuccesful, try again";
            }
            //System.out.println("message " + response.message() + " | body " + response.body().string());
            return response.body().string();
        } catch (Exception e) {
            System.out.println("error bole " + e);
        }
        return null;
    }

    protected void onPostExecute(String result){
        res = result;
        System.out.println("result? " + res);
        delegate.processFinish(result);
    }
}
