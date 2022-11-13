package com.sesac.planet.presentation.view.main.planet_list

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.planet.CreateNewPlanetPlanListRequest
import com.sesac.planet.data.model.planet.CreateNewPlanetRequest
import com.sesac.planet.databinding.ActivityCreatePlanetBinding
import com.sesac.planet.presentation.view.main.home.OnSelectColorResult
import com.sesac.planet.presentation.view.main.planet_list.adapter.CreateDetailPlanAdapter
import com.sesac.planet.presentation.viewmodel.main.planet.NewPlanetViewModel
import com.sesac.planet.presentation.viewmodel.main.planet.NewPlanetViewModelFactory
import com.sesac.planet.utility.Constant
import com.sesac.planet.utility.SystemUtility

class CreatePlanetActivity : AppCompatActivity(), OnSelectColorResult, OnGetCreatePlanetPlanResult {
    private val binding by lazy { ActivityCreatePlanetBinding.inflate(layoutInflater) }

    private var token = PlanetApplication.sharedPreferences.getString(Constant.X_ACCESS_TOKEN, "")
    private var journeyId = PlanetApplication.sharedPreferences.getInt(Constant.JOURNEY_ID, 0)

    private var selectedColor: String = ""
    private var planData: MutableList<CreateNewPlanetPlanListRequest> = mutableListOf()

    private lateinit var createDetailPlanAdapter: CreateDetailPlanAdapter

    private val newPlanetViewModel by lazy {
        ViewModelProvider(
            this,
            NewPlanetViewModelFactory()
        )[NewPlanetViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initialize()
        initView()
    }

    private fun initialize() {
        SystemUtility.makeFullScreen(window, binding.root)
        SystemUtility.applyWindowInsetsPadding(binding.root)
    }

    private fun initView(){
        binding.createBackImg.setOnClickListener {
            finish()
        }

        binding.createDetailNoPlanTitle.visibility = View.VISIBLE
        binding.createDetailPlanRcv.visibility = View.GONE

        binding.planetDetailPlanetImg.setOnClickListener {
            SelectColorDialog(this).show(supportFragmentManager, "Dialog")
        }

        binding.planetDetailAddPlansBtn.setOnClickListener {
            CreatePlanetPlanDialog(this).show(supportFragmentManager, "CreatePlanetPlanDialog")
        }

        binding.createPlanetSaveBtn.setOnClickListener {
            createNewPlanet()
            finish()
        }
    }

    private fun createNewPlanet(){
        token?.let {
            newPlanetViewModel.createNewPlanet(
                it,
                journeyId, CreateNewPlanetRequest(binding.createPlanetNameEdt.text.toString(), binding.createPlanetExplainPlanetTextView.text.toString(), selectedColor, planData)
            )
        }
    }

    //Dialog에서 선택한 색깔 값 받아옴
    override fun onSelectColorResult(colorId: String?) {
        if (colorId != null) {
            binding.planetDetailPlanetImg.backgroundTintList = ColorStateList.valueOf(Color.parseColor(colorId))
            binding.createPlanetSelectColorTv.visibility = View.GONE

            selectedColor = colorId
        }
    }

    //Dialog에서 작성한 계획과 타입 받아옴
    override fun onGetCreatePlanetPlanResult(planContent: String?, type: String?) {
        if(planContent != null && type != null){
            binding.createDetailNoPlanTitle.visibility = View.GONE
            binding.createDetailPlanRcv.visibility = View.VISIBLE

            planData.add(CreateNewPlanetPlanListRequest(planContent, type))

            createDetailPlanAdapter = CreateDetailPlanAdapter(planData)
            binding.createDetailPlanRcv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            binding.createDetailPlanRcv.adapter = createDetailPlanAdapter
        }
    }
}