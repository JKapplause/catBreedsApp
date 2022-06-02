package com.info.catbreeds.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.info.catbreeds.R
import com.info.catbreeds.databinding.ItemCatBinding
import com.info.catbreeds.model.Cat
import com.info.catbreeds.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_cat.view.*


class CatAdapter(val catList: ArrayList<Cat>): RecyclerView.Adapter<CatAdapter.CatViewHolder>(), CatClickListener{

    class CatViewHolder(var view: ItemCatBinding) : RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemCatBinding>(inflater,R.layout.item_cat, parent, false)
        return CatViewHolder(view)
    }
     override fun getItemCount(): Int {
        return catList.size
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {

        holder.view.cat = catList[position]
        holder.view.listener = this

    }

    fun updateCatList(newCatList: List<Cat>) {
        catList.clear()
        catList.addAll(newCatList)
        notifyDataSetChanged()
    }

    override fun onCatClicked(v: View) {
        val uuid = v.catUuidText.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToCatFragment()
        action.catUuid = uuid
        Navigation.findNavController(v).navigate(action)
    }
}