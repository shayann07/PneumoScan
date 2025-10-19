package com.devsphere.pneumoscan.domain.usecase

import com.devsphere.pneumoscan.domain.model.User
import com.devsphere.pneumoscan.domain.repository.PneumoRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val repo: PneumoRepository) {
    suspend operator fun invoke(email: String, password: String): Result<User> = repo.login(email, password)
}
