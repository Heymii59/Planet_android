package com.sesac.planet.presentation.view.main.planet_list.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sesac.planet.data.model.planet.ResultPlanetInfo
import com.sesac.planet.databinding.ItemPlanetListBinding

class PlanetListAdapter(private val items: List<ResultPlanetInfo>?) : RecyclerView.Adapter<PlanetListAdapter.PlanetListViewHolder>(){
    private var planetId: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetListViewHolder {
        val binding = ItemPlanetListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanetListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlanetListViewHolder, position: Int) {
        holder.bind(position)
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }

        holder.itemView.setOnLongClickListener{
            itemLongClickLongListener.onLongClick(it, position, items!![position].planet_id)
            true
        }
    }

    override fun getItemCount() = items!!.size

    inner class PlanetListViewHolder(private val binding : ItemPlanetListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            planetId = items!![position].planet_id

            binding.itemPlanetListImg.imageTintList = ColorStateList.valueOf(Color.parseColor(items!![position].color))
            binding.itemPlanetListPlanetTextView.text = "${items!![position].planet_name} 행성"
            binding.itemPlanetListExplainPlanetTextView.text = items!![position].planet_intro
            binding.itemPlanetListLevelTextView.text = "LV.${items!![position].planet_level}"
            binding.itemPlanetListLevelProgressBar.max = items!![position].plan_count
            binding.itemPlanetListLevelProgressBar.progress = items!![position].planet_exp
            binding.itemPlanetListLevelProgressBar.progressTintList = ColorStateList.valueOf(Color.parseColor(items!![position].color))
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    interface OnItemLongClickListener{
        fun onLongClick(v: View, position: Int, deletePlanetId: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }

    fun setItemLongClickListener(onItemLongClickListener: OnItemLongClickListener){
        this.itemLongClickLongListener = onItemLongClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener

    private lateinit var itemLongClickLongListener: OnItemLongClickListener
}