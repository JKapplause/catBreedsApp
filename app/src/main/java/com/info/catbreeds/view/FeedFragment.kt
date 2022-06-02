package com.info.catbreeds.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.info.catbreeds.R
import com.info.catbreeds.adapter.CatAdapter
import com.info.catbreeds.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : Fragment() {

    private lateinit var viewModel : FeedViewModel
    private val catAdapter = CatAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refreshData()
        catList.layoutManager = LinearLayoutManager(context)
        catList.adapter = catAdapter


        swipeRefreshLayout.setOnRefreshListener {
            catList.visibility = View.GONE
            catError.visibility  =View.GONE
            catLoading.visibility = View.VISIBLE
            viewModel.refreshFromAPI()
            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()

    }

    private fun observeLiveData() {
        viewModel.cats.observe(viewLifecycleOwner, Observer {cats ->

            cats?.let {
                catList.visibility = View.VISIBLE
                catAdapter.updateCatList(cats)
            }
        })

        viewModel.catError.observe(viewLifecycleOwner, Observer {error ->

            error?.let {
                if(it) {
                    catError.visibility = View.VISIBLE
                }else {
                    catError.visibility = View.GONE
                }
            }
        })

        viewModel.catLoading.observe(viewLifecycleOwner, Observer { loading ->

            loading?.let {
                if(it) {
                    catLoading.visibility = View.VISIBLE
                    catList.visibility = View.GONE
                    catError.visibility = View.GONE
                }else {
                    catLoading.visibility = View.GONE
                }
            }

        })


    }

}