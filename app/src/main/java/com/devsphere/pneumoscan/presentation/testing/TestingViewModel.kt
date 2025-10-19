package com.devsphere.pneumoscan.presentation.testing

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.devsphere.pneumoscan.domain.usecase.AnalyzeImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class TestingViewModel @Inject constructor(private val analyzeUseCase: AnalyzeImageUseCase): ViewModel() {
    var currentImageUri: Uri? = null

    suspend fun analyzeImage(uriStr: String) = withContext(Dispatchers.IO) {
        analyzeUseCase(uriStr)
    }
}
