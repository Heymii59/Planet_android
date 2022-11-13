package com.sesac.planet.presentation.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kakao.sdk.common.util.Utility
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.databinding.ActivityLoadingSplashBinding
import com.sesac.planet.presentation.view.login.LoginActivity
import com.sesac.planet.presentation.view.main.MainActivity
import com.sesac.planet.utility.Constant
import com.sesac.planet.utility.SystemUtility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingSplashActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoadingSplashBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initialize()
        val keyHash = Utility.getKeyHash(this)
        Log.d("keyHash", keyHash)
    }

    private fun initialize() {
        SystemUtility.makeFullScreen(window, binding.root)
        makeSplash()
        val jwt = PlanetApplication.sharedPreferences.getString(Constant.X_ACCESS_TOKEN, "")
        val userId = PlanetApplication.sharedPreferences.getInt(Constant.USER_ID, -1)
        Log.d("userInfo", "${jwt}")
        Log.d("userInfo", "${userId}")

    }

    private fun makeSplash() {
        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.Main).launch {
                delay(Constant.SPLASH_ANIMATION_MILLIS)
                //ToDO
                startActivity(Intent(this@LoadingSplashActivity, LoginActivity::class.java))
                //startActivity(Intent(this@SplashActivity, MakePlanningActivity::class.java))
                //startActivity(Intent(this@LoadingSplashActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}