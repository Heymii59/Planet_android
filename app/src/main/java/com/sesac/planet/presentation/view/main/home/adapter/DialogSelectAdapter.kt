package com.sesac.planet.presentation.view.main.home.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.sesac.planet.R
import com.sesac.planet.data.model.planet.ResultPlanetInfo
import com.sesac.planet.databinding.ItemDialogAddToDoPlanetBinding
import com.sesac.planet.presentation.view.main.home.OnSelectPlanetResult

class DialogSelectAdapter(val items: List<ResultPlanetInfo>?, private val onSelectPlanet: OnSelectPlanetResult) :
    RecyclerView.Adapter<DialogSelectAdapter.ViewHolder>() {
    private lateinit var binding: ItemDialogAddToDoPlanetBinding

    private var mSelectedItem = -1
    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemDialogAddToDoPlanetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = items!!.size

    inner class ViewHolder(private val binding: ItemDialogAddToDoPlanetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            if(selectedPosition == position) {
                binding.itemDialogPlanetImageView.background = ResourcesCompat.getDrawable(binding.root.resources, R.drawable.ic_checked_planet, null)
                binding.itemDialogPlanetImageView.backgroundTintList = ColorStateList.valueOf(Color.parseColor(items!![position].color))
            } else {
                binding.itemDialogPlanetImageView.background = ResourcesCompat.getDrawable(binding.root.resources, R.drawable.shape_rectangle_radius_10dp_stroke_1dp, null)
                binding.itemDialogPlanetImageView.backgroundTintList = ColorStateList.valueOf(Color.parseColor(items!![position].color))
            }

            binding.root.setOnClickListener {
                selectedPosition = adapterPosition

                if(lastSelectedPosition == -1) {
                    lastSelectedPosition = selectedPosition
                } else {
                    notifyItemChanged(lastSelectedPosition)
                    lastSelectedPosition = selectedPosition
                }
                onSelectPlanet.onItemClickResult(items!![position].planet_id)
                notifyItemChanged(selectedPosition)
            }

            binding.itemDialogPlanetTv.text = items!![position].planet_name
        }
    }


    fun getSelectedPlanet(): ResultPlanetInfo? {
        return items?.get(lastSelectedPosition)
    }

}