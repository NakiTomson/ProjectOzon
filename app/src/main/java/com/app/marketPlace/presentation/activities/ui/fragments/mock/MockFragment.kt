package com.app.marketPlace.presentation.activities.ui.fragments.mock

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app.marketPlace.R
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_mock.*
import kotlinx.coroutines.*


class MockFragment : Fragment(R.layout.fragment_mock), YouTubePlayer.OnInitializedListener {

    var API_YOUTUBE_KEY = "AIzaSyCkso3lU92eEjOHnhn6alaowRkD6i3gGXU"

    private var youtube: YouTubePlayerSupportFragment? = null

    private var urlStream = ""

    private val args: MockFragmentArgs by navArgs()

    private var youtubePlayer: YouTubePlayer? = null

    var position = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        youtube = childFragmentManager.findFragmentById(R.id.youtube_fragment) as YouTubePlayerSupportFragment
        activity?.bottomNavigationView?.visibility = View.GONE

        val imageUrl =  args.imageUrl
        val videoUrl = args.videoUrl

        val arrayHistoryList = args.arrayHistory
        position = args.position

        arrayHistoryList?.let {
            imageMock.visibility = View.VISIBLE
            progressBar.visibility = View.VISIBLE
            insertHistory(it, position)
        }

        imageUrl.let {
            if (imageUrl.isNotEmpty()){
                progressBar.visibility = View.VISIBLE
                imageMock.visibility = View.VISIBLE
                imageMock.apply {
                    transitionName = imageUrl
                    Picasso.with(context)
                        .load(imageUrl)
                        .noFade()
                        .into(this)
                }
            }
        }
        videoUrl.let {
            if (videoUrl.isNotEmpty()){
                imageMock.visibility = View.GONE
                frameMock.visibility = View.VISIBLE
                urlStream = videoUrl
                
                youtube?.initialize(API_YOUTUBE_KEY, this)
            }
        }

        val detailsGiftMockImageUrl = args.mockGiftImage
        detailsGiftMockImageUrl.let {
            if (it.isNotEmpty()){
                Picasso.with(context)
                    .load(detailsGiftMockImageUrl)
                    .into(imageGift)
                giftLayout.visibility = View.VISIBLE
                buttonNextGift.setOnClickListener {
                    Toast.makeText(context, "Подарок отправлен", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


    private fun insertHistory(strings: Array<String>, position: Int) {
        imageMock.apply {
            strings[position].also { transitionName = it }
            Picasso.with(context)
                .load(strings[this@MockFragment.position])
                .noFade()
                .into(this)
        }
        loadingProgress(strings)
    }

    private val valueProcess:MutableLiveData<Int> = MutableLiveData()

    private fun loadingProgress(strings: Array<String>) {
        valueProcess.observe(viewLifecycleOwner, { progress ->
            progressBar?.progress = progress
            if (progress == 100 && progressBar != null) {
                valueProcess.value = 0
                nextHistory(strings, ++position)
            }
        })

        val job = GlobalScope.launch(Dispatchers.IO) {
            for (i in 0..100) {
                Thread.sleep(30)
                valueProcess.postValue(i)
            }
        }
    }

    private fun nextHistory(strings: Array<String>, position: Int) {
        if(strings.size > position && position >= 0){
            insertHistory(strings, position)
        }else{
            findNavController().popBackStack()
        }
    }

    override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer?, p2: Boolean) {
        youtubePlayer = p1
        if (!p2) {
            p1?.cueVideo(urlStream)
        }
    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
    }

    override fun onDestroyView() {
        activity?.bottomNavigationView?.visibility = View.VISIBLE
        super.onDestroyView()
    }
}