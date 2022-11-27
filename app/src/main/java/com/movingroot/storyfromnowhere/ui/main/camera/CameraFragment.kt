package com.movingroot.storyfromnowhere.ui.main.camera

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.movingroot.storyfromnowhere.data.model.Photo
import com.movingroot.storyfromnowhere.databinding.FragmentCameraBinding
import com.movingroot.storyfromnowhere.ui.base.BaseFragment
import com.movingroot.storyfromnowhere.util.Constants

class CameraFragment : BaseFragment() {
    private val TAG = this.javaClass.simpleName
    private val binding: FragmentCameraBinding get() = _binding!! as FragmentCameraBinding
    private var imageCapture: ImageCapture? = null
    private val reqPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all {
            it.value
        }
        if (granted)
            initCamera()
        else
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
    }

    private fun initFragment() {
        checkPermission()
    }

    private fun checkPermission() {
        reqPermissionLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private fun initCamera() {
        ProcessCameraProvider.getInstance(requireContext())
            .apply {
                addListener({
                    // Used to bind the lifecycle of cameras to the lifecycleOwner
                    val provider = this.get()
                    // Preview
                    val preview = Preview.Builder()
                        .build()
                        .also {
                            it.setSurfaceProvider(binding.preview.surfaceProvider)
                        }
                    imageCapture = ImageCapture.Builder().build()
                    val selector = CameraSelector.DEFAULT_BACK_CAMERA

                    try {
                        // Unbind use cases before rebinding
                        provider.apply {
                            unbindAll()
                            bindToLifecycle(this@CameraFragment, selector, preview)
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Camera binding failed", e)
                    }
                }, ContextCompat.getMainExecutor(requireContext()))
            }
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Set up listener which is triggered after photo has been taken
        imageCapture.takePicture(
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    val buffer = image.planes.first().buffer
                    val bytes = ByteArray(buffer.capacity())
                    val photoData = Photo()
                        .apply {
                            this.bytes = bytes
                        }
                    goToLabelFragment(photoData)
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun goToLabelFragment(data: Photo) {
        val bundle = bundleOf(
            Constants.BundleConst.DATA_KEY to data
        )
        findNavController().navigate(
            com.movingroot.storyfromnowhere.R.id.action_cameraFragment_to_labelFragment,
            bundle
        )
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = mutableListOf(
            Manifest.permission.CAMERA
        )
            .apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P)
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            .toTypedArray()
    }
}
