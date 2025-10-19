package com.devsphere.pneumoscan.domain.usecase

import com.devsphere.pneumoscan.domain.model.User
import com.devsphere.pneumoscan.domain.repository.PneumoRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repo: PneumoRepository) {
    suspend operator fun invoke(email: String, password: String, firstName: String?, lastName: String?): Result<User> =
        repo.register(email, password, firstName, lastName)
}
