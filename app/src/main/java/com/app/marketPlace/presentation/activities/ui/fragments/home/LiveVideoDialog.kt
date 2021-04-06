package com.app.marketPlace.presentation.activities.ui.fragments.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.View
import androidx.fragment.app.DialogFragment
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.app.marketPlace.R
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import kotlinx.android.synthetic.main.item_live_stream.view.*

class LiveVideoDialog : DialogFragment() {

    var player: SimpleExoPlayer? = null
    var playWhenReady: Boolean = true
    var currentWindow: Int = 0
    var playbackPosition: Int = 0

    private lateinit var exoplayer: PlayerView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view  = layoutInflater.inflate(R.layout.item_live_stream, null)
        exoplayer = view.exoplayer
        if (savedInstanceState != null){
            currentWindow = savedInstanceState.getInt("currentWindow")
            playbackPosition = savedInstanceState.getInt("playbackPosition")
            playWhenReady = savedInstanceState.getBoolean("playWhenReady")
        }

        hideSystemUi()

        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setPositiveButton(getString(R.string.close)) { _, _ -> }
            .create()
    }

    private fun hideSystemUi() {
        exoplayer.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }
    @SuppressLint("StaticFieldLeak")
    private fun playYoutubeVideo(url: String) {

        object : YouTubeExtractor(requireContext()) {
            override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, videoMeta: VideoMeta?) {
                if (ytFiles != null){
                    val videoTag = 22
                    val audioTag = 140

                    val video: MediaSource = ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
                        .createMediaSource(MediaItem.fromUri(ytFiles.get(videoTag).url))

                    val media: MediaSource = ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
                        .createMediaSource(MediaItem.fromUri(ytFiles.get(audioTag).url))

                    player?.setMediaSource(MergingMediaSource(true,video,media),true)
                    player?.prepare()
                    player?.playWhenReady = playWhenReady
                    player?.seekTo(currentWindow,playbackPosition.toLong())
                }
            }
        }.extract(url, false, true)
    }

    private fun initPlayer(){
        player = SimpleExoPlayer.Builder(requireContext()).build()
        exoplayer.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
        player?.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
        exoplayer.player = player
    }



    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("currentWindow",currentWindow)
        outState.putInt("playbackPosition",playbackPosition)
        outState.putBoolean("playWhenReady",playWhenReady)
        super.onSaveInstanceState(outState)
    }

    private fun releasePlayer() {
        player?.let {
            playWhenReady = it.playWhenReady
            playbackPosition = it.currentPosition.toInt()
            currentWindow = it.currentWindowIndex
            it.release()
            player = null
        }
    }
    override fun onResume() {
        super.onResume()
        playYoutubeVideo("https://www.youtube.com/watch?v=InZSL1lw3VA")
        initPlayer()
        hideSystemUi()
    }

    override fun onStop() {
        releasePlayer()
        super.onStop()
    }

    override fun onDismiss(dialog: DialogInterface) {
        releasePlayer()
        super.onDismiss(dialog)
    }

    override fun onCancel(dialog: DialogInterface) {
        releasePlayer()
        super.onCancel(dialog)
    }
    companion object {
        const val TAG = "LiveVideoDialog"
    }
}