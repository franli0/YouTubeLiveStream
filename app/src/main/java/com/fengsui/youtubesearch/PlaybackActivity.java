package com.fengsui.youtubesearch;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

/**
 * Loads {@link PlaybackVideoFragment}.
 */
public class PlaybackActivity extends FragmentActivity {

    private YouTubePlayerView youTubePlayerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);
        Movie movie = (Movie) this.getIntent().getSerializableExtra(DetailsActivity.MOVIE);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, ExoPlayerFragment.newInstance(movie))
                    .commit();
        }

        /*youTubePlayerView = findViewById(R.id.youtube_player_view);
            getLifecycle().addObserver(youTubePlayerView);
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String videoId = movie.getVideoUrl();
                    youTubePlayer.loadVideo(videoId, 0);
                }
            });*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //youTubePlayerView.release();
    }

}