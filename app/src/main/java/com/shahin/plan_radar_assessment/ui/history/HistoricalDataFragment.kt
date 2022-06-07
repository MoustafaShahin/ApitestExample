package com.shahin.plan_radar_assessment.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shahin.plan_radar_assessment.R
import com.shahin.plan_radar_assessment.databinding.FragmentHistoricalDataBinding
import com.shahin.plan_radar_assessment.ui.home.CitiesAdapter
import com.shahin.plan_radar_assessment.ui.home.HomeViewModel
import kotlinx.coroutines.launch


class HistoricalDataFragment : Fragment() {
lateinit var binding:FragmentHistoricalDataBinding
    val viewModel : HistoryViewModel by viewModels()

    private lateinit var historyAdapter: HistoryAdapter
    var cityName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
cityName = it.getString("cityName").toString()

        }
        initRv()

    }

    private fun initRv() {
        historyAdapter = HistoryAdapter(ArrayList(),{  i -> run{
            onItemClicked(i)
        }  })
    }

    private fun onItemClicked(i: Int) {
var action = HistoricalDataFragmentDirections.actionHistoricalDataFragmentToDetailsFragment(cityName, viewModel.historyData.value!![i])
        findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_historical_data, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupUi()
        setupRv()
        initObserve()

        lifecycleScope.launch {
            viewModel.getHistory(cityName)
        }
    }

    private fun setupUi() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.tvTitle.text = "$cityName History"
    }

    private fun initObserve() {
        viewModel.historyData.observe(viewLifecycleOwner,{
            if (!it.isNullOrEmpty()){
                binding.rvlog.visibility=View.VISIBLE
                historyAdapter.setItems(it)
            }else{
                binding.rvlog.visibility=View.GONE
            }

        })
    }

    private fun setupRv() {
        val linearLayoutManager = LinearLayoutManager(context)
        binding.rvlog.apply {
            layoutManager = linearLayoutManager
            adapter = historyAdapter
        }
    }



}