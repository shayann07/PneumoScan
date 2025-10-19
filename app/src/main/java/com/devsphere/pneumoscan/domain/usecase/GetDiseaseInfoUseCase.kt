package com.devsphere.pneumoscan.domain.usecase

import com.devsphere.pneumoscan.domain.model.Disease
import com.devsphere.pneumoscan.domain.repository.PneumoRepository
import javax.inject.Inject

class GetDiseaseInfoUseCase @Inject constructor(private val repo: PneumoRepository) {
    suspend operator fun invoke(): Result<List<Disease>> = repo.getDiseaseInfo()
}
