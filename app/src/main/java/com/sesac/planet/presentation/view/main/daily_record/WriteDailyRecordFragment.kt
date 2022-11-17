package com.sesac.planet.presentation.view.main.daily_record

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.sesac.planet.R
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.CreateDiaryRequest
import com.sesac.planet.databinding.FragmentWriteDailyRecordBinding
import com.sesac.planet.presentation.viewmodel.dailyrecord.DailyRecordViewModel
import com.sesac.planet.presentation.viewmodel.dailyrecord.DailyRecordViewModelFactory
import com.sesac.planet.utility.Constant


class WriteDailyRecordFragment : DialogFragment() {

    private var _binding: FragmentWriteDailyRecordBinding? = null
    private val binding get() = _binding!!
    private var point: Int = 0
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var startGalleryActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var selectedImageUri: String
    private val viewModel by lazy { ViewModelProvider(this, DailyRecordViewModelFactory()).get(DailyRecordViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWriteDailyRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if(isGranted) {
                startGallery()
            } else {
                Snackbar.make(binding.root, "사진을 불러오기 위해서는 저장소 읽기 권한이 필요합니다.", Snackbar.LENGTH_SHORT).show()
            }

        }


        startGalleryActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            selectedImageUri = result.data?.getStringExtra("selected_image_uri")?.let {
                it
            } ?: ""
            Log.d("selected_uri", selectedImageUri)
            binding.firstSelectedImageContainer.visibility = View.VISIBLE
            Glide.with(binding.firstSelectedImageView.context)
                .load(selectedImageUri)
                .into(binding.firstSelectedImageView)
        }
        binding.saveButton.setOnClickListener {
            //Toast.makeText(requireActivity(), "Dialog Fragment Dismiss", Toast.LENGTH_LONG).show()
            //dialog?.dismiss()
            val journeyId = PlanetApplication.sharedPreferences.getInt(Constant.JOURNEY_ID, -1)
            val token = PlanetApplication.sharedPreferences.getString(Constant.X_ACCESS_TOKEN, "")

            if(binding.inputEmotionEditText.text.isNotEmpty() && binding.inputEmotionEditText.text.isNotEmpty() && point != 0) {

                viewModel.makeDailyRecord(
                    CreateDiaryRequest(
                        binding.inputShortReviewEditText.text.toString().trim(),
                        binding.inputEmotionEditText.text.toString().trim(),
                        point,
                        emptyList(),
                        journeyId
                    ),
                    token!!
                )
            }
        }

        binding.temporarySaveButton.setOnClickListener {
            dialog?.dismiss()
        }

        binding.onePointTextView.setOnClickListener {
            point = 1
            binding.onePointTextView.backgroundTintList = resources.getColorStateList(R.color.purple_896DF3, null)
            binding.onePointTextView.setTextColor(resources.getColor(R.color.white, null))

            binding.twoPointTextView.backgroundTintList = resources.getColorStateList(R.color.gray_F2F2F2, null)
            binding.twoPointTextView.setTextColor(resources.getColor(R.color.gray_737373, null))

            binding.threePointTextView.backgroundTintList = resources.getColorStateList(R.color.gray_F2F2F2, null)
            binding.threePointTextView.setTextColor(resources.getColor(R.color.gray_737373, null))

            binding.fourPointTextView.backgroundTintList = resources.getColorStateList(R.color.gray_F2F2F2, null)
            binding.fourPointTextView.setTextColor(resources.getColor(R.color.gray_737373, null))

            binding.fivePointTextView.backgroundTintList = resources.getColorStateList(R.color.gray_F2F2F2, null)
            binding.fivePointTextView.setTextColor(resources.getColor(R.color.gray_737373, null))
        }

        binding.twoPointTextView.setOnClickListener {
            point = 2
            binding.twoPointTextView.backgroundTintList = resources.getColorStateList(R.color.purple_896DF3, null)
            binding.twoPointTextView.setTextColor(resources.getColor(R.color.white, null))

            binding.onePointTextView.backgroundTintList = resources.getColorStateList(R.color.gray_F2F2F2, null)
            binding.onePointTextView.setTextColor(resources.getColor(R.color.gray_737373, null))

            binding.threePointTextView.backgroundTintList = resources.getColorStateList(R.color.gray_F2F2F2, null)
            binding.threePointTextView.setTextColor(resources.getColor(R.color.gray_737373, null))

            binding.fourPointTextView.backgroundTintList = resources.getColorStateList(R.color.gray_F2F2F2, null)
            binding.fourPointTextView.setTextColor(resources.getColor(R.color.gray_737373, null))

            binding.fivePointTextView.backgroundTintList = resources.getColorStateList(R.color.gray_F2F2F2, null)
            binding.fivePointTextView.setTextColor(resources.getColor(R.color.gray_737373, null))
        }

        binding.threePointTextView.setOnClickListener {
            point = 3
            binding.threePointTextView.backgroundTintList = resources.getColorStateList(R.color.purple_896DF3, null)
            binding.threePointTextView.setTextColor(resources.getColor(R.color.white, null))

            binding.onePointTextView.backgroundTintList = resources.getColorStateList(R.color.gray_F2F2F2, null)
            binding.onePointTextView.setTextColor(resources.getColor(R.color.gray_737373, null))

            binding.twoPointTextView.backgroundTintList = resources.getColorStateList(R.color.gray_F2F2F2, null)
            binding.twoPointTextView.setTextColor(resources.getColor(R.color.gray_737373, null))

            binding.fourPointTextView.backgroundTintList = resources.getColorStateList(R.color.gray_F2F2F2, null)
            binding.fourPointTextView.setTextColor(resources.getColor(R.color.gray_737373, null))

            binding.fivePointTextView.backgroundTintList = resources.getColorStateList(R.color.gray_F2F2F2, null)
            binding.fivePointTextView.setTextColor(resources.getColor(R.color.gray_737373, null))
        }

        binding.fourPointTextView.setOnClickListener {
            point = 4
            binding.fourPointTextView.backgroundTintList = resources.getColorStateList(R.color.purple_896DF3, null)
            binding.fourPointTextView.setTextColor(resources.getColor(R.color.white, null))

            binding.onePointTextView.backgroundTintList = resources.getColorStateList(R.color.gray_F2F2F2, null)
            binding.onePointTextView.setTextColor(resources.getColor(R.color.gray_737373, null))

            binding.twoPointTextView.backgroundTintList = resources.getColorStateList(R.color.gray_F2F2F2, null)
            binding.twoPointTextView.setTextColor(resources.getColor(R.color.gray_737373, null))

            binding.threePointTextView.backgroundTintList = resources.getColorStateList(R.color.gray_F2F2F2, null)
            binding.threePointTextView.setTextColor(resources.getColor(R.color.gray_737373, null))

            binding.fivePointTextView.backgroundTintList = resources.getColorStateList(R.color.gray_F2F2F2, null)
            binding.fivePointTextView.setTextColor(resources.getColor(R.color.gray_737373, null))
        }

        binding.fivePointTextView.setOnClickListener {
            point = 5
            binding.fivePointTextView.backgroundTintList = resources.getColorStateList(R.color.purple_896DF3, null)
            binding.fivePointTextView.setTextColor(resources.getColor(R.color.white, null))

            binding.onePointTextView.backgroundTintList = resources.getColorStateList(R.color.gray_F2F2F2, null)
            binding.onePointTextView.setTextColor(resources.getColor(R.color.gray_737373, null))

            binding.twoPointTextView.backgroundTintList = resources.getColorStateList(R.color.gray_F2F2F2, null)
            binding.twoPointTextView.setTextColor(resources.getColor(R.color.gray_737373, null))

            binding.threePointTextView.backgroundTintList = resources.getColorStateList(R.color.gray_F2F2F2, null)
            binding.threePointTextView.setTextColor(resources.getColor(R.color.gray_737373, null))

            binding.fourPointTextView.backgroundTintList = resources.getColorStateList(R.color.gray_F2F2F2, null)
            binding.fourPointTextView.setTextColor(resources.getColor(R.color.gray_737373, null))
        }

        binding.selectImageButton.setOnClickListener {
            //권한 확인
            checkPermission()
        }

        initObservers()

    }

    private fun initObservers() {
        viewModel.makeDailyRecordResponse.observe(viewLifecycleOwner) { response ->
            when(response.body()?.code) {
                1000 -> {
                    Toast.makeText(requireActivity(), "하루기록이 저장되었습니다.", Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()
                } else -> {
                    Toast.makeText(requireActivity(), "하루기록을 저장하지 못했습니다.", Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()
                }
            }
        }
    }

    private fun checkPermission() {
        if(ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            startGallery()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun startGallery() {
        val intent = Intent(requireActivity(), GalleryActivity::class.java)
        startGalleryActivityResultLauncher.launch(intent)
    }

    override fun onResume() {
        super.onResume()
        resizeDialog()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun resizeDialog() {
        val windowManager = requireActivity().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width: Int = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMatrix = windowManager.currentWindowMetrics
            val insets = windowMatrix.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMatrix.bounds.width() - insets.left - insets.right

        } else {
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            size.x
        }

        val layoutParams = dialog?.window?.attributes
        layoutParams?.width = (width * 0.9).toInt()
        dialog?.window?.attributes = layoutParams as WindowManager.LayoutParams
    }
}