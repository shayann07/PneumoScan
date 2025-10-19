package com.devsphere.pneumoscan.domain.usecase

import com.devsphere.pneumoscan.domain.model.TestResult
import com.devsphere.pneumoscan.domain.repository.PneumoRepository
import javax.inject.Inject

class AnalyzeImageUseCase @Inject constructor(private val repo: PneumoRepository) {
    suspend operator fun invoke(imageUriOrPath: String): Result<TestResult> = repo.analyzeImage(imageUriOrPath)
}
