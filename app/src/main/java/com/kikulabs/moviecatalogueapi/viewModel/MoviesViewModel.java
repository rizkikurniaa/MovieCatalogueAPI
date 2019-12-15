package com.kikulabs.moviecatalogueapi.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.kikulabs.moviecatalogueapi.BuildConfig;
import com.kikulabs.moviecatalogueapi.model.MoviesAndTvData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MoviesViewModel extends ViewModel {
    private static final String apiKey = BuildConfig.TMDB_API_KEY;
    private MutableLiveData<ArrayList<MoviesAndTvData>> listMovies = new MutableLiveData<>();

    public void setMovies(final String movies) {
        final ArrayList<MoviesAndTvData> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + apiKey + "&language=en-US";

        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Sukses", "onResponse: " + response);

                        {

                            try {
                                JSONArray datalist = response.getJSONArray("results");
                                for (int i = 0; i < datalist.length(); i++) {
                                    JSONObject data = datalist.getJSONObject(i);
                                    MoviesAndTvData movieItems = new MoviesAndTvData();
                                    movieItems.setTitle(data.getString("original_title"));
                                    movieItems.setPoster(data.getString("poster_path"));
                                    movieItems.setReleaseDate(data.getString("release_date"));
                                    movieItems.setVoteAverage(data.getString("vote_average"));
                                    movieItems.setLanguage(data.getString("original_language"));
                                    movieItems.setOverview(data.getString("overview"));
                                    listItems.add(movieItems);

                                }
                                listMovies.postValue(listItems);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.d("Error", "onError: " + error);
                    }
                });

    }

    public LiveData<ArrayList<MoviesAndTvData>> getMovies() {
        return listMovies;
    }
}
