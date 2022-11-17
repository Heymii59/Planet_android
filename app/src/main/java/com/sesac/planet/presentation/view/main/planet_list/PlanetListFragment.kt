package com.sesac.planet.presentation.view.main.planet_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.planet.RevisePlanetRequest
import com.sesac.planet.databinding.FragmentPlanetListBinding
import com.sesac.planet.presentation.view.main.planet_list.adapter.PlanetListAdapter
import com.sesac.planet.presentation.viewmodel.main.planet.*
import com.sesac.planet.utility.Constant
import com.sesac.planet.utility.SystemUtility

class PlanetListFragment() : Fragment(){
    private var _binding: FragmentPlanetListBinding? = null
    private val binding get() = _binding!!

    private var token = PlanetApplication.sharedPreferences.getString(Constant.X_ACCESS_TOKEN, "")
    private var journeyId = PlanetApplication.sharedPreferences.getInt(Constant.JOURNEY_ID, 0)

    private lateinit var planetListAdapter: PlanetListAdapter

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            PlanetViewModelFactory()
        )[PlanetInfoViewModel::class.java]
    }

    private val newPlanetViewModel by lazy {
        ViewModelProvider(
            this,
            NewPlanetViewModelFactory()
        )[NewPlanetViewModel::class.java]
    }

    private val deletePlanetViewModel by lazy {
        ViewModelProvider(
            this,
            DeletePlanetViewModelFactory()
        )[DeletePlanetViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlanetListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        initView()
    }

    private fun initialize() {
        SystemUtility.applyWindowInsetsTopPadding(binding.root)
    }

    private fun initView() {
        initPlanetListRecyclerView()
        binding.planetListMenuOptionBtn.setOnClickListener {
            val intent = Intent(requireContext(), CreatePlanetActivity::class.java)
            startActivity(intent)
        }

        binding.refreshContainer.setOnRefreshListener {
            token?.let {
                viewModel.getPlanet(
                    it,
                    journeyId
                )
            }
        }
    }

    private fun initPlanetListRecyclerView() {
        initObservers()
        token?.let {
            viewModel.getPlanet(
                it,
                journeyId
            )
        }
    }

    private fun initObservers() {
        newPlanetViewModel.newPlanetData.observe(viewLifecycleOwner){response ->
            if(response.isSuccessful){
                Log.d("CREATE_RESULT >> ", "${response.body()}")
            }
        }

        viewModel.planetData.observe(viewLifecycleOwner) { response ->
            binding.refreshContainer.isRefreshing = false
            if (response.isSuccessful) {
                response.body()?.result.let { body ->
                    if (body == null) {

                    } else {
                        planetListAdapter = PlanetListAdapter()
                        binding.planetListRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                        binding.planetListRecyclerView.adapter = planetListAdapter
                        planetListAdapter.setData(body)

                        planetListAdapter.setItemClickListener(
                            object : PlanetListAdapter.OnItemClickListener {
                                override fun onClick(v: View, position: Int) {
                                    val intent = Intent(requireContext(), PlanetDetailActivity::class.java)
                                    intent.putExtra("planet_id", body?.get(position)?.planet_id)
                                    startActivity(intent)
                                }
                            })

                        planetListAdapter.setItemLongClickListener(
                            object : PlanetListAdapter.OnItemLongClickListener{
                                override fun onLongClick(
                                    v: View,
                                    position: Int,
                                    deletePlanetId: Int
                                ) {
                                    val deleteDialog = DeletePlanetDialog(deletePlanetId)
                                    activity?.let { deleteDialog.show(it.supportFragmentManager, "DeletePlanetDialog") }

                                    deletePlanetViewModel.deletePlanetData.observe(viewLifecycleOwner){response ->
                                        if(response.isSuccessful){
                                            Log.d("TAG : ", response.body().toString())
                                        }
                                    }
                                }
                            }
                        )

                    }
                }
            } else {
                //서버에 문제가 생겼을 때
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}