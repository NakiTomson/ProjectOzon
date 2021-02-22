package com.appmarketplace.ozon.presentation.activityes.ui.fragments.mock

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.appmarketplace.ozon.R
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_mock.*
import kotlinx.coroutines.*


class MockFragment : Fragment(), YouTubePlayer.OnInitializedListener {

    var API_YOUTUBE_KEY = "AIzaSyCkso3lU92eEjOHnhn6alaowRkD6i3gGXU"

    var youtube: YouTubePlayerSupportFragment? = null


    var urlStreem = ""

    val args: MockFragmentArgs by navArgs()

    private var youtubeplayer: YouTubePlayer? = null

    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return inflater.inflate(R.layout.fragment_mock, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        youtube = childFragmentManager.findFragmentById(R.id.youtube_fragment) as YouTubePlayerSupportFragment

        activity?.bottomNavigationView?.visibility = View.GONE

        val imageUrl =  args.imageUrl
        val videoUrl = args.videUrl

        val arrayHistoryList = args.arrayHistory
        position = args.position

        arrayHistoryList?.let {
            progressBar.visibility = View.VISIBLE
            insertHistory(it, position)
        }

        imageUrl?.let {

            if (imageUrl.isNotEmpty()){
                progressBar.visibility = View.VISIBLE

                imageMock.apply {
                    transitionName = imageUrl
                    Picasso.get()
                        .load(imageUrl)
                        .noFade()
                        .into(this)
                }
            }
        }
        videoUrl?.let {
            if (videoUrl.isNotEmpty()){
                imageMock.visibility = View.GONE
                frameMock.visibility = View.VISIBLE
                urlStreem = videoUrl
//                youtube?.initialize(API_YOUTUBE_KEY, this)
            }
        }
    }

    fun insertHistory(strings: Array<String>, positionm: Int) {
        imageMock.apply {
            strings[positionm].also { transitionName = it }
            Picasso.get()
                .load(strings[this@MockFragment.position])
                .noFade()
                .into(this)
        }

        loadingProgress(strings)

        rightHistory.setOnClickListener {
//            valueMove.postValue(true)
//            nextHistory(strings,++position)
        }
        leftHistory.setOnClickListener {
//            valueMove.postValue(true)
//            nextHistory(strings,postion -1)
        }
    }

    val valueProcess:MutableLiveData<Int> = MutableLiveData()



    fun loadingProgress(strings: Array<String>) {

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

    fun nextHistory(strings: Array<String>, postion: Int) {
        if(strings.size > postion && postion >= 0){
            insertHistory(strings,postion)
        }else{
            findNavController().popBackStack()
        }
    }

    override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer?, p2: Boolean) {
        youtubeplayer = p1
        if (!p2) {
            p1?.cueVideo(urlStreem)
        }
    }

    override fun onDestroyView() {
        activity?.bottomNavigationView?.visibility = View.VISIBLE
        super.onDestroyView()
    }
    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        if (p1!!.isUserRecoverableError) {
            Log.v("CRTYBU", "re ${p1.isUserRecoverableError}")
            Log.v("CRTYBU", "re ${p1.name}")
        } else {
            Log.v("CRTYBU", "re ${p1.isUserRecoverableError}")
            Log.v("CRTYBU", "re ${p1.name}")

        }
    }

}