package com.mentormate.foodwars.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.mentormate.foodwars.data.Destination
import com.mentormate.foodwars.data.navigateToDestination
import com.mentormate.foodwars.ui.theme.FoodTheme
import com.mentormate.foodwars.utils.ShowMotivationScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(), ShowMotivationScreen {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
        )
        setContent {
            FoodTheme {
                LoginScreen(viewModel)
            }
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        observeNavigation(viewModel.navigation)
    }

    fun observeNavigation(navigation: LiveData<Destination>) {
        navigation.observe(viewLifecycleOwner) {
            navigateToDestination(it)
        }
    }
}