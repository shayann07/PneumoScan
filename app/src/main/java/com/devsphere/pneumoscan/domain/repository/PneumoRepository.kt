package com.devsphere.pneumoscan.domain.repository

import com.devsphere.pneumoscan.domain.model.Disease
import com.devsphere.pneumoscan.domain.model.TestResult
import com.devsphere.pneumoscan.domain.model.User

interface PneumoRepository {
    // Auth
    suspend fun register(email: String, password: String, firstName: String?, lastName: String?): Result<User>
    suspend fun login(email: String, password: String): Result<User>
    fun getCurrentUser(): User?

    // Info
    suspend fun getDiseaseInfo(): Result<List<Disease>>
    suspend fun getCauses(): Result<List<String>>
    suspend fun getSymptoms(): Result<List<String>>
    suspend fun getMedicationInfo(): Result<List<String>>

    // Testing
    suspend fun analyzeImage(imageUriOrPath: String): Result<TestResult>
}
