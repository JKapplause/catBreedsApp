package com.info.catbreeds.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.info.catbreeds.R
import com.info.catbreeds.databinding.FragmentCatBinding
import com.info.catbreeds.viewmodel.CatViewModel


class CatFragment : Fragment() {

    private lateinit var viewModel : CatViewModel

    private var catUuid = 0
    private lateinit var dataBinding : FragmentCatBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_cat,container,false)
        print(dataBinding)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            catUuid = CatFragmentArgs.fromBundle(it).catUuid
        }
        viewModel = ViewModelProviders.of(this).get(CatViewModel::class.java)
        viewModel.getDataFromRoom(catUuid)

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.catLiveData.observe(viewLifecycleOwner, Observer { cats ->
            cats?.let {
                dataBinding.selectedCat = it
            }
        })
    }


}