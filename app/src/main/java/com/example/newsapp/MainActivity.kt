package com.example.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.models.NewsArticle
import com.example.newsapp.models.articleList
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity(), NewsAdapter.OnButtonClick {
    var articleAdapter: NewsAdapter?=null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestNews()
        articleAdapter = NewsAdapter(articleList, this)
        //binding.mainRecycler.setHasFixedSize(true)
        binding.mainRecycler.layoutManager = LinearLayoutManager(baseContext)
        binding.mainRecycler.adapter = articleAdapter

    }

    fun requestNews() {
        val url = "https://newsdata.io/api/1/news?apikey=pub_89886a223b319a6275fa1a374074e2b2f3bc&language=en"
        val requestQueue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                // Log.d("GET", response.toString())
                articleList.removeAll(articleList.toSet())
                articleList.addAll(parseJSON(response))
                articleAdapter!!.notifyDataSetChanged()
                for (i in articleList) {
                    Log.d("Test", i.toString())
                }
            },
            { error -> Log.d("Test", error.toString())
                // TODO: Handle error
            }
        )
        requestQueue.add(jsonObjectRequest)
    }

    // https://stackoverflow.com/a/36188796
    fun parseJSON(JSON: JSONObject): ArrayList<NewsArticle> {
        val articles: JSONArray = JSON.getJSONArray("results")
        operator fun JSONArray.iterator(): Iterator<JSONObject>
            = (0 until length()).asSequence().map { get(it) as JSONObject }.iterator()
        for (i in articles) {
            val source = i.getString("source_id")
            val title = i.getString("title")
            val description = i.getString("description")
            val date = i.getString("pubDate")
            val url = i.getString("link")
            val imgURL = i.optString("image_url", null.toString())
            articleList.add(NewsArticle(source, title, description, date, url, imgURL))
        }
        return articleList
    }

    override fun onArticleClick(article: NewsArticle, position: Int) {
        val intent = Intent(this@MainActivity, WebViewActivity::class.java)
        intent.putExtra("article", position)
        startActivity(intent)
    }

}