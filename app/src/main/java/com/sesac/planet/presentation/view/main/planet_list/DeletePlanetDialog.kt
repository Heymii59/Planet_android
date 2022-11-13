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
import androidx.lifecycle.ViewModelProvider
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.databinding.DialogDeletePlanetBinding
import com.sesac.planet.presentation.viewmodel.main.planet.DeletePlanetViewModel
import com.sesac.planet.presentation.viewmodel.main.planet.DeletePlanetViewModelFactory
import com.sesac.planet.utility.Constant

class DeletePlanetDialog(private val deletePlanetId: Int): DialogFragment(){
    private lateinit var binding: DialogDeletePlanetBinding

    private var token = PlanetApplication.sharedPreferences.getString(Constant.X_ACCESS_TOKEN, "")

    private val deletePlanetViewModel by lazy {
        ViewModelProvider(
            this,
            DeletePlanetViewModelFactory()
        )[DeletePlanetViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogDeletePlanetBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.setCancelable(false)

        binding.dialogDeletePlanetYes.setOnClickListener {
            deletePlanet()
            dismiss()
        }

        binding.dialogDeletePlanetNo.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    private fun deletePlanet(){
        token?.let {
            deletePlanetViewModel.deletePlanet(
                it,
                deletePlanetId
            )
        }
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