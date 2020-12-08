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


class LoginActivity : AppCompatActivity() {
    var ed_email: EditText? = null
    var ed_password: EditText? = null
    var str_email: String? = null
    var str_password: String? = null
    var url = "https://groceryapptarucproject.000webhostapp.com/grocery/login.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ed_email = findViewById(R.id.ed_email)
        ed_password = findViewById(R.id.ed_password)
    }

    fun Login(view: View?) {
        if (ed_email!!.text.toString() == "") {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
        } else if (ed_password!!.text.toString() == "") {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
        } else {
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Please Wait..")
            progressDialog.show()
            str_email = ed_email!!.text.toString().trim { it <= ' ' }
            str_password = ed_password!!.text.toString().trim { it <= ' ' }
            val request: StringRequest = object : StringRequest(Method.POST, url, Response.Listener { response ->
                progressDialog.dismiss()
                if (response.equals("logged in successfully", ignoreCase = true)) {
                    ed_email!!.setText("")
                    ed_password!!.setText("")
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(this@LoginActivity, response, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@LoginActivity, response, Toast.LENGTH_SHORT).show()
                }
            }, Response.ErrorListener { error ->
                progressDialog.dismiss()
                Toast.makeText(this@LoginActivity, error.message.toString(), Toast.LENGTH_SHORT).show()
            }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {


                    val params: MutableMap<String, String> = HashMap()
                    params["email"] = str_email!!
                    params["password"] = str_password!!
                    return params
                }
            }
            val requestQueue = Volley.newRequestQueue(this@LoginActivity)
            requestQueue.add(request)
        }
    }

    fun moveToRegistration(view: View?) {
        startActivity(Intent(applicationContext, RegistrationActivity::class.java))
        finish()
    }
}
