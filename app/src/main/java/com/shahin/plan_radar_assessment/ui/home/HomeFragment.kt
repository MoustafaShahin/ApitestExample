package com.shahin.plan_radar_assessment.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.constraintlayout.helper.widget.Flow
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shahin.plan_radar_assessment.R
import com.shahin.plan_radar_assessment.data.dto.remoteData.ResponseObject
import com.shahin.plan_radar_assessment.databinding.FragmentHomeBinding
import com.shahin.plan_radar_assessment.utils.GeneralResponse.Status
import com.shahin.plan_radar_assessment.utils.bind
import com.shahin.plan_radar_assessment.utils.toCelisuis
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
lateinit var binding :FragmentHomeBinding
    private lateinit var citiesAdapter: CitiesAdapter

val viewModel :HomeViewModel by viewModels()
    lateinit var bottomSheetDialog  : BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRv()
    }

    private fun initRv() {
        citiesAdapter = CitiesAdapter(ArrayList(),{  i -> run{
            onItemClicked(i)
        }  },{  i -> run{
            onInfoClicked(i)
        }  },{  i -> run{
            onremoveClicked(i)
        }  })
    }

    private fun onInfoClicked(i: Int) {
val action =  HomeFragmentDirections.actionHomeFragmentToHistoricalDataFragment(viewModel.citiesData?.value!![i].name)
        findNavController().navigate(action)

    }

    private fun onremoveClicked(i: Int) {
        lifecycleScope.launch {
            viewModel.deleteCity(viewModel.citiesData?.value!![i]?.name)
        }
    }

    private fun onItemClicked(i: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(viewModel.citiesData?.value!![i].name)
        findNavController().navigate(action)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetDialog = BottomSheetDialog(requireContext())
        initObservers()
        initClicks()
        setupRv()
        lifecycleScope.launch {
            viewModel.getCities()
        }
    }

    private fun setupRv() {
        val linearLayoutManager = LinearLayoutManager(context)
        binding.rvCities.apply {
            layoutManager = linearLayoutManager
            adapter = citiesAdapter
        }
    }


    private fun initObservers() {
        viewModel.citiesData.observe(viewLifecycleOwner,{
            if (!it.isNullOrEmpty()){
                binding.rvCities.visibility=View.VISIBLE
            citiesAdapter.setItems(it)
            }else{
binding.rvCities.visibility=View.GONE
            }

        })
        viewModel.searchData.observe(viewLifecycleOwner,{
            when(it?.status){
                Status.Loading ->{
                if (bottomSheetDialog.isShowing){

                    bottomSheetDialog.findViewById<Flow>(R.id.flow)?.visibility =View.INVISIBLE
                    bottomSheetDialog.findViewById<ProgressBar>(R.id.progressBar)?.visibility= View.VISIBLE
                }
                }
                Status.Failure ->{
                    bottomSheetDialog.findViewById<ProgressBar>(R.id.progressBar)?.visibility= View.GONE
                    Toast.makeText(requireContext(), it.error.msg.toString(), Toast.LENGTH_SHORT).show()}

                Status.Success -> {
                    if (bottomSheetDialog.isShowing){
                        bottomSheetDialog.findViewById<ProgressBar>(R.id.progressBar)?.visibility= View.GONE
                        bindDatatoSheet(it.data)
                    }

                }
            }
        })
    }

    private fun bindDatatoSheet(data: ResponseObject?) {
            if (bottomSheetDialog.isShowing){
                bottomSheetDialog.findViewById<Flow>(R.id.flow)?.visibility= View.VISIBLE
                bottomSheetDialog.findViewById<ImageView>(R.id.ivIcon)?.bind(if (data?.weather.isNullOrEmpty()){""}else{ data?.weather!![0].icon.toString()})
                bottomSheetDialog.findViewById<TextView>(R.id.tvCityName)?.text = data?.name.toString()
                bottomSheetDialog.findViewById<TextView>(R.id.tvDescription)?.text = if (data?.weather.isNullOrEmpty()){""}else{ data?.weather!![0].description.toString()}
                bottomSheetDialog.findViewById<TextView>(R.id.tvHumidity)?.text = data?.main?.humidity.toString() + " %"
                bottomSheetDialog.findViewById<TextView>(R.id.tvWindspeed)?.text = data?.wind?.speed.toString()+" km/h"
                bottomSheetDialog.findViewById<TextView>(R.id.tvTemperature)?.text = data?.main?.temp?.toCelisuis().toString() + "  °C"
                lifecycleScope.launch {
                    viewModel.saveCity(data?.name.toString())
                }            }
    }

    private fun initClicks() {
        binding.cardAdd.setOnClickListener {
            openSearchDialog()
        }
    }

    private fun openSearchDialog() {
            bottomSheetDialog.setContentView(R.layout.sheet_input_city);
        bottomSheetDialog.findViewById<Flow>(R.id.flow)?.visibility =View.INVISIBLE
        bottomSheetDialog.findViewById<EditText>(R.id.etSearch)?.setOnEditorActionListener { textView, i, keyEvent ->
if (i == EditorInfo.IME_ACTION_SEARCH ){
    if (textView.text.toString().length == 0) {
        return@setOnEditorActionListener false
    }else{
        lifecycleScope.launch {
            viewModel.getDetails(textView.text.toString())

        }
return@setOnEditorActionListener true
    }
}else{
    return@setOnEditorActionListener false
}
        }
        bottomSheetDialog.show()

    }


}