package com.mentormate.foodwars.ui.topten

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mentormate.foodwars.data.BaseFragment
import com.mentormate.foodwars.databinding.FragmentToptenBinding
import com.mentormate.foodwars.utils.ShowMotivationScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopTenFragment : BaseFragment<FragmentToptenBinding>(FragmentToptenBinding::inflate),
    ShowMotivationScreen {

    private val viewModel: TopTenViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        observeNavigation(viewModel.navigation)
        observeUiData()
    }

    private fun observeUiData() {
        binding.apply {
            presenter = viewModel
            lifecycleOwner = this@TopTenFragment
            initializeRecyclerView()
            viewModel.uiTopTenData.observe(viewLifecycleOwner) { uiModel ->
                binding.model = uiModel
                (binding.recyclerView.adapter as TopTenAdapter).submitList(uiModel.entityList)
            }
        }
    }

    private fun initializeRecyclerView() {
        binding.apply {
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            recyclerView.adapter = TopTenAdapter(viewModel)
        }
    }
}