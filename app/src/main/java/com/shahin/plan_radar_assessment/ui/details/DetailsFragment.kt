package com.shahin.plan_radar_assessment.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.helper.widget.Flow
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahin.plan_radar_assessment.R
import com.shahin.plan_radar_assessment.data.dto.local.SavedDetails
import com.shahin.plan_radar_assessment.data.dto.remoteData.ResponseObject
import com.shahin.plan_radar_assessment.databinding.FragmentDetailsBinding
import com.shahin.plan_radar_assessment.ui.home.HomeViewModel
import com.shahin.plan_radar_assessment.utils.GeneralResponse.Status
import com.shahin.plan_radar_assessment.utils.bind
import com.shahin.plan_radar_assessment.utils.timeStamp
import com.shahin.plan_radar_assessment.utils.toCelisuis

class DetailsFragment : Fragment() {
    val viewModel : HomeViewModel by viewModels()
    var cityName = ""
    lateinit var history :SavedDetails
    var isHistory = false
lateinit var binding :FragmentDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        cityName = it.getString("cityName").toString()
            if (it.getSerializable("details")!= null){
                isHistory = true
                history = it.getSerializable("details") as SavedDetails
            }else{
                viewModel.getDetails(cityName)
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_details, container, false)
            return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        if (!isHistory){
            initObserver()
        }else{
            bindData()
        }
    }

    private fun bindData() {
        binding.tvtime.text = history.time.toString()
        binding.tvlbl1.text = "Weather information for ${history?.name.toString()} received on"
        binding.tvCityName.text = history?.name.toString()
        binding.tvDescription.text = history.desc
        binding.ivIcon.bind(history.icon)
        binding.tvHumidity.text = history.humidity.toString() + " %"
        binding.tvWindspeed.text = history.wind.toString()+" km/h"
        binding.tvTemperature.text = history.temp.toCelisuis().toString() + "  °C"
    }

    private fun initObserver() {
        viewModel.searchData.observe(viewLifecycleOwner,{
            when(it?.status){
                Status.Loading->{
binding.progressBar.visibility=View.VISIBLE
                }
                Status.Failure ->{
                    binding.progressBar.visibility=View.GONE
                    Toast.makeText(requireContext(), it.error.msg.toString(), Toast.LENGTH_SHORT).show()
                }
                Status.Success ->{
                    binding.progressBar.visibility=View.GONE
                    bindRemoteData(it.data)
                }
            }
        })
    }

    private fun bindRemoteData(data: ResponseObject?) {
        binding.tvCityName.text = data?.name.toString() + " , " + data?.sys?.country.toString()
        binding.tvDescription.text = if (data?.weather.isNullOrEmpty()){""}else{ data?.weather!![0].description.toString()}
        binding.ivIcon.bind(if (data?.weather.isNullOrEmpty()){""}else{ data?.weather!![0].icon.toString()})
        binding.tvHumidity.text = data?.main?.humidity.toString() + " %"
        binding.tvWindspeed.text = data?.wind?.speed.toString()+" km/h"
        binding.tvTemperature.text = data?.main?.temp?.toCelisuis().toString() + "  °C"
        binding.tvlbl1.text = "Weather information for ${data?.name.toString()} received on"
        binding.tvtime.text = timeStamp()
    }

}