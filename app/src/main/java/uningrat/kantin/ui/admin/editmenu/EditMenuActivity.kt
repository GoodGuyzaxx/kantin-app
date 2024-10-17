package uningrat.kantin.ui.admin.editmenu

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import uningrat.kantin.R
import uningrat.kantin.databinding.ActivityEditMenuBinding
import uningrat.kantin.helper.reduceFileImage
import uningrat.kantin.helper.uriToFile
import uningrat.kantin.ui.ViewModelFactory

class EditMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditMenuBinding
    private val viewModel by viewModels<EditMenuViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var currentImageUri: Uri? = null
    private val method = "PATCH"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(ID_MENU)
        val nama = intent.getStringExtra(NAMA_MENU)
        val harga = intent.getStringExtra(HARGA_MENU)
        val gambar = intent.getStringExtra(GAMBAR_MENU)
        val kategori = intent.getStringExtra(KATEGORI_MENU)
        val deskripsi = intent.getStringExtra(DESKRIPSI_MENU)
        val stok = intent.getStringExtra(STOK_MENU)

        Log.d("TAG", "onCreate: $id")

        binding.etFoodName.setText(nama)
        binding.etFoodPrice.setText(harga)
        binding.etFoodDescription.setText(deskripsi)
        binding.etFoodStock.setText(stok)
        Glide
            .with(binding.root.context)
            .load(gambar)
            .into(binding.imgMenu)

        if (kategori == "makanan") {
            binding.rbFood.isChecked = true
        } else {
            binding.rbDrink.isChecked = true
        }

        binding.imgMenu.setOnClickListener {
            getGallery()
        }

        val actionBar = supportActionBar
        actionBar?.title = "Edit Menu"
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)


        binding.btnEdit.setOnClickListener {
            if (validateInputs()) {
                currentImageUri?.let { uri ->
                    viewModel.getSession().observe(this) { session ->
                        val idKantin = session.id_konsumen.trim()
                        val namaMenu = binding.etFoodName.text.toString().trim()
                        val hargaMenu = binding.etFoodPrice.text.toString().trim()
                        val deskripsiMenu = binding.etFoodDescription.text.toString().trim()
                        val stockMenu = binding.etFoodStock.text.toString().trim()

                        val imageFile = uriToFile(uri, this).reduceFileImage()
                        val requestImage = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())

                        val kategoriMenu = when (binding.rgCategory.checkedRadioButtonId) {
                            R.id.rb_food -> "makanan"
                            R.id.rb_drink -> "minuman"
                            else -> ""
                        }

                        val idKantinBody = idKantin.toRequestBody("text/plain".toMediaType())
                        val namaMenuBody = namaMenu.toRequestBody("text/plain".toMediaType())
                        val deskripsiMenuBody = deskripsiMenu.toRequestBody("text/plain".toMediaType())
                        val stockMenuBody = stockMenu.toRequestBody("text/plain".toMediaType())
                        val hargaMenuBody = hargaMenu.toRequestBody("text/plain".toMediaType())
                        val kategoriMenuBody = kategoriMenu.toRequestBody("text/plain".toMediaType())
                        val metohodBody = method.toRequestBody("text/plain".toMediaType())

                        val imageBody = MultipartBody.Part.createFormData(
                            "file",
                            imageFile.name,
                            requestImage
                        )

                        viewModel.editMenu(
                            id.toString(),
                            idKantinBody,
                            namaMenuBody,
                            deskripsiMenuBody,
                            hargaMenuBody,
                            stockMenuBody,
                            kategoriMenuBody,
                            imageBody,
                            metohodBody
                        )
                    }
                }
            }
            viewModel.EditResponse.observe(this){
                Toast.makeText(this, it.message,Toast.LENGTH_LONG).show()
            }
        }
    }



    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
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


    companion object {
        const val ID_MENU = "id"
        const val NAMA_MENU = "nama"
        const val HARGA_MENU = "harga"
        const val GAMBAR_MENU = "gambar"
        const val KATEGORI_MENU = "kategori"
        const val DESKRIPSI_MENU = "deskripsi"
        const val STOK_MENU = "stok"
    }
}