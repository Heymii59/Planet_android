package com.sesac.planet.presentation.view.settings

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.sesac.planet.R
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.databinding.ActivityMakePlanningBinding
import com.sesac.planet.presentation.view.main.MainActivity
import com.sesac.planet.utility.Constant
import com.sesac.planet.utility.SystemUtility

class MakePlanningActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMakePlanningBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initialize()
    }

    private fun initialize() {
        SystemUtility.makeFullScreen(window, binding.root)
        SystemUtility.applyWindowInsetsPadding(binding.root)
    }

    fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()

    }

    override fun onStart() {
        super.onStart()

        val flag = PlanetApplication.sharedPreferences.getBoolean(Constant.IS_ALREADY_CREATED_JOURNEY, false)
        if(flag) {
            startMainActivity()
        }
    }

}