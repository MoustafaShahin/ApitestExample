package com.shahin.plan_radar_assessment.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.shahin.plan_radar_assessment.R
import com.shahin.plan_radar_assessment.data.dto.local.SavedCity
import com.shahin.plan_radar_assessment.databinding.ItemCityHomeBinding

class CitiesAdapter (
    private val mItemsList: ArrayList<SavedCity>,
    private val mItemClickListener: ( Int) -> Unit,
    private val mItemInfo: ( Int) -> Unit,
    private val mItemRemove: ( Int) -> Unit
        ): RecyclerView.Adapter<CitiesAdapter.CityItemViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CityItemViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context);
        val itemBinding = DataBindingUtil.inflate<ItemCityHomeBinding>(
            layoutInflater,
            R.layout.item_city_home,
            parent,
            false
        )
        return CityItemViewHolder(itemBinding)
    }
    override fun onBindViewHolder(holder: CityItemViewHolder, position: Int) {
holder.binding.tvCityName.text= mItemsList[position].name.toString()
        holder.binding.tvCityName.setOnClickListener {
            mItemClickListener(position)
        }

        holder.binding.ivInfo.setOnClickListener {
            mItemInfo(position)
        }
        holder.binding.ivRemove.setOnClickListener {
            mItemRemove(position)
        }
    }
    class CityItemViewHolder(item: ItemCityHomeBinding) : RecyclerView.ViewHolder(item.root){
        val binding :ItemCityHomeBinding =item
    }
    fun setItems(items: List<SavedCity>) {
        mItemsList.clear()
        mItemsList.addAll(items)
        notifyDataSetChanged()
    }
    override fun getItemCount() = mItemsList.size

}