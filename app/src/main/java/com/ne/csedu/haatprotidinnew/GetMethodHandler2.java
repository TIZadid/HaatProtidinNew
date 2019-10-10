package com.ne.csedu.haatprotidinnew;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
interface AsyncResponse2 {
    void processFinish(String output);
}
public class GetMethodHandler2 extends AsyncTask<String, String, String>{

    MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    String attribute[], value[];
    int numberOfComponents;
    String initUrl;
    String res; // the result is stored in this


    AsyncResponse2 delegate ;
    public GetMethodHandler2(String initUrl, Context delegate){
        this.initUrl = initUrl;
        this.delegate = (AsyncResponse2) delegate;
    }


    @Override
    protected String doInBackground(String... strings) {
        JSONObject jsonObj = new JSONObject();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", "nana")
                .addFormDataPart("datetime", "mana")
                .build();

        Request request = new Request.Builder()
                .url(initUrl)
                .post(requestBody)
                .build();

        try {
            System.out.println("huh");
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
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
