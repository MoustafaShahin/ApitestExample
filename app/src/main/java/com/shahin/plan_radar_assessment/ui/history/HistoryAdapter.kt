package com.shahin.plan_radar_assessment.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.shahin.plan_radar_assessment.R
import com.shahin.plan_radar_assessment.data.dto.local.SavedCity
import com.shahin.plan_radar_assessment.data.dto.local.SavedDetails
import com.shahin.plan_radar_assessment.databinding.ItemCityHomeBinding
import com.shahin.plan_radar_assessment.databinding.ItemLogBinding
import com.shahin.plan_radar_assessment.utils.bind
import com.shahin.plan_radar_assessment.utils.toCelisuis

class HistoryAdapter (
    private val mItemsList: ArrayList<SavedDetails>,
    private val mItemClickListener: ( Int) -> Unit
): RecyclerView.Adapter<HistoryAdapter.HistoryItemViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryItemViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context);
        val itemBinding = DataBindingUtil.inflate<ItemLogBinding>(
            layoutInflater,
            R.layout.item_log,
            parent,
            false
        )
        return HistoryItemViewHolder(itemBinding)
    }
    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        holder.binding.tvDesc.text= mItemsList[position].desc.toString() + mItemsList[position].temp.toCelisuis().toString() + "  °C"
        holder.binding.tvTime.text =  mItemsList[position].time.toString()
        holder.binding.ivIcon.bind(mItemsList[position].icon)
        holder.itemView.setOnClickListener {
            mItemClickListener(position)
        }

    }
    class HistoryItemViewHolder(item: ItemLogBinding) : RecyclerView.ViewHolder(item.root){
        val binding : ItemLogBinding =item
    }
    fun setItems(items: List<SavedDetails>) {
        mItemsList.clear()
        mItemsList.addAll(items)
        notifyDataSetChanged()
    }
    override fun getItemCount() = mItemsList.size

}