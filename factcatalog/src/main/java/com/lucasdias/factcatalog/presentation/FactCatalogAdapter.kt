package com.lucasdias.factcatalog.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lucasdias.extensions.bind
import com.lucasdias.factcatalog.R
import com.lucasdias.factcatalog.domain.model.Fact

internal class FactCatalogAdapter : RecyclerView.Adapter<FactCatalogAdapter.ViewHolder>() {

    private var factCatalog = mutableListOf<Fact>()

    fun updateFactCatalog(facts: List<Fact>) {
        if (this.factCatalog.isNotEmpty()) {
            this.factCatalog.clear()
        }

            this.factCatalog.addAll(facts)
            notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewLayout =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fact_list_item, parent, false)
        return ViewHolder(viewLayout)
    }

    override fun getItemCount(): Int {
        return factCatalog.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (factCatalog.isNotEmpty()) {
            holder.itemBind(factCatalog[position])
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val content by bind<TextView>(itemView, R.id.fact_description_fact_list_item)

        fun itemBind(fact: Fact) {
            content.text = fact.value
        }
    }
}
