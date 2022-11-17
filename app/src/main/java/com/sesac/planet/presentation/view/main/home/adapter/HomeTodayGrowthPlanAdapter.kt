package com.sesac.planet.presentation.view.main.home.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sesac.planet.R
import com.sesac.planet.data.model.plan.PostDetailPlanResult
import com.sesac.planet.data.model.plan.ResultTodayGrowthPlans
import com.sesac.planet.databinding.ItemHomeTodayGrowthPlanBinding

class HomeTodayGrowthPlanAdapter(private val isShowMore: Boolean) : RecyclerView.Adapter<HomeTodayGrowthPlanAdapter.HomeTodayGrowthPlanViewHolder>(){
    private var planList = mutableListOf<ResultTodayGrowthPlans>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeTodayGrowthPlanViewHolder {
        val binding = ItemHomeTodayGrowthPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeTodayGrowthPlanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeTodayGrowthPlanViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return if(planList.size > 3){
            when(isShowMore){
                true -> planList.size
                false -> 3
            }
        } else{
            planList.size
        }
    }

    inner class HomeTodayGrowthPlanViewHolder(private val binding : ItemHomeTodayGrowthPlanBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.itemHomeTodayGrowthPlanTextView.text = planList[position].plan_content

            when(planList[position].is_completed){
                1 -> binding.itemHomeTodayGrowthPlanTextView.setTextColor(ColorStateList.valueOf(Color.parseColor("#E1E1E1")))
                0 -> binding.itemHomeTodayGrowthPlanTextView.setTextColor(ColorStateList.valueOf(Color.parseColor("#5E5E5E")))
            }

            when(planList[position].color){
                "#896DF3" -> binding.itemHomeTodayGrowthPlanImageView.setImageResource(R.drawable.ic_planet_purple)
                "#7AE3AA" -> binding.itemHomeTodayGrowthPlanImageView.setImageResource(R.drawable.ic_planet_mint)
                "#FFC212" -> binding.itemHomeTodayGrowthPlanImageView.setImageResource(R.drawable.ic_planet_mustard)
                "#F8CBB6" -> binding.itemHomeTodayGrowthPlanImageView.setImageResource(R.drawable.ic_planet_pink)
                "#F2606A" -> binding.itemHomeTodayGrowthPlanImageView.setImageResource(R.drawable.ic_planet_hotpink)
                "#E0DFFE" -> binding.itemHomeTodayGrowthPlanImageView.setImageResource(R.drawable.ic_planet_navy)
                "#D3FB03" -> binding.itemHomeTodayGrowthPlanImageView.setImageResource(R.drawable.ic_planet_lime)
                "#FDFE00" -> binding.itemHomeTodayGrowthPlanImageView.setImageResource(R.drawable.ic_planet_lemon)
                "#B4C9FF" -> binding.itemHomeTodayGrowthPlanImageView.setImageResource(R.drawable.ic_planet_sora)
                "#E1E1E1" -> binding.itemHomeTodayGrowthPlanImageView.setImageResource(R.drawable.ic_planet_mono)
                else -> binding.itemHomeTodayGrowthPlanImageView.setImageResource(R.drawable.ic_planet_basic)
            }

            binding.root.setOnClickListener {
                val currentColor = binding.itemHomeTodayGrowthPlanTextView.currentTextColor
                if(currentColor == R.color.gray_5E5E5E) {
                    //완료 X -> 완료
                    Log.d("TextColor", "5e5e5e")
                    binding.itemHomeTodayGrowthPlanTextView.setTextColor(binding.itemHomeTodayGrowthPlanTextView.context.resources.getColor(R.color.gray_E1E1E1, null))
                } else {
                    // 완료 -> x
                    Log.d("TextColor", "e1e1e1")

                    binding.itemHomeTodayGrowthPlanTextView.setTextColor(binding.itemHomeTodayGrowthPlanTextView.context.resources.getColor(R.color.gray_5E5E5E, null))

                }

                itemClickListener.onClick(it, position, planList[position].detailed_plan_id)
            }

        }
    }

    fun setData(item: List<ResultTodayGrowthPlans>) {
        if(!planList.contains(item[0])) {
            planList.addAll(0, item)
            notifyDataSetChanged()
        }
    }

    interface OnItemClickListener{
        fun onClick(v: View, position: Int, detailedPlanId: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener
}