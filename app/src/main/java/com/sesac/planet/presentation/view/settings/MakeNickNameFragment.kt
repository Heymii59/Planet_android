package com.sesac.planet.presentation.view.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sesac.planet.databinding.FragmentMakeNickNameBinding
import com.sesac.planet.presentation.viewmodel.SettingsViewModel
import com.sesac.planet.presentation.viewmodel.settings.SettingsViewModelFactory
import com.sesac.planet.utility.SystemUtility


class MakeNickNameFragment : Fragment() {
    private var _binding: FragmentMakeNickNameBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy { ViewModelProvider(requireActivity(), SettingsViewModelFactory()).get(SettingsViewModel::class.java)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMakeNickNameBinding.inflate(inflater, container, false)

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
            viewModel.nickName = binding.nickNameEditText.text.toString()
            val action = MakeNickNameFragmentDirections.actionMakeNickNameFragment2ToMyFutureLookFragment()
            findNavController().navigate(action)
        }

        binding.checkThisNickNameButton.setOnClickListener {
            viewModel.checkNickName(binding.nickNameEditText.text.toString())
        }
    }

    private fun initObservers() {
        viewModel.isAvailableNickName.observe(viewLifecycleOwner) { response ->
            when(response.body()?.code) {
                1000 -> {
                    Snackbar.make(binding.root, "사용가능한 닉네임입니다.", Snackbar.LENGTH_SHORT).show()
                }
                2047 -> {
                    Snackbar.make(binding.root, "중복된 닉네임입니다.", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}