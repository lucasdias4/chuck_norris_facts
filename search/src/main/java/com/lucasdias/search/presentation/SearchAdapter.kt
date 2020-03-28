package com.lucasdias.search.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lucasdias.extensions.bind
import com.lucasdias.search.R

internal class SearchAdapter(private val searchActionMethod: ((String) -> Unit)?) :
        RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var searchList = mutableListOf<String>()

    fun updateSearchTextList(searchList: ArrayList<String>) {
        if (this.searchList.isNotEmpty()) {
            this.searchList.clear()
        }
        this.searchList.addAll(searchList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewLayout = LayoutInflater.from(
                parent.context
        ).inflate(
                R.layout.search_item, parent, false
        )
        return ViewHolder(viewLayout)
    }

    override fun getItemCount(): Int {
        return searchList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (searchList.isNotEmpty()) {
            holder.itemBind(searchList[position], searchActionMethod)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val searchText by bind<TextView>(itemView, R.id.search_item_text)

        fun itemBind(text: String, searchActionMethod: ((String) -> Unit)?) {
            searchText.text = text
            itemView.setOnClickListener {
                searchActionMethod?.let { method ->
                    method(text)
                }
            }
        }
    }
}
