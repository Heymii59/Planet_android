package com.sesac.planet.presentation.view.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sesac.planet.R
import com.sesac.planet.databinding.FragmentMyFutureLookBinding
import com.sesac.planet.presentation.view.settings.adapter.MyFutureLookAdapter
import com.sesac.planet.utility.SystemUtility


class MyFutureLookFragment : Fragment() {
    private var _binding: FragmentMyFutureLookBinding? = null
    private val binding get() = _binding!!
    private lateinit var myFutureLookAdapter: MyFutureLookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyFutureLookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
    }

    private fun initialize() {
        SystemUtility.setSoftInputMode(requireActivity().window, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        initMyFutureLookRecyclerView()
        initViews()
    }

    private fun initMyFutureLookRecyclerView() {
        val items = mutableListOf<String>().apply {
            add("예의바른")
            add("상냥한")
            add("시크한")
            add("똑부러지는")
            add("멋있는")
            add("강한")
            add("몸짱")
            add("센스있는")
            add("편안한")
            add("개발을 잘하는")
            add("능력자")
            add("존중하는")
            add("여유로운")
            add("성장하는")
            add("날씬한")
            add("유머러스한")
        }
        myFutureLookAdapter = MyFutureLookAdapter(items)
        binding.myFutureLookRecyclerView.layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL)
        binding.myFutureLookRecyclerView.adapter = myFutureLookAdapter
    }

    private fun initViews() {
        binding.startNextPageButton.setOnClickListener {
            val action = MyFutureLookFragmentDirections.actionMyFutureLookFragmentToWantToAchieveFragment("Test")
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}