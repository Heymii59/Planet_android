package com.sesac.planet.presentation.view.main.planet_list

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sesac.planet.databinding.ActivityPlanetDetailModifyBinding
import com.sesac.planet.presentation.view.main.home.OnSelectColorResult
import com.sesac.planet.presentation.view.main.planet_list.adapter.PlanetDetailModifyAdapter
import com.sesac.planet.presentation.viewmodel.main.plan.PatchDetailPlanViewModel
import com.sesac.planet.presentation.viewmodel.main.plan.PatchDetailPlanViewModelFactory
import com.sesac.planet.presentation.viewmodel.main.planet.PlanetDetailViewModel
import com.sesac.planet.presentation.viewmodel.main.planet.PlanetDetailViewModelFactory
import com.sesac.planet.utility.SystemUtility

class PlanetDetailModifyActivity : AppCompatActivity(), ItemDragListener, OnSelectColorResult, OnGetCreatePlanetPlanResult {
    private val binding by lazy { ActivityPlanetDetailModifyBinding.inflate(layoutInflater) }
    private lateinit var planetDetailModifyAdapter: PlanetDetailModifyAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper

    private var keyword: Int = 0

    private val planetDetailViewModel by lazy {
        ViewModelProvider(
            this,
            PlanetDetailViewModelFactory()
        )[PlanetDetailViewModel::class.java]
    }

    private val patchDetailPlanViewModel by lazy {
        ViewModelProvider(
            this, PatchDetailPlanViewModelFactory()
        )[PatchDetailPlanViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initialize()
    }

    private fun initialize() {
        SystemUtility.makeFullScreen(window, binding.root)
        SystemUtility.applyWindowInsetsPadding(binding.root)

        setData()

        binding.planetDetailModifyBackImageView.setOnClickListener {
            finish()
        }

        binding.planetDetailModifyPlanetImg.setOnClickListener {
            SelectColorDialog(this).show(supportFragmentManager, "dialog")
        }

        binding.planetDetailModifyAddPlansBtn.setOnClickListener {
            CreatePlanetPlanDialog(this).show(supportFragmentManager, "dialog")
        }
    }

    private fun setData() {
        keyword = intent.getIntExtra("keyword", 0)

        initPlanetDetailInfoObservers()
        planetDetailViewModel.getPlanetDetailInfo(
            "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWR4IjoxMSwiaWF0IjoxNjY3NjI2OTA1LCJleHAiOjE2NjkwOTgxMzR9.1IgJRf7fl08M0_5DZPff8a5GCH79hpyFtGkGET5ZtgM",
            keyword
        )
    }

    private fun patchDetailPlan(detailedPlanId: Int) {
        patchDetailPlanObserver()
        patchDetailPlanViewModel.patchDetailPlan(
            "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWR4IjoxMSwiaWF0IjoxNjY3NjI2OTA1LCJleHAiOjE2NjkwOTgxMzR9.1IgJRf7fl08M0_5DZPff8a5GCH79hpyFtGkGET5ZtgM",
            detailedPlanId
        )

    }


    private fun initPlanetDetailInfoObservers() {
        planetDetailViewModel.planetDetailData.observe(this) { response ->
            if (response.isSuccessful) {
                response.body()?.result.let { body ->
                    if (body == null) {
                    } else {
                        binding.planetDetailModifyPlanetNameTv.hint = body.planet_name
                        binding.planetDetailModifyPlanetImg.imageTintList =
                            ColorStateList.valueOf(Color.parseColor(body.color))
                        binding.planetDetailModifyExplainPlanetTextView.hint = body.planet_intro
                        binding.planetDetailModifyGrowthLevelTextView.text =
                            "LV.${body.planet_level}"
                        binding.itemPlanetModifyListLevelProgressBar.progress = body.planet_exp
                        binding.itemPlanetModifyListLevelProgressBar.progressTintList = ColorStateList.valueOf(Color.parseColor(body.color))

                        if (!body.plans.isNullOrEmpty()) {
                            binding.planetDetailModifyDetailsPlanTextView.visibility = View.GONE
                            binding.planetDetailModifyDetailsPlanRecyclerView.visibility =
                                View.VISIBLE

                            planetDetailModifyAdapter = PlanetDetailModifyAdapter(body.plans, this)
                            binding.planetDetailModifyDetailsPlanRecyclerView.layoutManager =
                                LinearLayoutManager(
                                    applicationContext,
                                    RecyclerView.VERTICAL,
                                    false
                                )
                            binding.planetDetailModifyDetailsPlanRecyclerView.adapter =
                                planetDetailModifyAdapter
                        } else {
                            binding.planetDetailModifyDetailsPlanTextView.visibility = View.VISIBLE
                            binding.planetDetailModifyDetailsPlanRecyclerView.visibility = View.GONE

                        }

                        //드래그 해서 위치 변경
                        //itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(planetDetailModifyAdapter))
                        //itemTouchHelper.attachToRecyclerView(binding.planetDetailModifyDetailsPlanRecyclerView)
                    }
                }
            }
        }
    }

    private fun patchDetailPlanObserver() {
        patchDetailPlanViewModel.patchDetailPlan.observe(this) { response ->
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    if (body.isSuccess) {

                    }
                }
            }
        }
    }

    override fun onSelectColorResult(colorId: String?) {

    }

    override fun onGetCreatePlanetPlanResult(planContent: String?, type: String?) {

    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }
}