package com.devsphere.pneumoscan.presentation.disease

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.devsphere.pneumoscan.databinding.FragmentDiseaseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiseaseFragment : Fragment() {
    private var _b: FragmentDiseaseBinding? = null
    private val b get() = _b!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentDiseaseBinding.inflate(inflater, container, false).also { _b = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // For now: load static content
        b.tvTitle.text = "Bacterial Pneumonia"
        b.tvDescription.text = "Bacterial pneumonia is ... (replace with real content or use domain usecase)"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}
