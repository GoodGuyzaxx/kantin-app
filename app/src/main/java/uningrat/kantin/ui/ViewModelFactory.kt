package uningrat.kantin.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uningrat.kantin.di.Injection
import uningrat.kantin.repository.KantinRepository
import uningrat.kantin.ui.user.Home.HomeViewModel
import uningrat.kantin.ui.user.cart.CartViewModel
import uningrat.kantin.ui.user.kantin.KantinViewModel
import uningrat.kantin.ui.user.login.LoginViewModel
import uningrat.kantin.ui.user.order.OrderViewModel
import uningrat.kantin.ui.user.profile.ProfileViewModel
import uningrat.kantin.ui.user.register.RegisterViewModel

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