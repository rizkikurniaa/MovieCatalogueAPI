package com.kikulabs.moviecatalogueapi.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.kikulabs.moviecatalogueapi.R;
import com.kikulabs.moviecatalogueapi.model.MoviesAndTvData;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_FILM = "extra_film";
    private ProgressBar progressBar;
    private ConstraintLayout cl;
    private TextView tvTitle, tvRelease, tvRating, tvLang, tvOverview;
    private ImageView ivPoster;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tv_title);
        tvRelease = findViewById(R.id.tv_release_date);
        tvRating = findViewById(R.id.tv_ratingscore);
        tvLang = findViewById(R.id.tv_language);
        tvOverview = findViewById(R.id.tv_overview);
        ivPoster = findViewById(R.id.iv_film);
        progressBar = findViewById(R.id.progressBar);
        cl = findViewById(R.id.cl);

        cl.setVisibility(View.GONE);

        handler = new Handler();

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }

                handler.post(new Runnable() {
                    public void run() {
                        MoviesAndTvData selectedFilm = getIntent().getParcelableExtra(EXTRA_FILM);

                        if (selectedFilm != null) {
                            String url_image = "https://image.tmdb.org/t/p/w185" + selectedFilm.getPoster();
                            tvTitle.setText(selectedFilm.getTitle());
                            tvRelease.setText(selectedFilm.getReleaseDate());
                            tvRating.setText(selectedFilm.getVoteAverage());
                            tvLang.setText(selectedFilm.getLanguage());
                            tvOverview.setText(selectedFilm.getOverview());

                            Glide.with(DetailActivity.this)
                                    .load(url_image)
                                    .placeholder(R.color.colorAccent)
                                    .dontAnimate()
                                    .into(ivPoster);

                            cl.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });
            }
        }).start();
    }
}
