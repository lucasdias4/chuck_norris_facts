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

    private val searchList = mutableListOf<String>()

    fun updateSearchHistoric(searchHistoric: List<String>) {
        if (this.searchList.isNotEmpty()) {
            this.searchList.clear()
        }
        this.searchList.addAll(searchHistoric)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewLayout = LayoutInflater.from(
            parent.context
        ).inflate(
            R.layout.search_item, parent, false
        )
        return ViewHolder(itemView = viewLayout)
    }

    override fun getItemCount(): Int {
        return searchList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (searchList.isNotEmpty()) {
            holder.bind(text = searchList[position], searchActionMethod = searchActionMethod)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val searchText by bind<TextView>(itemView, R.id.search_item_text)

        fun bind(text: String, searchActionMethod: ((String) -> Unit)?) {
            searchText.text = text
            itemView.setOnClickListener {
                searchActionMethod?.let { method ->
                    method(text)
                }
            }
        }
    }
}
