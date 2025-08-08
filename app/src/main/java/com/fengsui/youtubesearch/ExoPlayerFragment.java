package com.fengsui.youtubesearch;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;

public class ExoPlayerFragment extends Fragment {

    private ExoPlayer player;
    private PlayerView pvMain;
    DataSource.Factory dataSourceFactory;

    // TODO: Rename and change types and number of parameters
    public static ExoPlayerFragment newInstance(Movie movie) {
        ExoPlayerFragment fragment = new ExoPlayerFragment();
        Bundle args = new Bundle();
        args.putString(DetailsActivity.MOVIE, movie.getVideoUrl());
        fragment.setArguments(args);
        return fragment;
    }

    private void pausePlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
    }

    private void startPlayer(){
        if (player != null) {
            player.setPlayWhenReady(true);
            player.getPlaybackState();
        }
    }

    private void stopPlayer(){
        pvMain.setPlayer(null);
        player.release();
        player = null;
    }

    private void seekTo(long positionInMS) {
        if (player != null) {
            player.seekTo(positionInMS);
        }
    }

    private void initPlayer(String uri) {
        Log.i("uri", uri);
       /* TrackSelection.Factory adaptiveTrackSelection = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getActivity()),
                new DefaultTrackSelector(adaptiveTrackSelection),
                new DefaultLoadControl());
        setNoController();
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                Util.getUserAgent(getActivity(), "Exo2"), defaultBandwidthMeter);
        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
        HlsMediaSource hlsMediaSource = new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(uri));;
        player.prepare(hlsMediaSource);*/
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPlayer(getArguments().getString(DetailsActivity.MOVIE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View self = inflater.inflate(R.layout.fragment_exo_player, container, false);
        // Inflate the layout for this fragment
        pvMain = self.findViewById(R.id.pv_main); // creating player view
        pvMain.setControllerAutoShow(false);
        pvMain.setPlayer(player); // attach surface to the view
        return self;
    }

    private void setNoController() {
        /*pvMain.setControlDispatcher(new ControlDispatcher() {
            @Override
            public boolean dispatchSetPlayWhenReady(Player player, boolean playWhenReady) {
                return true;
            }

            @Override
            public boolean dispatchSeekTo(Player player, int windowIndex, long positionMs) {
                return false;
            }


            @Override
            public boolean dispatchSetRepeatMode(Player player, int repeatMode) {
                return false;
            }

            @Override
            public boolean dispatchSetShuffleModeEnabled(Player player, boolean shuffleModeEnabled) {
                return false;
            }

            @Override
            public boolean dispatchStop(Player player, boolean reset) {
                return false;
            }

        });
        pvMain.hideController();
        pvMain.setControllerVisibilityListener(new PlayerControlView.VisibilityListener() {
            @Override
            public void onVisibilityChange(int visibility) {
                if(visibility == View.VISIBLE) {
                    pvMain.hideController();
                }
            }
        });*/
    }

    @Override
    public void onPause() {
        super.onPause();
        pausePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        startPlayer();
    }
}