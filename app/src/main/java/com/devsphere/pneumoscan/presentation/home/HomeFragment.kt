package com.devsphere.pneumoscan.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.devsphere.pneumoscan.R
import com.devsphere.pneumoscan.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _b: FragmentHomeBinding? = null
    private val b get() = _b!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentHomeBinding.inflate(inflater, container, false).also { _b = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        b.cardDisease.setOnClickListener { requireActivity().findNavController(R.id.nav_host_fragment).navigate(R.id.diseaseFragment) }
        b.cardTesting.setOnClickListener { requireActivity().findNavController(R.id.nav_host_fragment).navigate(R.id.testingFragment) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}
