package com.mentormate.foodwars.ui.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.mentormate.foodwars.R
import com.mentormate.foodwars.databinding.HistoryCardItemBinding
import com.mentormate.foodwars.domain.vo.history.HistoryListItemUIModel
import com.mentormate.foodwars.utils.BaseAdapter

class HistoryAdapter(val presenter: HistoryPresenter) :
    BaseAdapter<HistoryListItemUIModel, HistoryCardItemBinding>(object :
        DiffUtil.ItemCallback<HistoryListItemUIModel>() {

        override fun areItemsTheSame(
            oldItem: HistoryListItemUIModel,
            newItem: HistoryListItemUIModel
        ): Boolean = oldItem === newItem

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: HistoryListItemUIModel,
            newItem: HistoryListItemUIModel
        ): Boolean = oldItem == newItem
    }) {

    override fun createBinding(
        parent: ViewGroup,
        viewType: Int
    ): HistoryCardItemBinding =
         DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.history_card_item, parent, false
        )

    override fun bind(
        binding: HistoryCardItemBinding,
        item: HistoryListItemUIModel
    ) {
        binding.model = item
        binding.presenter = presenter
    }
}