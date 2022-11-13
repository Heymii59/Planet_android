package com.sesac.planet.presentation.view.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sesac.planet.data.model.Goal
import com.sesac.planet.databinding.FragmentWantToAchieveBinding
import com.sesac.planet.presentation.view.settings.adapter.WantToAchieveAdapter
import com.sesac.planet.presentation.viewmodel.SettingsViewModel
import com.sesac.planet.presentation.viewmodel.settings.SettingsViewModelFactory

class WantToAchieveFragment : Fragment() {
    private var _binding: FragmentWantToAchieveBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy { ViewModelProvider(requireActivity(), SettingsViewModelFactory()).get(SettingsViewModel::class.java)}
    private lateinit var wantToAchieveAdapter: WantToAchieveAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWantToAchieveBinding.inflate(inflater, container ,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
    }

    private fun initialize() {
        initWantToAchieveRecyclerView()
        initViews()
    }

    private fun initWantToAchieveRecyclerView() {

        val items = mutableListOf<String>().apply {
            add("다이어트")
            add("진로찾기")
            add("책 읽기")
            add("인간관계")
            add("멘탈관리")
            add("취미")
            add("교양지식")
            add("일")
            add("취업")
        }


        wantToAchieveAdapter = WantToAchieveAdapter(items)
        binding.wantToAchieveRecyclerView.layoutManager = GridLayoutManager(requireActivity(), 3, GridLayoutManager.VERTICAL, false)
        binding.wantToAchieveRecyclerView.adapter = wantToAchieveAdapter
    }

    private fun initViews() {
        binding.startNextPageButton.setOnClickListener {

            for(item in wantToAchieveAdapter.getCheckedItems()) {
                viewModel.wantToAchieveItems.add(Goal(item))
            }



            //viewModel.wantToAchieveItems.addAll(wantToAchieveAdapter.getCheckedItems())
            val action = WantToAchieveFragmentDirections.actionWantToAchieveFragmentToPlanForGoalFragment()
            findNavController().navigate(action)
        }

        binding.startPrevPageButton.setOnClickListener {
            val action = WantToAchieveFragmentDirections.actionWantToAchieveFragmentToMyFutureLookFragment()
            findNavController().navigate(action)
        }

        binding.periodTextView.text = viewModel.period.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}