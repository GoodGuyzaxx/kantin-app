package uningrat.kantin.data.pref

data class UserModel(
    val id_konsumen: String,
    val nama_konsumen: String,
    val email: String,
    val no_telp: String,
    val isLogin: Boolean = false
)
