package com.slyvii.eyeam.ui.preview

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.slyvii.eyeam.R
import com.slyvii.eyeam.databinding.FragmentPreviewBinding
import com.slyvii.eyeam.rotateBitmap
import com.slyvii.eyeam.ui.CameraxActivity
import com.slyvii.eyeam.ui.ViewModelFactory
import com.slyvii.eyeam.ui.detail.DetailActivity
import com.slyvii.eyeam.uriToFile
import com.slyvii.eyeam.utils.SettingPreferences
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreviewFragment : Fragment() {
    private var getFile: File? = null
    private val viewModel: PreviewViewModel by viewModels {
        ViewModelFactory(SettingPreferences.getInstance(context?.dataStore!!))
    }
    private var token: String? = null
    private var _binding: FragmentPreviewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        state(false)

        viewModel.getToken().observe(viewLifecycleOwner) {
            token = "Bearer $it"
        }

        if (!allPermissionsGranted()) {
            requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.apply {
            btnCameraX.setOnClickListener { startCameraX() }
            btnGallery.setOnClickListener { startGallery() }
            btnUnggah.setOnClickListener {
                state(true)
                uploadImage(token!!)
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCameraX() {
        val intent = Intent(context, CameraxActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun uploadImage(auth: String) {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)

            val byteArrayOutputStream = ByteArrayOutputStream()
            val bitmap = BitmapFactory.decodeFile(file.path)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
            val imageString: String = Base64.encodeToString(imageBytes, Base64.DEFAULT)

            val requestImageFile = imageString.toRequestBody("text/plain".toMediaType())

            val context = requireContext()

            viewModel.postImage(auth, requestImageFile, context).observe(viewLifecycleOwner) {
                val res = it
                Toast.makeText(context, "$res", Toast.LENGTH_SHORT).show()
                state(false)
                startActivity(
                    Intent(requireContext(), DetailActivity::class.java).putExtra(
                        DetailActivity.RESULT,
                        res
                    ).putExtra(DetailActivity.TOKEN, auth)
                )
            }
        } else {
            Toast.makeText(
                requireContext(),
                resources.getText(R.string.select_image),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            getFile = myFile
            val result =
                rotateBitmap(
                    BitmapFactory.decodeFile(myFile.path),
                    isBackCamera
                )

            binding.imageView.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireContext())
            getFile = myFile
            binding.imageView.visibility = View.INVISIBLE
            binding.imageView.setImageURI(selectedImg)
            binding.imageView.visibility = View.VISIBLE
        }
    }

    private fun reduceFileImage(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int
        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        } while (streamLength > 1000000)
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }

    private fun state(b: Boolean){
        if (b){
            binding.progressCircular.visibility = View.VISIBLE
            binding.btnUnggah.isEnabled = false
            binding.btnGallery.isEnabled = false
            binding.btnCameraX.isEnabled = false
        } else {
            binding.progressCircular.visibility = View.GONE
            binding.btnUnggah.isEnabled = true
            binding.btnGallery.isEnabled = true
            binding.btnCameraX.isEnabled = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}