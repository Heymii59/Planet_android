package com.sesac.planet.presentation.view.settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.MakeJourneyRequest
import com.sesac.planet.data.model.Planet
import com.sesac.planet.databinding.FragmentPreviewMyPlanningBinding
import com.sesac.planet.presentation.view.settings.adapter.PreviewMyFutureLookAdapter
import com.sesac.planet.presentation.view.settings.adapter.PreviewPlanForGoalAdapter
import com.sesac.planet.presentation.viewmodel.SettingsViewModel
import com.sesac.planet.presentation.viewmodel.settings.SettingsViewModelFactory
import com.sesac.planet.utility.Constant

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class PreviewMyPlanningFragment : Fragment() {
    private var _binding: FragmentPreviewMyPlanningBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy { ViewModelProvider(requireActivity(), SettingsViewModelFactory()).get(SettingsViewModel::class.java)}
    private lateinit var previewMyFutureLookAdapter: PreviewMyFutureLookAdapter
    private lateinit var previewPlanForGoalAdapter: PreviewPlanForGoalAdapter
    private lateinit var activity: MakePlanningActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreviewMyPlanningBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
    }

    private fun initialize() {
        initViews()
        initObservers()
        initPreviewMyFutureLookRecyclerView()
        initPreviewPlanForGoalRecyclerView()
    }

    private fun initPreviewMyFutureLookRecyclerView() {
        /*
        val items = mutableListOf<String>().apply {
            add("상냥한")
            add("강한")
            add("몸짱")
            add("날씬한")
            add("편안한")
        }

         */
        val items = mutableListOf<String>()
        items.addAll(viewModel.myFutureLookItems)
        previewMyFutureLookAdapter = PreviewMyFutureLookAdapter(items)
        binding.previewMyFutureLookRecyclerView.layoutManager = GridLayoutManager(requireActivity(), 5, GridLayoutManager.VERTICAL, false)
        binding.previewMyFutureLookRecyclerView.adapter = previewMyFutureLookAdapter
    }

    private fun initPreviewPlanForGoalRecyclerView() {
        /*
        val items = mutableListOf<String>().apply {
            add("다이어트")
            add("취업")
            add("책 읽기")
            add("인간 관계")
            add("멘탈관리")
            add("취미")
        }

         */
        val items = mutableListOf<String>()
        for(item in viewModel.wantToAchieveItems) {
            items.add(item.field)
        }
        previewPlanForGoalAdapter = PreviewPlanForGoalAdapter(items)
        binding.previewPlanForGoalRecyclerView.layoutManager = GridLayoutManager(requireActivity(), 3, GridLayoutManager.VERTICAL, false)
        binding.previewPlanForGoalRecyclerView.adapter = previewPlanForGoalAdapter
    }

    private fun initViews() {
        binding.startNextPageButton.setOnClickListener {
            val keywords = viewModel.myFutureLookItems
            val nickName = viewModel.nickName
            val period = viewModel.period
            val planets = mutableListOf<Planet>()
            val token =  PlanetApplication.sharedPreferences.getString(Constant.X_ACCESS_TOKEN, "")
            val userId = PlanetApplication.sharedPreferences.getInt(Constant.USER_ID, -1)


            for(planet in viewModel.wantToAchieveItems) {
                planets.add(Planet(planet_name = planet.field, detailed_plans = planet.details))
            }
            Log.d("planetTest", planets.toString())

            Log.d("Test123", keywords.toString())
            Log.d("Test123", nickName.toString())
            Log.d("Test123", period.toString())
            Log.d("Test123", planets.toString())


            viewModel.makeJourney(
                MakeJourneyRequest(
                    keywords,
                    period,
                    nickName,
                    planets
                ),
                token!!, userId
            )
        }

        binding.startPrevPageButton.setOnClickListener {
            val action = PreviewMyPlanningFragmentDirections.actionPreviewMyPlanningFragmentToPlanForGoalFragment()
            findNavController().navigate(action)
        }

        binding.periodTextView.text = viewModel.period.toString()
    }


    private fun initObservers() {
        viewModel.isSuccessMakeJourney.observe(viewLifecycleOwner) { response ->
            when(response.body()?.code) {
                1000 -> {
                    Toast.makeText(requireActivity(), "여정을 등록했습니다.", Toast.LENGTH_SHORT).show()
                    PlanetApplication.sharedPreferences.edit {
                        putBoolean(Constant.IS_ALREADY_CREATED_JOURNEY, true)
                        putInt(Constant.JOURNEY_ID, response.body()?.result?.journey_id!!)
                    }
                    activity.startMainActivity()
                }
                else -> {
                    Log.d("MakePlanTest", response.body()?.message.toString())
                    Snackbar.make(binding.root, "${response.body()?.code} error", Snackbar.LENGTH_SHORT).show()
                }
            }

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (context as? MakePlanningActivity)?.let {
            activity = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}