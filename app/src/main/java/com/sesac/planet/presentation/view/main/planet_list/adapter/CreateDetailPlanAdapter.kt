package com.sesac.planet.presentation.view.main.planet_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sesac.planet.data.model.planet.CreateNewPlanetPlanListRequest
import com.sesac.planet.databinding.ItemCreatePlanetPlanBinding

class CreateDetailPlanAdapter(val items: List<CreateNewPlanetPlanListRequest>?): RecyclerView.Adapter<CreateDetailPlanAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCreatePlanetPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = items!!.size

    inner class ViewHolder(private val binding: ItemCreatePlanetPlanBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.itemCreatePlanetPlanTextView.text = items!![position].planContent
            binding.itemCreatePlanetDurationTv.text = items!![position].type
        }
    }
}