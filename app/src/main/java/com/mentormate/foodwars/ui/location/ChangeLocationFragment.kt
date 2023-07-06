package com.mentormate.foodwars.ui.location

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mentormate.foodwars.data.BaseFragment
import com.mentormate.foodwars.databinding.FragmentChangeLocationBinding
import com.mentormate.foodwars.ui.permission.PermissionContentHandler
import com.mentormate.foodwars.ui.permission.PermissionLiveDataHandler
import com.mentormate.foodwars.ui.permission.PermissionResultProvider
import com.mentormate.foodwars.utils.ShowMotivationScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeLocationFragment :
    BaseFragment<FragmentChangeLocationBinding>(FragmentChangeLocationBinding::inflate),
    ShowMotivationScreen, PermissionResultProvider {

    private val viewModel: ChangeLocationViewModel by viewModels()

    private lateinit var provider: PermissionLiveDataHandler

    private val resultCallback: (Boolean) -> Unit = { result ->
        PermissionContentHandler(requireContext(), result, viewModel).handleResult()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        provider = PermissionLiveDataHandler(
            requireActivity().activityResultRegistry,
            Manifest.permission.ACCESS_FINE_LOCATION,
            resultCallback
        )

        lifecycle.addObserver(provider)
        observeUiData()
        observeNavigation(viewModel.navigation)
        observeDialog(viewModel.dialog)
    }

    private fun observeUiData() {
        binding.apply {
            presenter = viewModel
            lifecycleOwner = this@ChangeLocationFragment
            viewModel.uiChangeLocationData.observe(viewLifecycleOwner) { uiModel ->
                model = uiModel
            }
        }
    }

    override fun provideObserver(): PermissionLiveDataHandler = provider
}