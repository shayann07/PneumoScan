package com.devsphere.pneumoscan.presentation.testing

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
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
import androidx.lifecycle.lifecycleScope
import com.devsphere.pneumoscan.databinding.FragmentTestingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TestingFragment : Fragment() {

    private var _b: FragmentTestingBinding? = null
    private val b get() = _b!!
    private val vm: TestingViewModel by viewModels()

    private var capturedImageUri: Uri? = null

    // pick from gallery
    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { onImageSelected(it) }
        }

    // camera result
    private val captureImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
            if (res.resultCode == Activity.RESULT_OK) {
                capturedImageUri?.let { onImageSelected(it) }
            } else {
                Toast.makeText(requireContext(), "Camera cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    // ✅ add this launcher for WRITE_EXTERNAL_STORAGE
    private val requestWritePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                openCamera()
            } else {
                Toast.makeText(requireContext(), "Storage permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    // camera permission launcher (for Android 6+)
    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) openCamera()
            else Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT)
                .show()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentTestingBinding.inflate(inflater, container, false).also { _b = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        b.buttonSelectImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        b.buttonOpenCamera.setOnClickListener {
            // 🔹 request WRITE_EXTERNAL_STORAGE if needed (Android 9 and below)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P && ContextCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestWritePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                return@setOnClickListener
            }

            // 🔹 then handle camera permission
            if (ContextCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openCamera()
            } else {
                requestCameraPermission.launch(Manifest.permission.CAMERA)
            }
        }

        b.buttonStartTest.setOnClickListener {
            val uri = vm.currentImageUri ?: run {
                Toast.makeText(requireContext(), "Attach an image first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                b.progressBar.visibility = View.VISIBLE
                val result = vm.analyzeImage(uri.toString())
                b.progressBar.visibility = View.GONE
                result.onSuccess {
                    b.textViewResult.text =
                        "Prediction: ${it.label} (${(it.confidence * 100).toInt()}%)"
                }.onFailure { ex ->
                    Toast.makeText(
                        requireContext(), ex.message ?: "Analysis failed", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun openCamera() {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.TITLE, "PneumoScan_Capture_${System.currentTimeMillis()}")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }

        val resolver = requireContext().contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        capturedImageUri = uri

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        captureImage.launch(intent)
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