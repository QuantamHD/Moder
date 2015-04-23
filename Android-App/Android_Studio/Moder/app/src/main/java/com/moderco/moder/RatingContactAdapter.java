package com.moderco.moder;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.provider.Telephony;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.moderco.model.LoadingPhotosAsync;
import com.moderco.utility.Moder;
import com.moderco.utility.RateCardInformation;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Ethan on 4/8/2015.
 */
public class RatingContactAdapter extends RecyclerView.Adapter<RatingContactAdapter.ViewHolderRate>{
    private int size = 1;
    private Handler handle;
    private boolean out_of_photos = false;


    public static class ViewHolderRate extends RecyclerView.ViewHolder{
        RatingContactAdapter a;
        public final ImageView img;
        private Boolean isLoading = false;
        private TextView description;
        final ImageView img2;
        final TextView tview;


        public void setDescription(String description) {
            this.description.setText(description);
        }

        public ViewHolderRate(View v,RatingContactAdapter adapter) {
            super(v);
            a = adapter;
            final AsyncHttpClient client = Moder.client;
            final Animation ab = AnimationUtils.loadAnimation(v.getContext(), R.anim.rotate_img);
            img2 = (ImageView) v.findViewById(R.id.LoadingIcon);
            tview = (TextView) v.findViewById(R.id.LoadingText);
            description = (TextView) v.findViewById(R.id.description_rate);
            img2.setAnimation(ab);
            img2.setVisibility(View.GONE);
            tview.setVisibility(View.GONE);
            img = (ImageView) v.findViewById(R.id.main_img_card_rate);
            Button likeButton = (Button) v.findViewById(R.id.like_button_rate);
            Button dislikeButton = (Button) v.findViewById(R.id.cancel_profile);
            img.setImageDrawable(null);
            img2.setAnimation(ab);
            img2.setVisibility(View.VISIBLE);
            tview.setVisibility(View.VISIBLE);
            isLoading = true;

            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!a.cardDataSet.isEmpty() && !isLoading){
                        String json = a.prefs.getString("rateq",null);
                        if(json!=null){
                            try {
                                JSONObject object = new JSONObject(json);
                                JSONObject object2 = new JSONObject();
                                JSONArray one = object.getJSONArray("photoIDs");
                                JSONArray two = object.getJSONArray("descriptions");
                                JSONArray three = new JSONArray();
                                JSONArray four = new JSONArray();


                                for(int i = 0; i < one.length(); i++){
                                    RateCardInformation information = new RateCardInformation(one.getString(i));
                                    information.setDescription(two.getString(i));
                                    a.cardDataSet.add(information);
                                    if(information.getId().equals(a.cardDataSet.peek().getId())){

                                        continue;
                                    }
                                    three.put(information.getId());
                                    four.put(information.getDescription());
                                }

                                object2.put("photoIDs",three);
                                object2.put("descriptions",four);
                                SharedPreferences.Editor editor =  a.prefs.edit();
                                editor.putString("rateq",object2.toString());
                                editor.commit();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        img2.setAnimation(null);
                        img2.setVisibility(View.GONE);
                        tview.setVisibility(View.GONE);
                        RequestParams params = new RequestParams();
                        params.add("photoID",a.cardDataSet.peek().getId());
                        params.add("choice","yes");
                        client.get("https://www.moderapp.com/SubmitRating", params, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                            }
                        });
                        a.cardDataSet.remove();
                        a.notifyItemChanged(0);
                        Log.v("Hello", a.cardDataSet.size() + "");
                        a.loadImages(1);

                        if(a.cardDataSet.size() == 0){
                            img.setImageDrawable(null);
                            img2.setAnimation(ab);
                            img2.setVisibility(View.VISIBLE);
                            tview.setVisibility(View.VISIBLE);
                            isLoading = true;
                            description.setText("");
                            LoadingPhotosAsync ac = new LoadingPhotosAsync(){
                                @Override
                                protected void onPostExecute(Void aVoid)
                                {
                                    img2.setAnimation(null);
                                    img2.setVisibility(View.GONE);
                                    tview.setVisibility(View.GONE);
                                    isLoading = false;
                                    a.notifyItemChanged(0);
                                }
                            };

                            ac.execute(a.cardDataSet);
                        }

                    }
                }
            });

            dislikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!a.cardDataSet.isEmpty() && !isLoading){
                        String json = a.prefs.getString("rateq",null);
                        if(json!=null){
                            try {
                                JSONObject object = new JSONObject(json);
                                JSONObject object2 = new JSONObject();
                                JSONArray one = object.getJSONArray("photoIDs");
                                JSONArray two = object.getJSONArray("descriptions");
                                JSONArray three = new JSONArray();
                                JSONArray four = new JSONArray();


                                for(int i = 0; i < one.length(); i++){
                                    RateCardInformation information = new RateCardInformation(one.getString(i));
                                    information.setDescription(two.getString(i));
                                    a.cardDataSet.add(information);
                                    if(information.getId().equals(a.cardDataSet.peek().getId())){

                                        continue;
                                    }
                                    three.put(information.getId());
                                    four.put(information.getDescription());
                                }

                                object2.put("photoIDs",three);
                                object2.put("descriptions",four);
                                SharedPreferences.Editor editor =  a.prefs.edit();
                                editor.putString("rateq",object2.toString());
                                editor.commit();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        img2.setAnimation(null);
                        img2.setVisibility(View.GONE);
                        tview.setVisibility(View.GONE);
                        RequestParams params = new RequestParams();
                        params.add("photoID",a.cardDataSet.peek().getId());
                        params.add("choice","no");
                        client.get("https://www.moderapp.com/SubmitRating",params, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                            }
                        });
                        a.cardDataSet.remove();
                        a.notifyItemChanged(0);
                        Log.v("Hello", a.cardDataSet.size() + "");
                        a.loadImages(1);
                        if(a.cardDataSet.size() == 0){
                            img.setImageDrawable(null);
                            img2.setAnimation(ab);
                            img2.setVisibility(View.VISIBLE);
                            tview.setVisibility(View.VISIBLE);
                            isLoading = true;
                            description.setText("");
                            LoadingPhotosAsync ac = new LoadingPhotosAsync(){
                                @Override
                                protected void onPostExecute(Void aVoid)
                                {
                                    img2.setAnimation(null);
                                    img2.setVisibility(View.GONE);
                                    tview.setVisibility(View.GONE);
                                    isLoading = false;
                                    a.notifyItemChanged(0);
                                }
                            };

                            ac.execute(a.cardDataSet);
                        }

                    }
                }
            });
        }
        public void setLoadingOff(){
            isLoading = false;
            tview.setVisibility(View.GONE);
            img2.setVisibility(View.GONE);
        }

    }

    public BlockingQueue<RateCardInformation> cardDataSet;
    private ImageLoader imageLoader;
    public ArrayList<Object> a;
    private AsyncHttpClient client;
    final SharedPreferences prefs = Moder.getPrefs();
    public RatingContactAdapter(){


        a = new ArrayList<>();
        imageLoader = Moder.getImageLoader();
        client = Moder.getClient();
        cardDataSet = new LinkedBlockingDeque<>();

        String json = prefs.getString("rateq",null);
        if(json!=null){
            try {
                JSONObject object = new JSONObject(json);
                JSONArray one = object.getJSONArray("photoIDs");
                JSONArray two = object.getJSONArray("descriptions");


                for(int i = 0; i < one.length(); i++){
                    RateCardInformation information = new RateCardInformation(one.getString(i));
                    information.setDescription(two.getString(i));
                    cardDataSet.add(information);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(cardDataSet.size() < 30) {
            loadImages(20);
        }

    }


    @Override
    public RatingContactAdapter.ViewHolderRate onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_rate, parent, false);
        final ViewHolderRate rate =new ViewHolderRate(itemView,this);
        LoadingPhotosAsync async = new LoadingPhotosAsync() {
            @Override
            protected Void doInBackground(BlockingQueue<RateCardInformation>... params) {
                BlockingQueue<RateCardInformation> cards = params[0];
                while(cards.size() < 5){

                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                notifyItemChanged(0);
                rate.setLoadingOff();
                Log.v("This Happened It","Readdy");
            }
        };

        async.execute(cardDataSet);
        return rate;
    }

    @Override
    public void onBindViewHolder(final ViewHolderRate holder, int position) {
        if(cardDataSet.isEmpty()){
            holder.img.setImageDrawable(null);
        }else {
            holder.setLoadingOff();
            final RateCardInformation rateCardInformation = cardDataSet.peek();
            if(rateCardInformation == null){
                holder.img.setImageDrawable(null);
                return;
            }
            imageLoader.displayImage(rateCardInformation.getURL(),holder.img, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    cardDataSet.remove();
                    loadImages(1);
                    notifyItemChanged(0);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    holder.setDescription(rateCardInformation.getDescription());
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    if(!cardDataSet.isEmpty())
                        cardDataSet.remove();
                    loadImages(1);
                    notifyItemChanged(0);
                }
            });
        }
    }

    public int getItemCount() {
        return 1;
    }

    public void init(final ViewHolderRate v){
        client.get("https://www.moderapp.com/getrate",new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonString = new String(responseBody);
                try {
                    JSONObject object = new JSONObject(jsonString);
                    int responseCode = object.getInt("ResponseCode");
                    if(responseCode == 300){
                        String photoID = object.getString("PhotoID");
                        String description =object.getString("Description");
                        final RateCardInformation cardInformation = new RateCardInformation(photoID);
                        imageLoader.displayImage(cardInformation.getURL(),v.img , new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String imageUri, View view) {

                            }

                            @Override
                            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                                init(v);
                            }

                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                cardDataSet.add(cardInformation);
                            }

                            @Override
                            public void onLoadingCancelled(String imageUri, View view) {
                                init(v);
                            }
                        });
                    }else if(responseCode == 202){
                        final SharedPreferences prefs = Moder.getPrefs();
                        RequestParams params = new RequestParams();
                        params.add("email", prefs.getString("email", null));
                        params.add("pwd", prefs.getString("pwd", null));
                        client.get("https://www.moderapp.com/login", params, new AsyncHttpResponseHandler() {

                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                                String s = new String(response);
                                try {
                                    JSONObject object = new JSONObject(s);
                                    int responseCode = object.getInt("ResponseCode");
                                    switch (responseCode) {
                                        case 300: {
                                            loadImages(1);
                                            break;
                                        }
                                        case 100: {
                                            break;
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                            }

                            @Override
                            public void onRetry(int retryNo) {

                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                init(v);
            }
        });
    }

    public void loadImages(final int count){
        for(int i = 0; i < count; i++){
            final int a = i;
            client.get("https://www.moderapp.com/getrate",new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    final String jsonString = new String(responseBody);
                    try {
                        JSONObject object = new JSONObject(jsonString);
                        int responseCode = object.getInt("ResponseCode");
                        if(responseCode == 300){
                            String photoID = object.getString("PhotoID");
                            String description =object.getString("Description");
                            final RateCardInformation cardInformation = new RateCardInformation(photoID);
                            cardInformation.setDescription(description);
                            boolean inList = false;
                            for(RateCardInformation card : cardDataSet){
                                if(card.getId().equals(cardInformation.getId())){
                                    inList = true;
                                }
                            }
                            if(inList) {
                                loadImages(1);
                                return;
                            }
                            imageLoader.loadImage(cardInformation.getURL(),new ImageLoadingListener() {
                                @Override
                                public void onLoadingStarted(String imageUri, View view) {

                                }

                                @Override
                                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                                    Log.v("Load", "Images Method");
                                    if(cardDataSet.size() < 20) {
                                        loadImages(2);
                                    }
                                }

                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    Log.v("Loading" , cardDataSet.size()+"");
                                    String json = prefs.getString("rateq","{\"photoIDs\":[],\"descriptions\":[]}");
                                    if(true){
                                        try {
                                            JSONObject old = new JSONObject(json);
                                            JSONArray one = old.getJSONArray("photoIDs");
                                            JSONArray two = old.getJSONArray("descriptions");
                                            one.put(cardInformation.getId());
                                            two.put(cardInformation.getDescription());


                                            JSONObject neww = new JSONObject();
                                            neww.put("photoIDs",one);
                                            neww.put("descriptions", two);

                                            SharedPreferences.Editor editor =  prefs.edit();
                                            editor.putString("rateq",neww.toString());
                                            editor.commit();


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    cardDataSet.add(cardInformation);
                                }

                                @Override
                                public void onLoadingCancelled(String imageUri, View view) {
                                    if(cardDataSet.size() < 20) {
                                        loadImages(2);
                                    }
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    loadImages(1);
                }
            });
        }
    }
}
