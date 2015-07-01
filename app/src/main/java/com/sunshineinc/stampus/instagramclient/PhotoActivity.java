package com.sunshineinc.stampus.instagramclient;

import android.os.Debug;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
//client id 7fbb7d6ac8af435ab53e81ba218d10c1
    //endpoint url https://api.instagram.com/v1/media/popular?client_id=CLIENT-ID
    //popular url: https://api.instagram.com/v1/media/popular?access_token=ACCESS-TOKEN



public class PhotoActivity extends ActionBarActivity {

    public static final String CLIENT_ID = "7fbb7d6ac8af435ab53e81ba218d10c1";
    private ArrayList<PhotoItem> photoList;
    private PhotoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        photoList = new ArrayList<PhotoItem>();
        fetchPopularPhotos();
        adapter = new PhotoListAdapter(getBaseContext(), photoList);
        ListView lvPhoto = (ListView)findViewById(R.id.lvPhotos);
        lvPhoto.setAdapter(adapter);
    }

    public void fetchPopularPhotos(){
        String url = "https://api.instagram.com/v1/media/popular?client_id="+ CLIENT_ID;
        AsyncHttpClient asyncClient = new AsyncHttpClient();
        asyncClient.get(url, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                PhotoItem photo;
                JSONArray photosJSON = null;
                try{
                    photosJSON =response.getJSONArray("data");
                    for(int i = 0; i<photosJSON.length(); i++){
                        photo = new PhotoItem();
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        //Log.i("DEBUG", photoJSON.toString());
                        photo.setUrl(photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                        photo.setAuthor(photoJSON.getJSONObject("user").getString("username"));
                        if(photoJSON.has("caption")&&!photoJSON.isNull("caption")){
                            photo.setCaption(photoJSON.getJSONObject("caption").getString("text"));
                        }else{
                            photo.setCaption("");
                        }
                        photo.setLikes(photoJSON.getJSONObject("likes").getInt("count"));
                        photoList.add(photo);
                    }
                    Log.i("DEBUG", "exit for loop");
                }catch(JSONException e){
                    e.printStackTrace();
                }
                Log.i("DEBUG", response.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("DEBUG", "onFailure reached");
            }
        });
        Log.i("DEBUG", "finished async call");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
