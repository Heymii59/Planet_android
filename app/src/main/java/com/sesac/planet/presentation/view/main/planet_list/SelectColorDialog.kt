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
import com.sesac.planet.databinding.DialogSelectPlanetColorBinding
import com.sesac.planet.presentation.view.main.home.OnSelectColorResult

class SelectColorDialog(private val onSelectColorResult: OnSelectColorResult) : DialogFragment() {
    private lateinit var binding: DialogSelectPlanetColorBinding

    private var isChecking: Boolean = true
    private var mCheckedId: Int = 0

    private var color: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogSelectPlanetColorBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.setCancelable(false)

        binding.dialogSelectExitImg.setOnClickListener {
            dismiss()
        }

        binding.dialogSelectColorRg.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId != -1 && isChecking){
                isChecking = false
                binding.dialogSelectColorRg2.clearCheck()
                mCheckedId = checkedId
            }
            isChecking = true
        }

        binding.dialogSelectColorRg2.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId != -1 && isChecking){
                isChecking = false
                binding.dialogSelectColorRg.clearCheck()
                mCheckedId = checkedId
            }
            isChecking = true
        }

        binding.dialogSaveBtn.setOnClickListener {
            //Activity로 선택한 색깔 알려주기
            onSelectColorResult.onSelectColorResult(getColor())
            dismiss()
        }

        return binding.root
    }

    private fun getColor(): String{
        when(mCheckedId){
            R.id.dialog_select_deep_purple -> color = "#896DF3"
            R.id.dialog_select_green -> color = "#7AE3AA"
            R.id.dialog_select_orange -> color = "#FFC212"
            R.id.dialog_select_pink -> color = "#F8CBB6"
            R.id.dialog_select_plum -> color = "#F2606A"
            R.id.dialog_select_purple -> color = "#E0DFFE"
            R.id.dialog_select_lime -> color = "#D3FB03"
            R.id.dialog_select_mustard -> color = "#FDFE00"
            R.id.dialog_select_sky -> color = "#B4C9FF"
            R.id.dialog_select_mono -> color = "#E1E1E1"
            else -> color = "#896DF3"
            //만약 색을 선택하지 않았다면 다이얼로그로 색을 선택하라고 할 것
        }

        return color
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this, 0.9f)
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