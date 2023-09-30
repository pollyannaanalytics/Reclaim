package com.example.reclaim.match

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.reclaim.R
import com.example.reclaim.chatroom.ChatRoomFragmentArgs
import com.example.reclaim.data.ChatRoom
import com.example.reclaim.data.ReclaimDatabase
import com.example.reclaim.databinding.FragmentMatchBinding


class MatchFragment : Fragment() {


    private lateinit var viewModel: MatchViewModel
    private val navArgs by navArgs<MatchFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val application = requireActivity().application
        val reclaimDatabaseDao = ReclaimDatabase.getInstance(application).reclaimDao()
        val factory = MatchFactory(navArgs, reclaimDatabaseDao)
        val binding = FragmentMatchBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, factory).get(MatchViewModel::class.java)
        binding.viewModel = viewModel

        val leftAvatar = binding.selfContainer
        val rightAvatar = binding.otherContainer
        val matchTitle = binding.matchTitle
        var message = ""


        avatarMatchAnimate(leftAvatar, rightAvatar)
        matchTitleAnimate(matchTitle)


        binding.messageInputEdit.doAfterTextChanged {
            message = it.toString()
        }

        binding.sendToChatRoom.setOnClickListener {
         viewModel.sendMessageToChatRoom(message)
        }

        return binding.root
    }

    private fun matchTitleAnimate(matchTitle: TextView) {
        val scaleUpX = ObjectAnimator.ofFloat(
            matchTitle,
            "scaleX",
            0f,
            1f
        )

        val scaleUpY = ObjectAnimator.ofFloat(
            matchTitle,
            "scaleY",
            0f,
            1f
        )



        val animationSet = AnimatorSet()
        animationSet.playTogether(scaleUpX, scaleUpY)

        animationSet.duration = 1000
        animationSet.start()
    }

    fun avatarMatchAnimate(leftAvatar: CardView, rightAvatar: CardView) {

        val scaleUpX = ObjectAnimator.ofFloat(
            leftAvatar,
            "scaleX",
            1.0f,
            1.5f
        )

        val scaleUpY = ObjectAnimator.ofFloat(
            leftAvatar,
            "scaleY",
            1.0f,
            1.5f
        )

        val scaleDownX = ObjectAnimator.ofFloat(
            rightAvatar,
            "scaleX",
            1.0f, 1.5f
        )
        val scaleDownY = ObjectAnimator.ofFloat(
            rightAvatar,
            "scaleY",
            1.0f, 1.5f
        )


        val transitionSelfMoveAnimation = ObjectAnimator.ofFloat(
            leftAvatar,
            View.TRANSLATION_X,
            50f
        )

        val transitionOtherMoveAnimation = ObjectAnimator.ofFloat(
            rightAvatar,
            View.TRANSLATION_X,
            -50f
        )

        val scaleUpSet = AnimatorSet()
        scaleUpSet.playTogether(
            scaleUpX, scaleUpY
        )

        val scaleDownSet = AnimatorSet()
        scaleDownSet.playTogether(
            scaleDownX, scaleDownY
        )

        val animationSet = AnimatorSet()
        animationSet.playTogether(scaleUpSet, transitionSelfMoveAnimation, transitionOtherMoveAnimation, scaleDownSet)
        animationSet.duration = 1000

        animationSet.start()

    }




}