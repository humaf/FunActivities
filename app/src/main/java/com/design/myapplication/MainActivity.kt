package com.design.myapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.design.myapplication.model.Item
import com.design.myapplication.network.MyApi
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getData()

        btn.setOnClickListener {
            text.setVisibility(View.VISIBLE)
            btn.setVisibility(View.GONE)
            card.setVisibility(View.VISIBLE)

        }

        okbtn.setOnClickListener {
            card.setVisibility(View.GONE)
            val refreshActivity = intent
            finish()
            startActivity(refreshActivity)
        }
    }


    private fun getData() : String {

        var res = ""

        MyApi().getItems().enqueue(object : Callback<Item> {
            override fun onFailure(call: Call<Item>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Something went wrong, Please check your INTERNET CONNECTION",
                    Toast.LENGTH_LONG
                ).show()

            }

            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                Log.i("Json Response", response.body().toString())
                val items = response.body().toString()

                val domain: String? = items.substringAfterLast("=")
                res = domain.toString()
                Log.i("rrrrrrrrrrrrrrr",res)

                items?.let {

                       text.setText(domain)

                }
            }

        })
            return res

    }
}




