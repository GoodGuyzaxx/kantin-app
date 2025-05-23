package uningrat.kantin.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uningrat.kantin.di.Injection
import uningrat.kantin.repository.KantinRepository
import uningrat.kantin.ui.admin.addmenu.AddMenuViewModel
import uningrat.kantin.ui.admin.addnewkantin.AddNewKantinViewModel
import uningrat.kantin.ui.admin.editmenu.EditMenuViewModel
import uningrat.kantin.ui.admin.homeadmin.ui.menuadmin.MenuAdminViewModel
import uningrat.kantin.ui.admin.homeadmin.ui.pesanan.uipesanan.dibatalkan.DibatalkanViewModel
import uningrat.kantin.ui.admin.homeadmin.ui.pesanan.uipesanan.diproses.DiprosesViewModel
import uningrat.kantin.ui.admin.homeadmin.ui.pesanan.uipesanan.diterima.DiterimaViewModel
import uningrat.kantin.ui.admin.homeadmin.ui.profileadmin.ProfileAdminViewModel
import uningrat.kantin.ui.admin.homeadmin.ui.ratingdetail.RatingDetailViewModel
import uningrat.kantin.ui.admin.homeadmin.ui.riwayatadmin.RiwayatAdminViewModel
import uningrat.kantin.ui.admin.loginadmin.LoginAdminViewModel
import uningrat.kantin.ui.admin.registeradmin.RegisterAdminViewModel
import uningrat.kantin.ui.user.Home.HomeViewModel
import uningrat.kantin.ui.user.cart.CartViewModel
import uningrat.kantin.ui.user.kantin.KantinViewModel
import uningrat.kantin.ui.user.login.LoginViewModel
import uningrat.kantin.ui.user.order.OrderViewModel
import uningrat.kantin.ui.user.profile.ProfileViewModel
import uningrat.kantin.ui.user.rating.RatingViewModel
import uningrat.kantin.ui.user.register.RegisterViewModel
import uningrat.kantin.ui.user.transaksi.TransaksiViewModel
import uningrat.kantin.ui.user.updateprofile.UpdateProfileViewModel

class ViewModelFactory(private val repository: KantinRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(KantinViewModel::class.java)){
            return KantinViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            return RegisterViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(CartViewModel::class.java)){
            return CartViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(OrderViewModel::class.java)){
            return OrderViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(UpdateProfileViewModel::class.java)){
            return UpdateProfileViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(LoginAdminViewModel::class.java)){
            return LoginAdminViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ProfileAdminViewModel::class.java)){
            return ProfileAdminViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(MenuAdminViewModel::class.java)){
            return MenuAdminViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(AddMenuViewModel::class.java)) {
            return AddMenuViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(EditMenuViewModel::class.java)) {
            return EditMenuViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(RatingViewModel::class.java)) {
            return RatingViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(TransaksiViewModel::class.java)) {
            return TransaksiViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DiprosesViewModel::class.java)) {
            return DiprosesViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DiterimaViewModel::class.java)) {
            return DiterimaViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DibatalkanViewModel::class.java)) {
            return DibatalkanViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(RiwayatAdminViewModel::class.java)){
            return RiwayatAdminViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(RegisterAdminViewModel::class.java)) {
            return RegisterAdminViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(RatingDetailViewModel::class.java)) {
            return RatingDetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(AddNewKantinViewModel::class.java)) {
            return AddNewKantinViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class: ${modelClass.name}")
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory?  =null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it}
    }
}