package com.example.newsfresh

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), NewsItemClicked {
    private val API_KEY: String = BuildConfig.ApiKey
    private lateinit var mAdapter:NewsListAdapter
    var url:String = "https://newsapi.org/v2/top-headlines?country=in&apiKey=${API_KEY}"
    private lateinit var mcurr:News

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this, this)
        recyclerView.adapter = mAdapter


    }

    fun fetchData() {


        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                        mcurr=news
                    newsArray.add(news)
                }

                mAdapter.updateNews(newsArray)
            },
            {
                Toast.makeText(this, "Not called", Toast.LENGTH_SHORT).show()
            }

        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers

            }
        };


        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


    }

    override fun onItemClicked(item: News) {
        //Toast.makeText(this, "clicked item is $item", Toast.LENGTH_SHORT).show()
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.category_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.business -> {
                titletv.text = getString(R.string.business)
                url = "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=${API_KEY}"
                fetchData()
                true
            }
            R.id.entertainment -> {
                titletv.text = getString(R.string.entertainment)
                url = "https://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=${API_KEY}"
                fetchData()
                true
            }
            R.id.general -> {
                titletv.text = getString(R.string.general)
                url = "https://newsapi.org/v2/top-headlines?country=in&category=general&apiKey=${API_KEY}"
                fetchData()
                true
            }
            R.id.health -> {
                titletv.text = getString(R.string.health)
                url = "https://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=${API_KEY}"
                fetchData()
                true
            }
            R.id.science -> {
                titletv.text = getString(R.string.science)
                url = "https://newsapi.org/v2/top-headlines?country=in&category=science&apiKey=${API_KEY}"
                fetchData()
                true
            }
            R.id.sports -> {
                titletv.text = getString(R.string.sports)
                url = "https://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=${API_KEY}"
                fetchData()
                true
            }
            R.id.technology -> {
                titletv.text = getString(R.string.technology)
                url = "https://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=${API_KEY}"
                fetchData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }}
