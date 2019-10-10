package com.ne.csedu.haatprotidinnew;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.InputStream;
import java.util.ArrayList;
interface Asynchresponsev2{
    void processFinish(Bitmap output);
}
public class Imagehandler extends AsyncTask<String, Void, Bitmap>{
    Context imagecontext;
    Bitmap bitmap;
    String Imageurls;
    RequestOptions requestOptions;
    StaggeredAdapter.ViewHolder viewholder;
    Asynchresponsev2 delegate;
    int imageposition;
    Imagehandler(Context context, String imageurl)
    {
        this.delegate = (Asynchresponsev2) context;
        this.Imageurls = imageurl;
    }
    @Override
    protected Bitmap doInBackground(String... strings) {
        String imageurl = Imageurls;
        bitmap = null;
        try {
            InputStream in = new java.net.URL(imageurl).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result){
        System.out.println("result? " + result);
        delegate.processFinish(result);
    }


}
