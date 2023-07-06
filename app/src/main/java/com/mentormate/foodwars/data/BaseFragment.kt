package com.mentormate.foodwars.data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VBinding : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VBinding
) : Fragment() {

    private lateinit var _binding: VBinding
    val binding: VBinding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    protected fun observeNavigation(navigation: LiveData<Destination>) {
        navigation.observe(viewLifecycleOwner){
            navigateToDestination(it)
        }
    }

    protected fun observeDialog(dialogData: LiveData<Dialog>) {
        dialogData.observe(viewLifecycleOwner){
            handleDialog(it)
        }
    }
}