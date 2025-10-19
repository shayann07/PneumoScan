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
import com.devsphere.pneumoscan.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _b: FragmentLoginBinding? = null
    private val b get() = _b!!
    private val vm: AuthViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentLoginBinding.inflate(inflater, container, false).also { _b = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        b.btnLogin.setOnClickListener {
            val email = b.etEmail.text.toString().trim()
            val pass = b.etPassword.text.toString().trim()
            lifecycleScope.launch {
                vm.login(email, pass).onSuccess {
                    Toast.makeText(requireContext(), "Welcome ${it.firstName ?: it.email}", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.homeFragment)
                }.onFailure { ex ->
                    Toast.makeText(requireContext(), ex.message ?: "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
        b.tvRegister.setOnClickListener { findNavController().navigate(R.id.registerFragment) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}
