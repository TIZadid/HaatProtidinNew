package com.ne.csedu.haatprotidinnew;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
interface AsyncResponseImage {
    void processFinish(String output);
}
public class imageSendingHandler extends AsyncTask<String, String, String>{

    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_JPG=MediaType.parse("image/jpg");

    OkHttpClient client = new OkHttpClient();
    String initUrl;
    String res; // the result is stored in this
    File file;


    AsyncResponseImage delegate ;
    public imageSendingHandler(String initUrl, File file, Context delegate){
        this.initUrl = initUrl;
        this.file = file;
        this.delegate = (AsyncResponseImage) delegate;
        if(file.exists()){
            System.out.println("ooppa e ase");
        }else{
            System.out.println("ooppa e nai");
        }
    }


    @Override
    protected String doInBackground(String... strings) {
        JSONObject jsonObj = new JSONObject();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("img", "no_idea", RequestBody.create(MEDIA_TYPE_JPG, file))
                .addFormDataPart("hunk", "punk")
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
