package com.mentormate.foodwars.ui.history

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mentormate.foodwars.data.*
import com.mentormate.foodwars.databinding.FragmentHistoryBinding
import com.mentormate.foodwars.utils.ShowMotivationScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate),
    ShowMotivationScreen {

    private val viewModel: HistoryViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        observeUiData()
        observeNavigation(viewModel.navigation)
    }

    private fun observeUiData() {
        binding.apply {
            presenter = viewModel
            lifecycleOwner = this@HistoryFragment
            viewModel.uiHistoryData.observe(viewLifecycleOwner) { uiModel ->
                model = uiModel
                (binding.historyRecyclerView.adapter as HistoryAdapter).submitList(uiModel.entityList)
            }
        }
    }

    private fun initializeRecyclerView() {
        binding.apply {
            historyRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            historyRecyclerView.adapter = HistoryAdapter(viewModel)
        }
    }
}