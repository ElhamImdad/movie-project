package com.example.smoot.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewResult ;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResult = findViewById(R.id.text_view_result);
        Button button_cards_of_movies = findViewById(R.id.button_cards_of_movies);
        mQueue = Volley.newRequestQueue(this);
        button_cards_of_movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParse();
            }
        });

    }

    private void jsonParse(){
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=b135b1b6b41176924f60581906216e59&language=en-US&page=1";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject results = jsonArray.getJSONObject(i);

                                int voteCount = results.getInt("vote_count");
                                int id = results.getInt("id");
                                boolean vid = results.getBoolean("video");
                                double voteAverage = results.getDouble("vote_average");
                                String title = results.getString("title");
                                double popularity = results.getDouble("popularity");
                                String posterPath = results.getString("poster_path");
                                String originalLanguage = results.getString("original_language");
                                String originalTitle = results.getString("original_title");
                              //  JSONArray genreIds = results.getJSONArray("genre_ids");
                              //  for(int j=0; j<genreIds.length(); j++){
                                ///    if(Constants.LOG)Log.d("","Column "+genreIds.getJSONObject(i).toString());
                             //   }
                             //   int [] genreIds = results.getInt("genre_ids");
                                String backdropPath = results.getString("backdrop_path");
                                boolean adult = results.getBoolean("adult");
                                String overview = results.getString("overview");
                                String releaseDate = results.getString("release_date");

                                mTextViewResult.append(String.valueOf(voteCount)+", "+ String.valueOf(id)+", "+
                                        String.valueOf(vid)+", "+String.valueOf(voteAverage)+", "+
                                        title+", "+String.valueOf(popularity)+", "+posterPath+", "+originalLanguage+", "+
                                        originalTitle+", "+backdropPath+", "+
                                        String.valueOf(adult)+", "+overview+", "+releaseDate+"\n\n ");

                            }
                        } catch (JSONException excep) {
                            excep.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}
