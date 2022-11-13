package com.sesac.planet.presentation.view.main.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.sesac.planet.R
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.plan.PostDetailPlanRequest
import com.sesac.planet.databinding.FragmentHomeBinding
import com.sesac.planet.presentation.view.main.home.adapter.HomeTodayGrowthPlanAdapter
import com.sesac.planet.presentation.viewmodel.main.home.KeywordViewModel
import com.sesac.planet.presentation.viewmodel.main.report.GetTodayInfoViewModelFactory
import com.sesac.planet.presentation.viewmodel.main.home.KeywordViewModelFactory
import com.sesac.planet.presentation.viewmodel.main.plan.*
import com.sesac.planet.presentation.viewmodel.main.report.GetTodayInfoViewModel
import com.sesac.planet.presentation.viewmodel.main.report.ReportViewModel
import com.sesac.planet.presentation.viewmodel.main.report.ReportViewModelFactory
import com.sesac.planet.utility.Constant
import com.sesac.planet.utility.SystemUtility

class HomeFragment : Fragment(), OnPostDetailPlan {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeTodayGrowthPlanAdapter: HomeTodayGrowthPlanAdapter
    private var isShowMore: Boolean = false

    private var token = ""
    private var userId = 0
    private var journeyId = 0

    private val entry1 = ArrayList<Entry>() //차트 1
    private val entry2 = ArrayList<Entry>() //차트 2

    private val chartData = LineData()

    //...님 안녕하세요 :)
    private val keywordViewModel by lazy {
        ViewModelProvider(
            this,
            KeywordViewModelFactory()
        )[KeywordViewModel::class.java]
    }

    //오늘 달성한 프로그레스바 뷰모델
    private val getTodayInfoViewModel by lazy {
        ViewModelProvider(
            this,
            GetTodayInfoViewModelFactory()
        )[GetTodayInfoViewModel::class.java]
    }

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            PlanViewModelFactory()
        )[PlanViewModel::class.java]
    }

    private val postDetailPlanViewModel by lazy {
        ViewModelProvider(
            this,
            PostDetailPlanViewModelFactory()
        )[PostDetailPlanViewModel::class.java]
    }

    private val patchDetailPlanViewModel by lazy {
        ViewModelProvider(
            this,
            PatchDetailPlanViewModelFactory()
        )[PatchDetailPlanViewModel::class.java]
    }

    private val reportViewModel by lazy {
        ViewModelProvider(
            this,
            ReportViewModelFactory()
        )[ReportViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        isShowMore = false
        binding.homeShowMoreBtn.setImageResource(R.drawable.ic_down_arrow)

        initViews()
        return binding.root
    }

    private fun initViews() {
        initialize()
    }

    private fun initialize() {
        SystemUtility.applyWindowInsetsTopPadding(binding.root)

        fetch()

        //키워드 데이터 세팅
        initKeyword()

        //프로그레스바 세팅
        initGetTodayInfo()

        //기본적인 성장 계획 리사이클러뷰로 보여주기
        initHomeTodayGrowthRcv()

        //계획 미리보기, 더보기 버튼 눌렀을 때
        binding.homeShowMoreLayout.setOnClickListener {
            isShowMore = !isShowMore

            if (isShowMore) {
                binding.homeShowMoreBtn.setImageResource(R.drawable.ic_up_arrow)
            } else {
                binding.homeShowMoreBtn.setImageResource(R.drawable.ic_down_arrow)
            }

            initHomeTodayGrowthRcv()
        }

        binding.homeAddToDoBtn.setOnClickListener {
            activity?.let { it1 ->
                HomeAddToDoDialog(this).show(
                    it1.supportFragmentManager,
                    "Dialog"
                )
            }
        }

        setChart()
    }

    private fun fetch() {
        token = PlanetApplication.sharedPreferences.getString(Constant.X_ACCESS_TOKEN, "").toString()
        userId = PlanetApplication.sharedPreferences.getInt(Constant.USER_ID, -1)
        journeyId = PlanetApplication.sharedPreferences.getInt(Constant.JOURNEY_ID, -1)
    }

    //뷰모델
    private fun initKeyword() {
        initKeywordObservers()
        token?.let {
            keywordViewModel.getKeyword(
                it,
                journeyId
            )
        }
    }

    private fun initGetTodayInfo() {
        initGetTodayInfoObservers()
        token?.let {
            getTodayInfoViewModel.getTodayInfo(
                it,
                userId
            )
        }
    }

    private fun initHomeTodayGrowthRcv() {
        initObservers()
        token?.let {
            viewModel.getPlan(
                it,
                journeyId
            )
        }
    }

    //옵저버
    private fun initKeywordObservers() {
        keywordViewModel.keywordData.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                response.body()?.result.let { body ->
                    if (body == null) {
                    } else {
                        binding.homeExpressWithAdjTextView.text = body.keyword_name
                    }
                }
            } else {

            }
        }
    }

    private fun initReport() {
        initReportObservers()
        token?.let {
            reportViewModel.getReport(
                it,
                userId
            )
        }
        binding.homeMyReportChart.invalidate()
    }

    private fun initGetTodayInfoObservers() {
        getTodayInfoViewModel.getTodayInfoData.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                response.body()?.result.let { body ->
                    if (body == null) {

                    } else {
                        binding.homeAccomplishTextView.text =
                            "오늘 할 일 ${body.totalPlan}개 중 ${body.completedCount}개를 달성하였습니다"
                        binding.homeAccomplishProgressBar.max = body.totalPlan
                        binding.homeAccomplishProgressBar.progress = body.completedCount
                        binding.homeAccomplishedAmountTextView.text = body.completedCount.toString()
                        binding.homeTargetAmountTextView.text = body.totalPlan.toString()
                    }
                }
            } else {

            }
        }
    }

    private fun initObservers() {
        viewModel.planData.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                response.body()?.result.let { body ->
                    if (body == null) {

                    } else {
                        if (body.isEmpty()) {
                            binding.homeNoResultTv.visibility = View.VISIBLE
                            binding.homeAddToDoRcv.visibility = View.GONE
                            binding.homeShowMoreBtn.visibility = View.GONE
                        } else {
                            if (body.size < 3) {
                                binding.homeNoResultTv.visibility = View.GONE
                                binding.homeAddToDoRcv.visibility = View.VISIBLE
                                binding.homeShowMoreBtn.visibility = View.GONE
                            } else {
                                binding.homeNoResultTv.visibility = View.GONE
                                binding.homeAddToDoRcv.visibility = View.VISIBLE
                                binding.homeShowMoreBtn.visibility = View.VISIBLE
                            }

                            homeTodayGrowthPlanAdapter =
                                HomeTodayGrowthPlanAdapter(body, isShowMore)
                            binding.homeAddToDoRcv.layoutManager =
                                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                            binding.homeAddToDoRcv.adapter = homeTodayGrowthPlanAdapter

                            homeTodayGrowthPlanAdapter.setItemClickListener(object :
                                HomeTodayGrowthPlanAdapter.OnItemClickListener {
                                override fun onClick(v: View, position: Int, detailedPlanId: Int) {
                                    //계획 완료/미완료 API 연결
                                    token?.let {
                                        patchDetailPlanViewModel.patchDetailPlan(
                                            it,
                                            detailedPlanId
                                        )
                                    }
                                }
                            })

                        }
                    }
                }
            } else {

            }
        }
    }

    override fun onPostDetailPlan(planetId: Int?, toDoText: String?, type: String?) {
        //오늘의 성장 계획 추가하기
        if (planetId != null && toDoText != null && type != null) {
            token?.let {
                postDetailPlanViewModel.postDetailPlan(
                    it,
                    journeyId, planetId, PostDetailPlanRequest(toDoText, type)
                )
            }

            //activity?.let { refreshFragment(this, it.supportFragmentManager) }
            //homeTodayGrowthPlanAdapter.notifyDataSetChanged()
            //initHomeTodayGrowthRcv()
        }
    }

    private fun initReportObservers() {
        reportViewModel.reportData.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                response.body()?.result.let { body ->
                    if (body == null) {

                    } else {
                        entry1.clear()
                        entry2.clear()

                        entry1.add(Entry(1f, body.completed_five_weeks_ago.toFloat()))
                        entry1.add(Entry(2f, body.completed_four_weeks_ago.toFloat()))
                        entry1.add(Entry(3f, body.completed_three_weeks_ago.toFloat()))
                        entry1.add(Entry(4f, body.completed_two_weeks_ago.toFloat()))
                        entry1.add(Entry(5f, body.completed_last_weeks_ago.toFloat()))

                        entry2.add(Entry(1f, body.total_five_weeks_ago.toFloat()))
                        entry2.add(Entry(2f, body.total_four_weeks_ago.toFloat()))
                        entry2.add(Entry(3f, body.total_three_weeks_ago.toFloat()))
                        entry2.add(Entry(4f, body.total_two_weeks_ago.toFloat()))
                        entry2.add(Entry(5f, body.total_last_weeks_ago.toFloat()))

                        //Dataset 추가 및 선 커스텀
                        val lineDataSet1 = LineDataSet(entry1, "첫번째 차트")
                        chartData.addDataSet(lineDataSet1)

                        lineDataSet1.lineWidth = 1.5f
                        lineDataSet1.circleRadius = 4f
                        lineDataSet1.setDrawValues(false)
                        lineDataSet1.setDrawCircleHole(false)
                        lineDataSet1.setDrawCircles(true)
                        lineDataSet1.setDrawFilled(true)
                        lineDataSet1.setDrawHighlightIndicators(false)
                        lineDataSet1.setDrawHorizontalHighlightIndicator(false)
                        lineDataSet1.color = resources.getColor(R.color.purple_896DF3)
                        lineDataSet1.setCircleColors(resources.getColor(R.color.purple_896DF3))
                        lineDataSet1.fillDrawable =
                            resources.getDrawable(R.drawable.shape_chart_fill)

                        val lineDataSet2 = LineDataSet(entry2, "두번째 차트")
                        chartData.addDataSet(lineDataSet2)

                        lineDataSet2.lineWidth = 1.5f
                        lineDataSet2.circleRadius = 4f
                        lineDataSet2.setDrawValues(false)
                        lineDataSet2.setDrawCircleHole(false)
                        lineDataSet2.setDrawCircles(true)
                        lineDataSet2.setDrawHighlightIndicators(false)
                        lineDataSet2.setDrawHorizontalHighlightIndicator(false)
                        lineDataSet2.color = resources.getColor(R.color.green_63F3C8)
                        lineDataSet2.setCircleColors(resources.getColor(R.color.green_63F3C8))

                        binding.homeMyReportChart.data = chartData
                        binding.homeMyReportChart.invalidate()
                    }
                }
            } else {
                //서버에 문제가 생겼을 때
            }
        }
    }

    //차트
    private fun setChart() {
        configureChartAppearance(binding.homeMyReportChart)
        initReport()
    }

    private fun configureChartAppearance(lineChart: LineChart) {
        val labels = ArrayList<String>() //라벨

        labels.add("")
        labels.add("5주전")
        labels.add("4주전")
        labels.add("3주전")
        labels.add("2주전")
        labels.add("저번주")

        lineChart.extraBottomOffset = 15f // 간격
        lineChart.description.isEnabled = false // 차트 밑에 description

        // Legend는 차트의 범례
        lineChart.legend.isEnabled = false

        lineChart.setPinchZoom(false)

        // XAxis (아래쪽) - 선 유무, 사이즈, 색상, 축 위치 설정
        val xAxis: XAxis = lineChart.xAxis
        xAxis.setDrawLabels(false)
        xAxis.setDrawGridLines(false)
        xAxis.setDrawLabels(true)
        xAxis.textSize = 14f
        xAxis.textColor = Color.BLACK
        xAxis.position = XAxis.XAxisPosition.BOTTOM // x축 데이터 표시 위치
        xAxis.granularity = 1f
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.textColor = resources.getColor(R.color.gray_8C8C8C)
        xAxis.spaceMin = 0.5f //차트 맨 왼쪽 간격 띄우기
        xAxis.spaceMax = 0.5f //차트 맨 오른쪽 간격 띄우기

        //YAxis (왼쪽) - 선 유무, 데이터 최솟값/최댓값, 색상
        val yAxisLeft: YAxis = lineChart.axisLeft
        yAxisLeft.setDrawLabels(false)
        yAxisLeft.setDrawGridLines(false)
        yAxisLeft.axisMinimum = 0f //최솟값

        //YAxis (오른쪽) - 선 유무, 데이터 최솟값/최댓값, 색상
        val yAxis: YAxis = lineChart.axisRight
        yAxis.setDrawLabels(false) //label 삭제
        yAxis.setDrawAxisLine(false)
        yAxis.setDrawGridLines(false)

        /*
        val markerData = ArrayList<Float>()
        markerData.add(4f)
        markerData.add(2f)
        markerData.add(3f)
        markerData.add(5f)
        markerData.add(4f)
        */

        //val marker = MyMarkerView(this, layoutResource = R.layout.custom_marker_view)
        //binding.lineChart.marker = marker
    }

    private fun refreshFragment(fragment: Fragment, fragmentManager: FragmentManager) {
        var fm = activity?.supportFragmentManager
        var oldFragment: Fragment? = fm?.findFragmentById(R.id.action_home)

        if (oldFragment != null) {
            fm?.beginTransaction()?.remove(oldFragment)?.commit()
        }

        val newFragment = Fragment()
        fm?.beginTransaction()?.add(newFragment, "fragment_tag")
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}