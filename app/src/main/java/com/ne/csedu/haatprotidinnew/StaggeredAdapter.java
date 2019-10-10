package com.ne.csedu.haatprotidinnew;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class StaggeredAdapter extends  RecyclerView.Adapter<StaggeredAdapter.ViewHolder> {

    private static final String TAG = "StaggeredAdapter";
    private ArrayList<String> mImageURL=new ArrayList<>();

    private ArrayList<String> mNames=new ArrayList<>();
    private ArrayList<String> mPrice=new ArrayList<>();
    private ArrayList<String> mID = new ArrayList<>();
    private Context mContext;
    Bitmap bitmap;
   // private Context staggeredcontext;

    public StaggeredAdapter(ArrayList<String> mNames, ArrayList<String> mPrice, ArrayList<String> mImageURL, ArrayList<String> mID, Context mContext) {
        this.mNames = mNames;
        this.mPrice = mPrice;
        this.mImageURL = mImageURL;
        this.mID = mID;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.preference_adapter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        String imageurl = mImageURL.get(position);
        System.out.println(imageurl);

       /* try{
            Imagehandler imageload = new Imagehandler(mContext,imageurl);
            imageload.execute().get();
        }catch (Exception e){
            System.out.println("problem Problem problem");
        }*/
        Glide.with(mContext)
                .load(imageurl)
                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.productImage);

        holder.productName.setText(mNames.get(position));
        holder.productPrice.setText(mPrice.get(position));

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: on : " + mNames.get(position) );
                Toast.makeText(mContext,mNames.get(position),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext,ProductPage.class);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

/*    @Override
    public void processFinish(Bitmap output) {
            bitmap = output;
    }*/

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        CardView card;


        public ViewHolder(View itemView) {
            super(itemView);
            this.productImage=itemView.findViewById(R.id.productImage);
            this.productName=itemView.findViewById(R.id.productName);
            this.productPrice=itemView.findViewById(R.id.productPrice);
            this.card=itemView.findViewById(R.id.card);

        }

        @Override
        public void onClick(View view) {

        }
    }
}



