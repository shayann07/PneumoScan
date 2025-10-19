package com.devsphere.pneumoscan.data.repository

import com.devsphere.pneumoscan.domain.model.Disease
import com.devsphere.pneumoscan.domain.model.TestResult
import com.devsphere.pneumoscan.domain.model.User
import com.devsphere.pneumoscan.domain.repository.PneumoRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PneumoRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : PneumoRepository {

    override suspend fun register(email: String, password: String, firstName: String?, lastName: String?): Result<User> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: throw Exception("Failed to create user")
            val map = mapOf("email" to email, "firstName" to (firstName ?: ""), "lastName" to (lastName ?: ""))
            firestore.collection("users").document(uid).set(map).await()
            Result.success(User(uid, firstName, lastName, email))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: throw Exception("Auth succeeded but UID null")
            val snapshot = firestore.collection("users").document(uid).get().await()
            val first = snapshot.getString("firstName")
            val last = snapshot.getString("lastName")
            Result.success(User(uid, first, last, email))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUser(): User? {
        val u = auth.currentUser ?: return null
        return User(u.uid, u.displayName, null, u.email ?: "")
    }

    override suspend fun getDiseaseInfo(): Result<List<Disease>> {
        // Temporary hard-coded data; optionally fetch from Firestore or remote later
        return try {
            val list = listOf(
                Disease("bact", "Bacterial Pneumonia", "Caused by bacteria. May require antibiotics.", "https://en.wikipedia.org/wiki/Pneumonia"),
                Disease("viral", "Viral Pneumonia", "Caused by viruses. Usually supportive care.")
            )
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCauses(): Result<List<String>> =
        Result.success(listOf("Bacteria", "Virus", "Fungi", "Aspiration"))

    override suspend fun getSymptoms(): Result<List<String>> =
        Result.success(listOf("Cough", "Fever", "Shortness of breath", "Chest pain"))

    override suspend fun getMedicationInfo(): Result<List<String>> =
        Result.success(listOf("Antibiotics (for bacterial)", "Antivirals (if applicable)", "Supportive care"))

    override suspend fun analyzeImage(imageUriOrPath: String): Result<TestResult> {
        // Placeholder. Later integrate TFLite or ML Kit here.
        val fake = TestResult("Normal", 0.86f)
        return Result.success(fake)
    }
}
