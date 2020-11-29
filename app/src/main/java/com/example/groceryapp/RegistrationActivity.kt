package com.example.groceryapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.util.*


class RegistrationActivity : AppCompatActivity() {
    var ed_username: EditText? = null
    var ed_email: EditText? = null
    var ed_password: EditText? = null
    var str_name: String? = null
    var str_email: String? = null
    var str_password: String? = null
    var url = "https://homies4life.000webhostapp.com/register.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        ed_email = findViewById(R.id.ed_email)
        ed_username = findViewById(R.id.ed_username)
        ed_password = findViewById(R.id.ed_password)
    }

    fun moveToLogin(view: View?) {
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        finish()
    }

    fun Register(view: View?) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait..")
        if (ed_username!!.text.toString() == "") {
            Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show()
        } else if (ed_email!!.text.toString() == "") {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
        } else if (ed_password!!.text.toString() == "") {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
        } else {
            progressDialog.show()
            str_name = ed_username!!.text.toString().trim { it <= ' ' }
            str_email = ed_email!!.text.toString().trim { it <= ' ' }
            str_password = ed_password!!.text.toString().trim { it <= ' ' }
            val request: StringRequest = object : StringRequest(
                Method.POST, url,
                Response.Listener { response ->
                    progressDialog.dismiss()
                    ed_username!!.setText("")
                    ed_email!!.setText("")
                    ed_password!!.setText("")
                    Toast.makeText(this@RegistrationActivity, response, Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener { error ->
                    progressDialog.dismiss()
                    Toast.makeText(
                        this@RegistrationActivity,
                        error.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["name"] = str_name!!
                    params["email"] = str_email!!
                    params["password"] = str_password!!
                    return params
                }
            }
            val requestQueue = Volley.newRequestQueue(this@RegistrationActivity)
            requestQueue.add(request)
        }
    }
}