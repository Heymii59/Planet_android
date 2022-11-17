package com.sesac.planet.presentation.view.main.daily_record

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.sesac.planet.R
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.QueryDailyRecordRequest
import com.sesac.planet.databinding.ActivityPastRecordBinding
import com.sesac.planet.presentation.viewmodel.dailyrecord.PastDailyRecordViewModel
import com.sesac.planet.presentation.viewmodel.dailyrecord.PastDailyRecordViewModelFactory
import com.sesac.planet.utility.Constant
import com.sesac.planet.utility.SystemUtility

class PastRecordActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPastRecordBinding.inflate(layoutInflater)}
    private val viewModel by lazy { ViewModelProvider(this, PastDailyRecordViewModelFactory()).get(PastDailyRecordViewModel::class.java)}

    private var selectedMonth: String = ""
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initialize()
    }

    private fun initialize() {
        SystemUtility.makeFullScreen(window, binding.root)
        SystemUtility.applyWindowInsetsPadding(binding.root)

        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.calendar.setOnDateChangedListener { widget, date, selected ->
            if(selected) {
                val currentMonth = String.format("%02d", date.month)
                val currentDate = String.format("%02d", date.day)

                selectedMonth = currentMonth
                selectedDate = currentDate

                val token = PlanetApplication.sharedPreferences.getString(Constant.X_ACCESS_TOKEN, "")
                val userId = PlanetApplication.sharedPreferences.getInt(Constant.USER_ID, -1)

                viewModel.queryDailyRecord(
                    "${date.year}${currentMonth}${currentDate}",
                    "${date.year}${currentMonth}${currentDate}",
                    token!!,
                    userId
                )
            }
        }
    }

    private fun initObservers() {
        viewModel.queryDailyRecordResponse.observe(this) { response ->
            when(response.body()?.code) {
                1000 -> {
                    if(response.body()?.result?.isNotEmpty()!!) {
                        binding.visibilityControlGroup.visibility = View.VISIBLE
                        binding.notExistDailyRecordGroup.visibility = View.GONE

                        binding.pastFeelDetailTextView.text = response.body()?.result?.get(0)?.emotion
                        binding.todayEvaluationPointImageView.text = response.body()?.result?.get(0)?.evaluation.toString()
                        binding.pastShortReviewDetailTextView.text = response.body()?.result?.get(0)?.content
                        binding.detailTitleTextView.text = "${selectedMonth}월 ${selectedDate}일의 나"
                    } else {
                        binding.notExistDailyRecordGroup.visibility = View.VISIBLE
                        binding.visibilityControlGroup.visibility = View.GONE
                        binding.notExistDailyRecordTextView.text = "${selectedMonth}월 ${selectedDate}일은\n 하루기록을 작성하지 않으셨군요"
                    }
                } else -> {
                    Snackbar.make(binding.root, "하루기록을 읽어오지 못했습니다.", Snackbar.LENGTH_SHORT).show()
                    binding.visibilityControlGroup.visibility = View.GONE
                    binding.notExistDailyRecordGroup.visibility = View.GONE
                }
            }
        }
    }

}