package uningrat.kantin.ui.admin.addmenu

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import uningrat.kantin.R
import uningrat.kantin.databinding.ActivityAddMenuBinding
import uningrat.kantin.helper.uriToFile
import uningrat.kantin.ui.ViewModelFactory
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddMenuActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddMenuBinding
    private val viewModel by viewModels<AddMenuViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var currentImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLanjut.setOnClickListener{
            addMenu()
            viewModel.addMenuResponse.observe(this){
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }

        binding.imgMenu.setOnClickListener {
            getGallery()
        }

        val actionBar = supportActionBar
        actionBar?.title = "Tambah Menu"
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun addMenu() {
        if (validateInputs()) {
            currentImageUri?.let { uri ->
                viewModel.getSession().observe(this) { session ->
                    val idKantin = session.id_konsumen.trim()
                    val namaMenu = binding.etFoodName.text.toString().trim()
                    val hargaMenu = binding.etFoodPrice.text.toString().trim()
                    val deskripsiMenu = "TEST"
                    val stockMenu = 99

                    val imageFile = uriToFile(uri, this)
                    val requestImage = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())

                    val kategoriMenu = when (binding.rgCategory.checkedRadioButtonId) {
                        R.id.rb_food -> "makanan"
                        R.id.rb_drink -> "minuman"
                        else -> ""
                    }

                    val idKantinBody = idKantin.toRequestBody("text/plain".toMediaType())
                    val namaMenuBody = namaMenu.toRequestBody("text/plain".toMediaType())
                    val deskripsiMenuBody = deskripsiMenu.toRequestBody("text/plain".toMediaType())
                    val stockMenuBody = stockMenu.toString().toRequestBody("text/plain".toMediaType())
                    val hargaMenuBody = hargaMenu.toRequestBody("text/plain".toMediaType())
                    val kategoriMenuBody = kategoriMenu.toRequestBody("text/plain".toMediaType())

                    val imageBody = MultipartBody.Part.createFormData(
                        "file",
                        imageFile.name,
                        requestImage
                    )

                    viewModel.addMenu(
                        idKantinBody,
                        namaMenuBody,
                        deskripsiMenuBody,
                        hargaMenuBody,
                        stockMenuBody,
                        kategoriMenuBody,
                        imageBody
                    )
                }
            } ?: run {
                Toast.makeText(this, "Silakan pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        val errorMessage = StringBuilder()

        if (binding.etFoodName.text.toString().trim().isEmpty()) {
            isValid = false
            errorMessage.append("Nama makanan tidak boleh kosong\n")
        }

        if (binding.etFoodPrice.text.toString().trim().isEmpty()) {
            isValid = false
            errorMessage.append("Harga tidak boleh kosong\n")
        }

        if (binding.rgCategory.checkedRadioButtonId == -1) {
            isValid = false
            errorMessage.append("Silakan pilih kategori\n")
        }

        if (currentImageUri == null) {
            isValid = false
            errorMessage.append("Silakan pilih gambar\n")
        }

        if (!isValid) {
            Toast.makeText(this, errorMessage.toString().trim(), Toast.LENGTH_LONG).show()
        }

        return isValid
    }

    private fun getGallery(){
        openGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val openGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri : Uri? ->
        if (uri != null){
            currentImageUri = uri
            showImage()
        }

    }

    private fun showImage() {
        currentImageUri?.let {
            binding.imgMenu.setImageURI(it)
        }
    }
}