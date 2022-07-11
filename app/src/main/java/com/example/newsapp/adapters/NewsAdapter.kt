package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.NewsItemBinding
import com.example.newsapp.models.NewsArticle
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

// TODO: Implement DiffUtil
class NewsAdapter(private val newsList: ArrayList<NewsArticle>,
                  val click: OnButtonClick) : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val itemBinding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        return holder.bind(newsList[position], click)
    }

    override fun getItemCount(): Int = newsList.size

    // https://stackoverflow.com/a/60427658
    class NewsHolder(private val itemBinding: NewsItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(article: NewsArticle, OnButtonClick: OnButtonClick) {
            itemBinding.textNewsTitle.text = article.title
            itemBinding.textNewsSource.text = article.source_id
            itemBinding.textNewsDate.text = article.pubDate
            Picasso.get().load(article.image_url).into(itemBinding.imageArticle)
            itemBinding.btnRead.setOnClickListener { OnButtonClick.onArticleClick(article, position) }
        }
    }

    interface OnButtonClick {
        fun onArticleClick(article: NewsArticle, position: Int)
    }
}