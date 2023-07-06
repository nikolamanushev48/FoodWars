package com.mentormate.foodwars.ui.topten

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.mentormate.foodwars.R
import com.mentormate.foodwars.databinding.HeaderBinding
import com.mentormate.foodwars.databinding.ToptenEntityItemBinding
import com.mentormate.foodwars.domain.vo.topten.HeaderUiModel
import com.mentormate.foodwars.domain.vo.topten.CardItem
import com.mentormate.foodwars.domain.vo.topten.TopTenEntityUIModel
import com.mentormate.foodwars.utils.BaseAdapter

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ENTITY = 1

class TopTenAdapter(private val presenter: TopTenPresenter) :
    BaseAdapter<CardItem, ViewDataBinding>(object :
        DiffUtil.ItemCallback<CardItem>() {

        override fun areItemsTheSame(
            oldItem: CardItem,
            newItem: CardItem
        ): Boolean = oldItem === newItem

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: CardItem,
            newItem: CardItem
        ): Boolean = oldItem == newItem
    }) {

    override fun createBinding(
        parent: ViewGroup,
        viewType: Int
    ): ViewDataBinding {
        return when (viewType) {
            ITEM_VIEW_TYPE_ENTITY -> {
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.topten_entity_item, parent, false
                )
            }
            else -> {
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.header, parent, false
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is TopTenEntityUIModel -> ITEM_VIEW_TYPE_ENTITY
            is HeaderUiModel -> ITEM_VIEW_TYPE_HEADER
        }

    override fun bind(
        binding: ViewDataBinding,
        item: CardItem
    ) {
        when (binding) {
            is ToptenEntityItemBinding -> {
                binding.model = item as TopTenEntityUIModel
                binding.presenter = presenter
            }
            is HeaderBinding -> {
                binding.model = item as HeaderUiModel
            }
        }
    }
}