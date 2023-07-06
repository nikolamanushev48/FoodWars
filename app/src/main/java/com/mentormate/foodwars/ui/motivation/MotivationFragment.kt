package com.mentormate.foodwars.ui.motivation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mentormate.foodwars.data.BaseFragment
import com.mentormate.foodwars.databinding.FragmentMotivationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MotivationFragment :
    BaseFragment<FragmentMotivationBinding>(FragmentMotivationBinding::inflate) {

    private val viewModel: MotivationViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.motionLayout.setTransitionListener(MotivationListener(viewModel))
        observeNavigation(viewModel.navigation)
    }
}