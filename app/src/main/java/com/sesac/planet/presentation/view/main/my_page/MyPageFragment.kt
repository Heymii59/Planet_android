package com.sesac.planet.presentation.view.main.my_page

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.kakao.sdk.user.UserApiClient
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.databinding.FragmentMyPageBinding
import com.sesac.planet.presentation.viewmodel.main.MainViewModel
import com.sesac.planet.presentation.viewmodel.main.MainViewModelFactory
import com.sesac.planet.utility.Constant
import com.sesac.planet.utility.SystemUtility

class MyPageFragment : Fragment()  {
    private var _binding : FragmentMyPageBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy { ViewModelProvider(requireActivity(), MainViewModelFactory()).get(MainViewModel::class.java)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SystemUtility.applyWindowInsetsTopPadding(binding.root)
        initialize()
    }

    private fun initialize() {
        initViews()
        initObservers()
        fetch()
    }

    private fun initViews() {
        binding.myPageLogoutBtn.setOnClickListener {
            if(PlanetApplication.isLoginUser()) {
                when (PlanetApplication.getLoginType()) {
                    Constant.KAKAO_LOGIN -> {
                        UserApiClient.instance.logout {
                            PlanetApplication.sharedPreferences.edit {
                                putString(Constant.X_ACCESS_TOKEN, null)
                                putInt(Constant.USER_ID, -1)
                                putInt(Constant.LOGIN_TYPE, -1)
                                Snackbar.make(binding.root, "로그아웃에 성공했습니다.", Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    }
                    Constant.EMAIL_LOGIN -> {

                    }
                    else -> {

                    }
                }
            }
        }

    }

    private fun initObservers() {
        viewModel.currentUserInfoResponse.observe(viewLifecycleOwner) { response ->
            Log.d("UserInfoTest", response.body()?.result?.user_name.toString())
            Log.d("UserInfoTest", response.body()?.result?.email.toString())

            binding.myPageProfileNickNameTextView.text = response.body()?.result?.user_name
            binding.myPageProfileEmailTextView.text = response.body()?.result?.email
        }

    }

    private fun fetch() {
        val token = PlanetApplication.sharedPreferences.getString(Constant.X_ACCESS_TOKEN, "")
        val userIdx = PlanetApplication.sharedPreferences.getInt(Constant.USER_ID, -1)
        if(token != null && userIdx != -1 ) {
            viewModel.getCurrentUserInfo(token, userIdx)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}