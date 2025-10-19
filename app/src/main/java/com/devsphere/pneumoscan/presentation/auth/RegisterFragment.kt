package com.devsphere.pneumoscan.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.devsphere.pneumoscan.R
import com.devsphere.pneumoscan.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _b: FragmentRegisterBinding? = null
    private val b get() = _b!!
    private val vm: AuthViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentRegisterBinding.inflate(inflater, container, false).also { _b = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        b.btnRegister.setOnClickListener {
            val first = b.etFirstName.text.toString().trim()
            val last = b.etLastName.text.toString().trim()
            val email = b.etEmail.text.toString().trim()
            val pass = b.etPassword.text.toString().trim()
            val conf = b.etConfirmPassword.text.toString().trim()
            if (pass != conf) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launch {
                vm.register(email, pass, first, last).onSuccess {
                    Toast.makeText(requireContext(), "Registered ${it.firstName ?: it.email}", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.homeFragment)
                }.onFailure { ex ->
                    Toast.makeText(requireContext(), ex.message ?: "Registration failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}
