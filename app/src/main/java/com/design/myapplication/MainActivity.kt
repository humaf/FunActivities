package com.design.myapplication



import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
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

        btn.setOnClickListener {
           // text.setVisibility(View.VISIBLE)
            if(isNetworkConnected()) {

                var r = getData()
                Log.i("wwwwwwwwwww", r)

            }
                else
            {

                AlertDialog.Builder(this,R.style.AlertDialogTheme).setTitle("No Internet Connection")
                    .setMessage("Please check your internet connection and try again")
                    .setPositiveButton(android.R.string.ok) { _, _ -> }
                    .setIcon(android.R.drawable.ic_dialog_alert).show()

            }


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

                val domain: String = items.substringAfterLast("=")
                res = domain.dropLast(1)
                Log.i("rrrrrrrrrrrrrrr",res)

                items?.let {

                    showd(res)

                }
            }

        })


       return res

    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkConnected(): Boolean {
        //1
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //2
        val activeNetwork = connectivityManager.activeNetwork
        //3
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        //4
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }


    private fun showd(message : String){

        //    val builder = AlertDialog.Builder(this)

       val builder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.AlertDialogTheme))
        builder.setPositiveButton("Okay",null)
        builder.setMessage(message)
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()

        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()

    }


}




