package com.sesac.planet.presentation.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.sesac.planet.R
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.EmailSignUpRequest
import com.sesac.planet.databinding.FragmentSMSAuthBinding
import com.sesac.planet.presentation.view.settings.MakePlanningActivity
import com.sesac.planet.presentation.viewmodel.login.LoginViewModel
import com.sesac.planet.presentation.viewmodel.login.LoginViewModelFactory
import com.sesac.planet.utility.Constant
import com.sesac.planet.utility.SystemUtility


class SMSAuthFragment : Fragment() {
    private var _binding: FragmentSMSAuthBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy { ViewModelProvider(requireActivity(), LoginViewModelFactory()).get(LoginViewModel::class.java)}
    private lateinit var activity: LoginActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSMSAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
    }

    private fun initialize() {
        SystemUtility.setSoftInputMode(requireActivity().window, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.startNextPageButton.setOnClickListener {
            // TODO: 회원가입 과정이 끝났으므로 로그인 화면으로 다시 이동하도록 변경 필요
            if(binding.inputPhoneNumberEditText.text.toString().isNotEmpty() && binding.inputUserNameEditText.text.isNotEmpty()) {
                val request = EmailSignUpRequest(viewModel.userEmail, viewModel.userPassword, binding.inputPhoneNumberEditText.text.toString(), binding.inputUserNameEditText.text.toString())
                viewModel.requestEmailSignUp(request)
            }
            startActivity(Intent(requireActivity(), MakePlanningActivity::class.java))
        }
    }

    private fun initObservers() {
        viewModel.requestEmailSignUpResponse.observe(viewLifecycleOwner) { response ->
            Log.d("EmailSignUpTest", response.body()?.result?.jwt.toString())
            Log.d("EmailSignUpTest", response.body()?.message.toString())
            when(response.body()?.code) {
                1000 -> {
                    Toast.makeText(requireActivity(), "이메일 회원가입에 성공했습니다", Toast.LENGTH_SHORT).show()
                    PlanetApplication.sharedPreferences.edit {
                        putString(Constant.X_ACCESS_TOKEN, response.body()?.result?.jwt)
                        putInt(Constant.USER_ID, response.body()?.result?.userIdx!!)
                        putInt(Constant.LOGIN_TYPE, Constant.EMAIL_LOGIN)
                    }
                    activity.startNextPage()
                } else -> {
                    Toast.makeText(requireActivity(), "${response.body()?.code} - 이메일 회원가입에 실패했습니다", Toast.LENGTH_SHORT).show()
                    activity.restart()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as LoginActivity
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}