package com.devsphere.pneumoscan.presentation.testing

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.devsphere.pneumoscan.databinding.FragmentTestingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import androidx.core.content.PermissionChecker

@AndroidEntryPoint
class TestingFragment : Fragment() {
    private var _b: FragmentTestingBinding? = null
    private val b get() = _b!!
    private val vm: TestingViewModel by viewModels()

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { onImageSelected(it) }
    }

    private val captureImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
        if (res.resultCode == Activity.RESULT_OK) {
            val bmpUri = res.data?.data
            bmpUri?.let { onImageSelected(it) } // note: camera capture via Intent may return data differently
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentTestingBinding.inflate(inflater, container, false).also { _b = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        b.buttonSelectImage.setOnClickListener { pickImage.launch("image/*") }
        b.buttonOpenCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            captureImage.launch(intent)
        }
        b.buttonStartTest.setOnClickListener {
            val uri = vm.currentImageUri ?: run { Toast.makeText(requireContext(), "Attach an image first", Toast.LENGTH_SHORT).show(); return@setOnClickListener }
            lifecycleScope.launch {
                b.progressBar.visibility = View.VISIBLE
                val res = vm.analyzeImage(uri.toString())
                b.progressBar.visibility = View.GONE
                res.onSuccess {
                    b.textViewResult.text = "Prediction: ${it.label} (${(it.confidence*100).toInt()}%)"
                }.onFailure { ex ->
                    Toast.makeText(requireContext(), ex.message ?: "Analysis failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onImageSelected(uri: Uri) {
        vm.currentImageUri = uri
        b.previewImage.setImageURI(uri)
        b.buttonStartTest.isEnabled = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}
