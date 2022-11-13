package com.sesac.planet.presentation.view.main.planet_list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sesac.planet.data.model.planet.ResultPlanetDetailPlan
import com.sesac.planet.databinding.ItemPlanetDetailDetailsModifyPlanBinding
import com.sesac.planet.presentation.view.main.planet_list.ItemActionListener
import com.sesac.planet.presentation.view.main.planet_list.ItemDragListener
import com.sesac.planet.presentation.view.main.planet_list.OnDeletePlanResult

class PlanetDetailModifyAdapter(
    private val items: MutableList<ResultPlanetDetailPlan>,
    private val onDeletePlanResult: OnDeletePlanResult,
    private val listener: ItemDragListener
): RecyclerView.Adapter<PlanetDetailModifyAdapter.PlanDetailModifyViewHolder>(), ItemActionListener {
    @SuppressLint("ClickableViewAccessibility")
    inner class PlanDetailModifyViewHolder(
        private val binding: ItemPlanetDetailDetailsModifyPlanBinding,
        listener: ItemDragListener
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.itemPlanetDetailModifyDragImageView.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    listener.onStartDrag(this)
                }
                false
            }

            binding.itemPlanetDetailModifyDeleteImageView.setOnClickListener {
                val position: Int = adapterPosition

                //삭제 API 연결
                onDeletePlanResult.onDeletePlanResult(items!![position].detailed_plan_id)

                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }

        fun bind(position: Int) {
            binding.itemPlanetDetailModifyToDoTextView.hint = items!![position].plan_name

            if(items!![position].type.contains(":")){
                binding.itemPlanetDetailModifyDurationTextView.text = items!![position].type.substring(5)
            } else{
                binding.itemPlanetDetailModifyDurationTextView.text = items!![position].type
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlanetDetailModifyAdapter.PlanDetailModifyViewHolder {
        val binding = ItemPlanetDetailDetailsModifyPlanBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlanDetailModifyViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: PlanetDetailModifyAdapter.PlanDetailModifyViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = items.size

    interface OnItemClickListener{
        fun onDeleteClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener

    override fun onItemMoved(from: Int, to: Int) {
        if (from == to) {
            return
        }

        val fromItem = items.removeAt(from)
        items.add(to, fromItem)
        notifyItemMoved(from, to)
    }

    override fun onItemSwiped(position: Int) {
    }
}