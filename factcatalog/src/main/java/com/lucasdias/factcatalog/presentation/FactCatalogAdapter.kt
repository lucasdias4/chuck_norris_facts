package com.lucasdias.factcatalog.presentation

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lucasdias.extensions.bind
import com.lucasdias.factcatalog.R
import com.lucasdias.factcatalog.domain.model.Fact
import java.util.Locale

internal class FactCatalogAdapter(private val shareUrl: ((String) -> Unit)?) :
    RecyclerView.Adapter<FactCatalogAdapter.ViewHolder>() {

    internal companion object {
        const val UNCATEGORIZED = "uncategorized"
        const val TEXT_SIZE_LIMIT = 80
        const val BIG_TEXT_SIZE = 23F
        const val SMALL_TEXT_SIZE = 18F
    }

    private val factCatalog = mutableListOf<Fact>()

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
            holder.bind(fact = factCatalog[position], shareUrl = shareUrl)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val content by bind<TextView>(itemView, R.id.fact_description_fact_list_item)
        private val shareIcon by bind<ImageView>(itemView, R.id.share_icon_fact_list_item)
        private val category by bind<TextView>(itemView, R.id.category_text_fact_list_item)

        fun bind(
            fact: Fact,
            shareUrl: ((String) -> Unit)?
        ) {
            contentSetup(fact = fact, content = content)
            categorySetup(fact = fact, categoryTextView = category)
            shareIconSetup(url = fact.url, shareIcon = shareIcon, shareUrl = shareUrl)
        }

        internal fun categorySetup(
            fact: Fact,
            categoryTextView: TextView
        ) {
            val categories = fact.categories
            var category = categories?.first() ?: UNCATEGORIZED
            category = category.toLowerCase(Locale.ROOT)
            categoryTextView.text = category
        }

        internal fun contentSetup(
            fact: Fact,
            content: TextView
        ) {
            val textSize = fact.value.length
            val needToIncreaseTheFontSize = textSize < TEXT_SIZE_LIMIT
            if (needToIncreaseTheFontSize) content.setTextSize(
                TypedValue.COMPLEX_UNIT_SP, BIG_TEXT_SIZE
            )
            else content.setTextSize(TypedValue.COMPLEX_UNIT_SP, SMALL_TEXT_SIZE)
            content.text = fact.value
        }

        private fun shareIconSetup(
            url: String,
            shareIcon: ImageView,
            shareUrl: ((String) -> Unit)?
        ) {
            shareIcon.setOnClickListener {
                shareUrl?.let { method ->
                    method(url)
                }
            }
        }
    }
}
