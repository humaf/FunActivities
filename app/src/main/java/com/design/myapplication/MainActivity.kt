package com.design.myapplication


import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.design.myapplication.model.Item
import com.design.myapplication.network.MyApi
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

   //    var result :String = getData()

   //     Log.i("vvvvvvvvvvvv",result)
       // getData()


        btn.setOnClickListener {
           // text.setVisibility(View.VISIBLE)
           var r= getData()
            Log.i("wwwwwwwwwww",r)
//            btn.setVisibility(View.GONE)



            // card.setVisibility(View.VISIBLE)

     /*       val builder = AlertDialog.Builder(this)

            with(builder)
            {
                setTitle("Hello")
                setMessage(result)
                setPositiveButton("OK", null)
                show()
            }


      */

        }

        okbtn.setOnClickListener {
            card.setVisibility(View.GONE)
            val refreshActivity = intent
            finish()
            startActivity(refreshActivity)
        }

    }


   private fun getData() : String {

  //  private fun getData(){

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
                res = domain.toString()
                Log.i("rrrrrrrrrrrrrrr",res)

                items?.let {

                    showd(res)

                }
            }

        })


       return res

    }


    private fun showDialog(title: String) {
        val dialog = Dialog(applicationContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_layout)
      //  val body = dialog.findViewById(R.id.dialogText) as TextView
      //  body.text = title
        val yesBtn = dialog.findViewById(R.id.dialogButton) as Button
     //   val noBtn = dialog.findViewById(R.id.noBtn) as TextView
        yesBtn.setOnClickListener {
            dialog.dismiss()
        }
    //    noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }

    private fun showd(message : String){

    //    val builder = AlertDialog.Builder(this)

        val builder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.AlertDialogCustom))
        //set title for alert dialog
    //    builder.setTitle("Hello")
        //set message for alert dialog
        builder.setMessage(message)

        Log.i("sssssssssssssssssss",message)
     //   builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("Okay",null)



        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()



    }






    fun showMessageBox(text: String){

        //Inflate the dialog as custom view
        val messageBoxView = LayoutInflater.from(applicationContext).inflate(R.layout.dialog_layout, null)

        //AlertDialogBuilder
        val messageBoxBuilder = AlertDialog.Builder(applicationContext).setView(messageBoxView)

        //setting text values
    //  dialogText.setText(text)

        //show dialog
        val  messageBoxInstance = messageBoxBuilder.show()

        //set Listener
        messageBoxView.setOnClickListener(){
            //close dialog
            messageBoxInstance.dismiss()
        }
    }
}




