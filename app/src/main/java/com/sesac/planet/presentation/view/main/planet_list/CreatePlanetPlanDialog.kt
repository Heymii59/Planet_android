package com.sesac.planet.presentation.view.main.planet_list

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
import com.sesac.planet.R
import com.sesac.planet.databinding.DialogCreatePlanetPlanBinding

class CreatePlanetPlanDialog(private val onGetCreatePlanetPlanResult: OnGetCreatePlanetPlanResult) : DialogFragment() {
    private lateinit var binding: DialogCreatePlanetPlanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogCreatePlanetPlanBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(true)
        dialog?.setCanceledOnTouchOutside(true)

        //요일 선택 시
        binding.dialogCreateChoiceRadioBtn.setOnCheckedChangeListener { button, b ->
            if (button.isChecked) {
                binding.dialogCreateChoiceTextView.visibility = View.VISIBLE
                binding.dialogCreateWeekCheckGroup.visibility = View.VISIBLE
            } else if (!button.isChecked) {
                binding.dialogCreateChoiceTextView.visibility = View.GONE
                binding.dialogCreateWeekCheckGroup.visibility = View.GONE

                initWeekGroup()
            }
        }

        binding.createPlanetPlanSaveBtn.setOnClickListener {
            onGetCreatePlanetPlanResult.onGetCreatePlanetPlanResult(binding.dialogCreateToDoEditText.text.toString(), getType())
            dismiss()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this, 0.9f)
    }

    //타입 데이터 저장하기
    private fun getType(): String {
        var result: String = ""
        when (binding.dialogCreateDurationRadioGroup.checkedRadioButtonId) {
            R.id.dialog_create_once_radio_btn -> result = "1회성"
            R.id.dialog_create_daily_radio_btn -> result = "매일루틴"
            R.id.dialog_create_mind_radio_btn -> result = "마음가짐"
            R.id.dialog_create_no_routine_radio_btn -> result = "비정기적"
            R.id.dialog_create_choice_radio_btn -> {
                result = "${getWeekType()}"
            }
        }

        return result
    }

    private fun getWeekType(): String {
        var weekResult: String = ""

        if(binding.dialogCreateMonCheckBtn.isChecked){
            weekResult += if(weekResult.isEmpty()){
                "월"
            } else {
                ",월"
            }
        }
        if(binding.dialogCreateTueCheckBtn.isChecked){
            weekResult += if(weekResult.isEmpty()){
                "화"
            } else {
                ",화"
            }
        }
        if(binding.dialogCreateWedCheckBtn.isChecked){
            weekResult += if(weekResult.isEmpty()){
                "수"
            } else {
                ",수"
            }
        }
        if(binding.dialogCreateThurCheckBtn.isChecked){
            weekResult += if(weekResult.isEmpty()){
                "목"
            } else {
                ",목"
            }
        }
        if(binding.dialogCreateFriCheckBtn.isChecked){
            weekResult += if(weekResult.isEmpty()){
                "금"
            } else {
                ",금"
            }
        }
        if(binding.dialogCreateSatCheckBtn.isChecked){
            weekResult += if(weekResult.isEmpty()){
                "토"
            } else {
                ",토"
            }
        }
        if(binding.dialogCreateSunCheckBtn.isChecked){
            weekResult += if(weekResult.isEmpty()){
                "일"
            } else {
                ",일"
            }
        }

        return weekResult
    }

    //월~금 체크박스 초기화
    private fun initWeekGroup() {
        binding.dialogCreateMonCheckBtn.isChecked = false
        binding.dialogCreateTueCheckBtn.isChecked = false
        binding.dialogCreateWedCheckBtn.isChecked = false
        binding.dialogCreateThurCheckBtn.isChecked = false
        binding.dialogCreateFriCheckBtn.isChecked = false
        binding.dialogCreateSatCheckBtn.isChecked = false
        binding.dialogCreateSunCheckBtn.isChecked = false
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
}