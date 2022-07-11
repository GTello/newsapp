package com.example.newsapp.models

import org.json.JSONObject

data class NewsArticle(val source_id: String, val title: String, val description: String,
                       val pubDate: String, val link: String, val image_url: String)

val articleList = ArrayList<NewsArticle>()

// Checks for null values in image_url element
fun optString(json: JSONObject, key: String, fallback: String?): String? {
    var stringToReturn = fallback
    if (!json.isNull(key)) {
        stringToReturn = json.optString(key, null)
    }
    return stringToReturn
}