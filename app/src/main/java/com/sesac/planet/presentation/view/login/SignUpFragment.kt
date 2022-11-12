package com.sesac.planet.presentation.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.sesac.planet.R
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.EmailSignInRequest
import com.sesac.planet.databinding.FragmentSignUpBinding
import com.sesac.planet.presentation.view.settings.MakePlanningActivity
import com.sesac.planet.presentation.viewmodel.login.LoginViewModel
import com.sesac.planet.presentation.viewmodel.login.LoginViewModelFactory
import com.sesac.planet.utility.Constant


class SignUpFragment : Fragment() {
    private lateinit var activity: LoginActivity
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy { ViewModelProvider(requireActivity(), LoginViewModelFactory()).get(LoginViewModel::class.java)}

    private val callback: (token: OAuthToken?, error: Throwable?) -> Unit = { token, error ->
        if(error != null) {
            Log.d("KakaoLogin", "로그인 실패 $error")
        } else if(token != null) {
            Log.d("access_token", token.accessToken)
            //서버 요청
            viewModel.requestKakaoLogin(token.accessToken)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
    }

    private fun initialize() {
        initViews()
        initObservers()

    }

    private fun initViews() {
        binding.emailSignUpButton.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToEmailCertifyFragment()
            findNavController().navigate(action)
        }

        binding.kakaoLoginButton.setOnClickListener {
            if(UserApiClient.instance.isKakaoTalkLoginAvailable(requireActivity())) {
                Log.d("KakaoLoginTest", "loginWithKakaoTalk")
                UserApiClient.instance.loginWithKakaoTalk(requireActivity(), callback = callback)
            } else {
                Log.d("KakaoLoginTest", "loginWithKakaoAccount")
                UserApiClient.instance.loginWithKakaoAccount(requireActivity(), callback = callback)
            }
        }

        binding.emailSignInButton.setOnClickListener {
            if(binding.emailInputEditText.text.toString().isNotEmpty() && binding.passwordInputEditText.text.toString().isNotEmpty()) {
                val request = EmailSignInRequest(binding.emailInputEditText.text.toString().trim(), binding.passwordInputEditText.text.toString().trim())
                viewModel.requestEmailSignIn(request)
            }
        }
    }

    private fun initObservers() {
        viewModel.kakaoLoginResponse.observe(viewLifecycleOwner) {
            Log.d("jwtToken", it.body()?.result?.jwt.toString())
            when(it.body()?.code) {
                1000 -> {
                    Snackbar.make(binding.root, "로그인 성공", Snackbar.LENGTH_SHORT).show()
                    // 로그인 성공
                    PlanetApplication.sharedPreferences.edit {
                        it.body()?.result?.let { response ->
                            putString(Constant.X_ACCESS_TOKEN, response.jwt)
                            putInt(Constant.USER_ID, response.user_id)
                            putInt(Constant.LOGIN_TYPE, Constant.KAKAO_LOGIN)
                            commit()
                        }
                    }
                    activity.startNextPage()
                }
                else -> {
                    Snackbar.make(binding.root, "로그인 실패", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.requestEmailSignInResponse.observe(viewLifecycleOwner) { response ->
            Log.d("EmailSignInTest", response.body()?.message.toString())
            Log.d("EmailSignInTest", response.body()?.result?.jwt.toString())
            Log.d("EmailSignInTest", response.body()?.result?.userIdx.toString())
            when(response.body()?.code) {
                1000 -> {
                    Toast.makeText(requireActivity(), "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
                    PlanetApplication.sharedPreferences.edit {
                        putString(Constant.X_ACCESS_TOKEN, response.body()?.result?.jwt.toString())
                        putInt(Constant.USER_ID, response?.body()?.result?.userIdx!!)
                        putInt(Constant.LOGIN_TYPE, Constant.EMAIL_LOGIN)
                    }
                    activity.startNextPage()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity =  context as LoginActivity
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}