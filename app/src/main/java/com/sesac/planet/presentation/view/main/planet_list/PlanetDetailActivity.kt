package com.sesac.planet.presentation.view.main.planet_list

import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.plan.PostDetailPlanRequest
import com.sesac.planet.databinding.ActivityPlanetDetailBinding
import com.sesac.planet.presentation.view.main.planet_list.adapter.PlanetDetailAdapter
import com.sesac.planet.presentation.viewmodel.main.plan.PatchDetailPlanViewModel
import com.sesac.planet.presentation.viewmodel.main.plan.PatchDetailPlanViewModelFactory
import com.sesac.planet.presentation.viewmodel.main.plan.PostDetailPlanViewModel
import com.sesac.planet.presentation.viewmodel.main.plan.PostDetailPlanViewModelFactory
import com.sesac.planet.presentation.viewmodel.main.planet.PlanetDetailViewModel
import com.sesac.planet.presentation.viewmodel.main.planet.PlanetDetailViewModelFactory
import com.sesac.planet.utility.Constant
import com.sesac.planet.utility.SystemUtility

class PlanetDetailActivity() : AppCompatActivity(), DetailPlansIdForPatch, OnGetCreatePlanetPlanResult  {
    private val binding by lazy { ActivityPlanetDetailBinding.inflate(layoutInflater) }

    private var token = PlanetApplication.sharedPreferences.getString(Constant.X_ACCESS_TOKEN, "")
    private var journeyId = PlanetApplication.sharedPreferences.getInt(Constant.JOURNEY_ID, 0)

    private lateinit var planetDetailAdapter: PlanetDetailAdapter

    private var keyword: Int= 0
    private var planSize: Int=0

    private val planetDetailViewModel by lazy {
        ViewModelProvider(
            this,
            PlanetDetailViewModelFactory()
        )[PlanetDetailViewModel::class.java]
    }

    private val planDataViewModel by lazy{
        ViewModelProvider(
            this,
            PostDetailPlanViewModelFactory()
        )[PostDetailPlanViewModel::class.java]
    }

    private val patchDetailPlanViewModel by lazy{
        ViewModelProvider(
            this,
            PatchDetailPlanViewModelFactory()
        )[PatchDetailPlanViewModel::class.java]
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

        setData()
    }

    private fun initView(){
        binding.planetDetailBackImageView.setOnClickListener{
            finish()
        }

        binding.planetDetailModifyBtn.setOnClickListener {
            val intent = Intent(this, PlanetDetailModifyActivity::class.java)
            intent.putExtra("keyword", keyword)
            startActivity(intent)
            finish()
        }

        binding.planetDetailAddPlansBtn.setOnClickListener {
            CreatePlanetPlanDialog(this).show(supportFragmentManager, "dialog")
        }
    }

    private fun setData(){
        keyword = intent.getIntExtra("planet_id",0)

        initPlanetDetailInfoObservers()
        token?.let {
            planetDetailViewModel.getPlanetDetailInfo(
                it,
                keyword
            )
        }
    }

    private fun initPlanetDetailInfoObservers(){
        planetDetailViewModel.planetDetailData.observe(this){ response ->
            if(response.isSuccessful){
                response.body()?.result.let { body ->
                    if(body == null){

                    } else {
                        binding.planetDetailPlanetImg.imageTintList = ColorStateList.valueOf(Color.parseColor(body.color))
                        binding.planetDetailPlanetNameTv.text = "${body.planet_name} 행성"

                        if(!body.planet_intro.isNullOrEmpty()){
                            binding.planetDetailExplainPlanetTextView.text = body.planet_intro
                        } else{
                            binding.planetDetailExplainPlanetTextView.text = "작성한 설명이 없습니다"
                            binding.planetDetailExplainPlanetTextView.setTextColor(ColorStateList.valueOf(Color.parseColor("#C4C4C4")))
                        }

                        binding.planetDetailGrowthLevelTextView.text = "LV.${body.planet_level}"
                        binding.itemPlanetListLevelProgressBar.max = body.plans.size
                        binding.itemPlanetListLevelProgressBar.progress = body.planet_exp
                        binding.itemPlanetListLevelProgressBar.progressTintList = ColorStateList.valueOf(Color.parseColor(body.color))

                        if(!body.plans.isNullOrEmpty()){
                            binding.planetDetailDetailsPlanRecyclerView.visibility = View.VISIBLE
                            binding.planetDetailDetailsPlanTextView.visibility = View.GONE

                            planetDetailAdapter = PlanetDetailAdapter(body.plans, this)
                            binding.planetDetailDetailsPlanRecyclerView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
                            binding.planetDetailDetailsPlanRecyclerView.adapter = planetDetailAdapter

                            planSize = body.plans.size

                        } else{
                            binding.planetDetailDetailsPlanRecyclerView.visibility = View.GONE
                            binding.planetDetailDetailsPlanTextView.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun getDetailPlansIdForPatch(detailedId: Int?) {
        if(detailedId != null){
            //세부계획 완료 미완료 API 연결
            token?.let {
                patchDetailPlanViewModel.patchDetailPlan(
                    it,
                    detailedId
                )
            }
        }

        initialize()
    }

    override fun onGetCreatePlanetPlanResult(planContent: String?, type: String?) {
        //서버에 저장해주고 리사이클러뷰 갱신
        if(planContent != null && type != null){
            token?.let {
                planDataViewModel.postDetailPlan(
                    it,
                    journeyId,
                    keyword,
                    PostDetailPlanRequest(planContent, type)
                )
            }
        }
    }
}