package com.sesac.planet.presentation.view.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sesac.planet.R
import com.sesac.planet.databinding.FragmentMakeNickNameBinding

class MakeNickNameFragment : Fragment() {
    private var _binding: FragmentMakeNickNameBinding? = null
    private val binding get() = _binding!!

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
        initViews()
    }

    private fun initViews() {
        binding.startNextPageButton.setOnClickListener {
            val action = MakeNickNameFragmentDirections.actionMakeNickNameFragment2ToMyFutureLookFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}