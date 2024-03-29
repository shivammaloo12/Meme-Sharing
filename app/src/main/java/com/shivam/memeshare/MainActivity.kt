package com.shivam.memeshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    var url:String?=null
    private fun loadMeme(){


        val currentURL = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, currentURL,null,
            Response.Listener { response ->
                // Display the first 500 characters of the response string.
                 url=response.getString("url")

                Glide.with(this).load(url).into(imageMeme)
            },
            Response.ErrorListener {
                Toast.makeText(this,"Someting is Wrong...",Toast.LENGTH_LONG).show()
            })

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    fun btnNext(view: View) {
        loadMeme()
    }
    fun btnShare(view: View) {
        intent= Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey!! Checkout this anazing meme : $url" )
        val chooser=Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)
    }
}