package com.moderco.moder;

import android.app.DownloadManager;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.moderco.model.LoadingPhotosAsync;
import com.moderco.utility.Moder;
import com.moderco.utility.ProfileCardInformation;
import com.moderco.utility.RateCardInformation;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Ethan on 3/30/2015.
 */
public class ProfileContactAdapter extends RecyclerView.Adapter<ProfileContactAdapter.ViewHolder>{
    private int currentCard;
    private int size;
    private ImageLoader loader;
    private Handler handle;
    private boolean allLoaded =false;
    private AsyncHttpClient client;



    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mainImg;
        private TextView descriptionText;
        private RelativeLayout buttons;
        private ProgressBar progressBar;
        private EditText descriptonEdit;
        private TextView percentText;
        public ProfileCardInformation info;
        private AsyncHttpClient client;
        private ProfileContactAdapter adapter;
        public int position = -1;
        public TextView viewedBy;

        public ViewHolder(View v, final ProfileContactAdapter adapte1r) {
            super(v);
            this.adapter = adapte1r;
            client = Moder.client;
            viewedBy = (TextView) v.findViewById(R.id.number_rated_profile);
            mainImg = (ImageView) v.findViewById(R.id.card_img_profile);
            descriptionText = (TextView) v.findViewById(R.id.description_rate);
            buttons = (RelativeLayout) v.findViewById(R.id.button_holder_profile);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
            descriptonEdit = (EditText) v.findViewById(R.id.description_edit_profile);
            percentText = (TextView) v.findViewById(R.id.text_percent_profile);
            Button submitButton = (Button) v.findViewById(R.id.submit_profile);


            descriptonEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    adapte1r.cardDataSet.get(getPosition()).setDescription(s.toString());
                }
            });

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    descriptonEdit.getWindowToken();
                    descriptonEdit.setFocusable(false);
                    hidSubmitSettings();
                    adapter.notifyItemChanged(getPosition());

                }
            });

            Button cancelButton = (Button) v.findViewById(R.id.cancel_profile);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    descriptonEdit.setFocusable(false);
                    adapte1r.cardDataSet.remove(getPosition());
                    adapte1r.notifyItemRemoved(getPosition());

                }
            });

        }

        public void revealSubmitSettings(){
            progressBar.setVisibility(View.GONE);
            percentText.setVisibility(View.GONE);
            viewedBy.setVisibility(View.GONE);
            descriptonEdit.setVisibility(View.VISIBLE);
            buttons.setVisibility(View.VISIBLE);
            descriptonEdit.setText(adapter.cardDataSet.get(getPosition()).getDescription());
        }

        public void defaultSettings(){
            progressBar.setVisibility(View.VISIBLE);
            percentText.setVisibility(View.VISIBLE);
            viewedBy.setVisibility(View.VISIBLE);
            descriptionText.setVisibility(View.VISIBLE);
            descriptonEdit.setVisibility(View.GONE);
            buttons.setVisibility(View.GONE);
        }

        public void hidSubmitSettings(){
            progressBar.setVisibility(View.VISIBLE);
            percentText.setVisibility(View.VISIBLE);
            viewedBy.setVisibility(View.VISIBLE);
            descriptionText.setVisibility(View.VISIBLE);
            descriptionText.setText(adapter.cardDataSet.get(getPosition()).getDescription());
            descriptonEdit.setVisibility(View.GONE);
            buttons.setVisibility(View.GONE);
            adapter.cardDataSet.get(getPosition()).setSubmited(true);
            final RequestParams params = new RequestParams();
            final int pos = getPosition();

            Moder.getImageLoader().loadImage(adapter.cardDataSet.get(getPosition()).getLocalURI().toString(), new ImageLoadingListener() {
                int pos2;
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    pos2 = pos;
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, final Bitmap loadedImage) {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    loadedImage.compress(Bitmap.CompressFormat.JPEG, 50, bos);
                    byte[] bitmapdata = bos.toByteArray();
                    ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
                    params.put("file", bs);
                    params.put("description", adapter.cardDataSet.get(pos2).getDescription());
                    client.post("https://www.moderapp.com/submitphoto", params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String s = new String(responseBody);
                            try {
                                JSONObject object = new JSONObject(s);
                                String id = object.getString("file");
                                adapter.cardDataSet.get(pos2).setId(id);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        }
                    });
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });


        }

        public ImageView getMainImg() {
            return mainImg;
        }
    }

    public final List<ProfileCardInformation> cardDataSet;
    public RecyclerView view3;
    public View viewp;
    public ProfileContactAdapter(RecyclerView v,final View b1){
        client = Moder.getClient();
        loader = Moder.getImageLoader();
        cardDataSet = new Vector<>();
        handle = new Handler();
        view3 = v;
        viewp = b1;

        client.get("https://www.moderapp.com/profile", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);
                try {

                    JSONObject object = new JSONObject(s);
                    int oldSize = getItemCount();
                    int responseCode = object.getInt("ResponseCode");
                    if(responseCode == 300){
                        JSONArray photoList = object.getJSONArray("UUID_Photo");
                        JSONArray rateDowns = object.getJSONArray("Rate_Downs");
                        JSONArray rateUps   = object.getJSONArray("Rate_Ups");
                        JSONArray descriptions   = object.getJSONArray("Descriptions");
                        if(photoList.length() == 0){
                            allLoaded = true;
                            TextView t = (TextView) b1.findViewById(R.id.tester_text);
                            t.setVisibility(View.VISIBLE);
                        }
                        for(int i = 0; i < photoList.length(); i++){
                            ProfileCardInformation card = new ProfileCardInformation(photoList.getString(i),rateUps.getInt(i),rateDowns.getInt(i));
                            card.setDescription(descriptions.getString(i));
                            cardDataSet.add(card);
                            notifyItemInserted(getItemCount());
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void updateCardInfo(final SwipeRefreshLayout layout){
        int size  = (int) Math.ceil(((double)cardDataSet.size())/10.0);
        for(int i = 0; i < size;i++){
            client.get("https://www.moderapp.com/profile", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String s = new String(responseBody);
                    try {

                        JSONObject object = new JSONObject(s);
                        int oldSize = getItemCount();
                        int responseCode = object.getInt("ResponseCode");
                        if(responseCode == 300){
                            JSONArray photoList = object.getJSONArray("UUID_Photo");
                            JSONArray rateDowns = object.getJSONArray("Rate_Downs");
                            JSONArray rateUps   = object.getJSONArray("Rate_Ups");

                            JSONArray descriptions = object.getJSONArray("Descriptions");
                            if(photoList.length() == 0){
                                allLoaded = true;
                            }
                            for(int i = 0; i < photoList.length(); i++){
                                ProfileCardInformation card = new ProfileCardInformation(photoList.getString(i),rateUps.getInt(i),rateDowns.getInt(i));
                                card.setDescription(descriptions.getString(i));
                                for(int z = 0; z < cardDataSet.size(); z++){
                                    if(cardDataSet.get(z).getId() == null){
                                        continue;
                                    }
                                    if(cardDataSet.get(z).getId().equals(card.getId())){
                                        cardDataSet.get(z).setRateDown(card.getRateDown());
                                        cardDataSet.get(z).setRateUp(card.getRateUp());
                                        cardDataSet.get(z).setDescription(card.getDescription());
                                    }
                                }
                            }
                            layout.setRefreshing(false);
                            notifyItemRangeChanged(0, cardDataSet.size());
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
                                                updateCardInfo(layout);
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

                }
            });
        }
    }

    public void uploadNewCardLayout(File file){
        ProfileCardInformation cardInformation = new ProfileCardInformation();
        cardInformation.setLocalFile(file);
        cardInformation.setSubmited(false);
        cardDataSet.add(0, cardInformation);
        notifyItemInserted(0);
        view3.smoothScrollToPosition(0);
        view3.smoothScrollToPosition(0);
        TextView t = (TextView) viewp.findViewById(R.id.tester_text);
        t.setVisibility(View.GONE);
    }

    @Override
    public ProfileContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(final ProfileContactAdapter.ViewHolder holder, final int position) {

        if(position == getItemCount()-2 && !allLoaded){
            client.get("https://www.moderapp.com/profile?start="+getItemCount(), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String s = new String(responseBody);
                    try {
                        JSONObject object = new JSONObject(s);
                        int responseCode = object.getInt("ResponseCode");
                        int oldSize = getItemCount();
                        if(responseCode == 300){
                            JSONArray photoList = object.getJSONArray("UUID_Photo");
                            JSONArray rateDowns = object.getJSONArray("Rate_Downs");
                            JSONArray rateUps   = object.getJSONArray("Rate_Ups");
                            JSONArray description = object.getJSONArray("Descriptions");
                            if(photoList.length() == 0){
                                allLoaded = true;
                            }
                            for(int i = 0; i < photoList.length(); i++){
                                ProfileCardInformation card = new ProfileCardInformation(photoList.getString(i),rateUps.getInt(i),rateDowns.getInt(i));
                                card.setDescription(description.getString(i));
                                cardDataSet.add(card);
                                notifyItemInserted(getItemCount());
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        }

        ProfileCardInformation card = cardDataSet.get(position);
        if(cardDataSet.get(position).getId() != null) {//NON-FILE PHOTOS
            loader.displayImage(card.getURL(), holder.getMainImg());
            holder.defaultSettings();
            holder.descriptionText.setText(cardDataSet.get(position).getDescription());
            holder.progressBar.setProgress(cardDataSet.get(position).getPercent());
            holder.percentText.setText(cardDataSet.get(position).getPercent() + "%");
            holder.viewedBy.setText((cardDataSet.get(position).getRateDown()+cardDataSet.get(position).getRateUp()) + " people rated your outfit");
        }
        else{//FILE PHOTOS

            if(!cardDataSet.get(position).isSubmited()){
                holder.revealSubmitSettings();
                holder.position = position;
                holder.descriptonEdit.setText(cardDataSet.get(position).getDescription());
                holder.descriptionText.setText(cardDataSet.get(position).getDescription());
                holder.percentText.setText(cardDataSet.get(position).getPercent() + "%");
                holder.viewedBy.setText((cardDataSet.get(position).getRateDown()+cardDataSet.get(position).getRateUp()) + " people rated your outfit");
                holder.progressBar.setProgress(cardDataSet.get(position).getPercent());
            }else{
                holder.defaultSettings();
                holder.descriptionText.setText(cardDataSet.get(position).getDescription());
                holder.percentText.setText(cardDataSet.get(position).getPercent() + "%");
                holder.viewedBy.setText((cardDataSet.get(position).getRateDown()+cardDataSet.get(position).getRateUp()) + " people rated your outfit");
                holder.progressBar.setProgress(cardDataSet.get(position).getPercent());
            }
            loader.displayImage(cardDataSet.get(position).getLocalURI().toString(),holder.getMainImg(),new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return cardDataSet.size();
    }


}
