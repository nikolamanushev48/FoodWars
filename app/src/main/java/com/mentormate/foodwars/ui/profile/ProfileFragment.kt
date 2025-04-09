package com.mentormate.foodwars.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import com.mentormate.foodwars.R
import com.mentormate.foodwars.data.BaseFragment
import com.mentormate.foodwars.databinding.FragmentProfileBinding
import com.mentormate.foodwars.ui.result.ActivityResultHandler
import com.mentormate.foodwars.ui.result.ImageContentResultHandler
import com.mentormate.foodwars.ui.result.ResultProvider
import com.mentormate.foodwars.ui.result.SelectImageFromExternalStorage
import com.mentormate.foodwars.utils.ShowMotivationScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate),
    ShowMotivationScreen, ResultProvider {

    private val viewModel: ProfileViewModel by viewModels()

    private val resultCallback: (ActivityResult) -> Unit = { result ->
        ImageContentResultHandler(result, viewModel).handleResult(
            result.data?.data
        )
    }

    private lateinit var provider: SelectImageFromExternalStorage

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)


        Log.d("testt","PROFILE")
        setProvider()
        setActionBar()

        lifecycle.addObserver(provider)
        initializeMenu()
        observeUiData()
        observeNavigation(viewModel.navigation)
    }

    private fun setProvider() {
        provider = SelectImageFromExternalStorage(
            requireActivity().activityResultRegistry,
            resultCallback
        )
    }

    private fun setActionBar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(view?.findViewById(R.id.toolbar_profile))
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initializeMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}
            override fun onMenuItemSelected(menuItem: MenuItem) =
                viewModel.menuItemSelected(menuItem)
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun observeUiData() {
        binding.apply {
            presenter = viewModel
            lifecycleOwner = this@ProfileFragment
            viewModel.uiProfileData.observe(viewLifecycleOwner) { uiModel ->
                model = uiModel
            }
        }
    }

    override fun provideObserver(): ActivityResultHandler = provider
}