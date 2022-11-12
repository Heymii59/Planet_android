package com.sesac.planet.presentation.view.main.home

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sesac.planet.R
import com.sesac.planet.data.model.plan.PostDetailPlanRequest
import com.sesac.planet.databinding.DialogHomeAddToDoBinding
import com.sesac.planet.presentation.view.main.home.adapter.DialogSelectAdapter
import com.sesac.planet.presentation.viewmodel.main.planet.PlanetInfoViewModel
import com.sesac.planet.presentation.viewmodel.main.planet.PlanetViewModelFactory
import com.sesac.planet.presentation.viewmodel.main.plan.PostDetailPlanVIewModelFactory
import com.sesac.planet.presentation.viewmodel.main.plan.PostDetailPlanViewModel

class HomeAddToDoDialog() : DialogFragment(), OnSelectPlanetResult {
    private lateinit var binding: DialogHomeAddToDoBinding

    private lateinit var dialogSelectAdapter: DialogSelectAdapter
    private var selectedPlanetId: Int = 0

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            PlanetViewModelFactory()
        )[PlanetInfoViewModel::class.java]
    }

    private val postDetailPlanViewModel by lazy {
        ViewModelProvider(
            this,
            PostDetailPlanVIewModelFactory()
        )[PostDetailPlanViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogHomeAddToDoBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(true)
        dialog?.setCanceledOnTouchOutside(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //요일 선택 시
        binding.dialogHomeChoiceRadioBtn.setOnCheckedChangeListener { button, b ->
            if (button.isChecked) {
                binding.dialogHomeWeekCheckGroup.visibility = View.VISIBLE
            } else if (!button.isChecked) {
                binding.dialogHomeWeekCheckGroup.visibility = View.GONE
                initWeekGroup()
            }
        }

        //행성 데이터 가져와서 리사이클러뷰에 뿌려주기
        initPlanetRcv()

        //계획 저장하기
        binding.dialogHomeOkBtn.setOnClickListener {
            initPostDetailPlan()
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this, 0.9f)
    }

    //데이터 가져오기
    private fun initPlanetRcv() {
        initObservers()
        viewModel.getPlanet(
            "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWR4IjoxMSwiaWF0IjoxNjY3NjI2OTA1LCJleHAiOjE2NjkwOTgxMzR9.1IgJRf7fl08M0_5DZPff8a5GCH79hpyFtGkGET5ZtgM",
            6
        )
    }

    //오늘의 성장 계획 추가하기
    private fun initPostDetailPlan() {
        postDetailPlanViewModel.postDetailPlan(
            "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWR4IjoxMSwiaWF0IjoxNjY3NjI2OTA1LCJleHAiOjE2NjkwOTgxMzR9.1IgJRf7fl08M0_5DZPff8a5GCH79hpyFtGkGET5ZtgM",
            6, selectedPlanetId, PostDetailPlanRequest(binding.dialogHomeToDoEditText.text.toString(),getType())
        )
    }

    private fun initObservers() {
        viewModel.planetData.observe(viewLifecycleOwner) { response ->
            if(response.isSuccessful){
                response.body()?.result.let { body ->
                    if(body == null){
                    } else{
                        dialogSelectAdapter = DialogSelectAdapter(body, this)
                        binding.dialogHomeSelectPlanetRecycler.layoutManager =
                            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                        binding.dialogHomeSelectPlanetRecycler.adapter = dialogSelectAdapter
                    }
                }
            } else{

            }
        }
    }

    //디바이스 가로의 90% 사이즈
    private fun Context.dialogFragmentResize(dialogFragment: DialogFragment, width: Float) {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (Build.VERSION.SDK_INT < 30) {

            val display = windowManager.defaultDisplay
            val size = Point()

            display.getSize(size)

            val window = dialogFragment.dialog?.window

            val x = (size.x * width).toInt()
            window?.setLayout(x, ViewGroup.LayoutParams.WRAP_CONTENT)

        } else {

            val rect = windowManager.currentWindowMetrics.bounds

            val window = dialogFragment.dialog?.window

            val x = (rect.width() * width).toInt()

            window?.setLayout(x, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    //월~금 체크박스 초기화
    private fun initWeekGroup() {
        binding.dialogHomeMonCheckBtn.isChecked = false
        binding.dialogHomeTueCheckBtn.isChecked = false
        binding.dialogHomeWedCheckBtn.isChecked = false
        binding.dialogHomeThurCheckBtn.isChecked = false
        binding.dialogHomeFriCheckBtn.isChecked = false
        binding.dialogHomeSatCheckBtn.isChecked = false
        binding.dialogHomeSunCheckBtn.isChecked = false
    }

    //데이터를 넣기 위해 선택 확인
    private fun getType(): String {
        var result: String = ""
        when (binding.dialogHomeDurationRadioGroup.checkedRadioButtonId) {
            R.id.dialog_home_once_radio_btn -> result = "1회성"
            R.id.dialog_home_daily_radio_btn -> result = "매일루틴"
            R.id.dialog_home_mind_radio_btn -> result = "마음가짐"
            R.id.dialog_home_no_routine_radio_btn -> result = "비정기적"
            R.id.dialog_home_choice_radio_btn -> {
                result = "${getWeekType()}"
            }
        }

        return result
    }

    private fun getWeekType(): String {
        var weekResult: String = ""

        if(binding.dialogHomeMonCheckBtn.isChecked){
            weekResult += if(weekResult.isEmpty()){
                "월"
            } else {
                ",월"
            }
        }
        if(binding.dialogHomeTueCheckBtn.isChecked){
            weekResult += if(weekResult.isEmpty()){
                "화"
            } else {
                ",화"
            }
        }
        if(binding.dialogHomeWedCheckBtn.isChecked){
            weekResult += if(weekResult.isEmpty()){
                "수"
            } else {
                ",수"
            }
        }
        if(binding.dialogHomeThurCheckBtn.isChecked){
            weekResult += if(weekResult.isEmpty()){
                "목"
            } else {
                ",목"
            }
        }
        if(binding.dialogHomeFriCheckBtn.isChecked){
            weekResult += if(weekResult.isEmpty()){
                "금"
            } else {
                ",금"
            }
        }
        if(binding.dialogHomeSatCheckBtn.isChecked){
            weekResult += if(weekResult.isEmpty()){
                "토"
            } else {
                ",토"
            }
        }
        if(binding.dialogHomeSunCheckBtn.isChecked){
            weekResult += if(weekResult.isEmpty()){
                "일"
            } else {
                ",일"
            }
        }

        return weekResult
    }

    //adapter에서 데이터 가져오기
    override fun onItemClickResult(planetId: Int?) {
        if (planetId != null) {
            selectedPlanetId = planetId
        }
    }

}