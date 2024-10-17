package uningrat.kantin.ui.admin.homeadmin.ui.profileadmin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import uningrat.kantin.databinding.FragmentProfieBinding
import uningrat.kantin.ui.ViewModelFactory
import uningrat.kantin.ui.admin.homeadmin.ui.riwayatadmin.RiwayatAdminActivity

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfieBinding? = null
    private val binding get() =  _binding!!
    private val profileAdminViewModel: ProfileAdminViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =FragmentProfieBinding.inflate(inflater,container,false)
        return binding.root
//        return inflater.inflate(R.layout.fragment_profie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileAdminViewModel.getSession().observe(viewLifecycleOwner){
            binding.tvProfileAdminEmail.text = it.email
            binding.tvProfileAdminName.text = it.nama_konsumen
            binding.tvProfileAdminNotelp.text = it.no_telp
        }

        binding.btnKeluar.setOnClickListener {
            profileAdminViewModel.logout()
            closeEntireApp()
        }

        binding.btnRiwayat.setOnClickListener {
            profileAdminViewModel.getSession().observe(viewLifecycleOwner){
                val idKantin = it.id_konsumen
                val i = Intent(requireContext(), RiwayatAdminActivity::class.java)
                i.putExtra("id",idKantin)
                startActivity(i)
            }
        }
    }

    fun closeEntireApp() {
        activity?.let { act ->
            when {
                android.os.Build.VERSION.SDK_INT >= 21 -> act.finishAndRemoveTask()
                android.os.Build.VERSION.SDK_INT >= 16 -> act.finishAffinity()
                else -> act.finish()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}