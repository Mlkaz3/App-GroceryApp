package com.example.groceryapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.Adapter.MySingleton
import org.json.JSONArray
import org.json.JSONObject
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


                    //read the user user_id and cart_id from the server
                    val url = "https://groceryapptarucproject.000webhostapp.com/grocery/getdata.php?email=" + str_email + "&password=" + str_password
                    val jsonObjectRequest = JsonObjectRequest(
                        Request.Method.GET, url, null,
                        Response.Listener { response ->
                            var userID = ""
                            var cartID = ""

                            Log.e("response",response.toString())
                            // Process the JSON
                            try{
                                if(response != null){
                                    val strResponse = response.toString()
                                    val jsonResponse  = JSONObject(strResponse)
                                    val jsonArray: JSONArray = jsonResponse.getJSONArray("userinfo")
                                    val size: Int = jsonArray.length()
                                    for(i in 0.until(size)){
                                        var jsonCartItem: JSONObject = jsonArray.getJSONObject(i)

                                        userID = jsonCartItem.getString("user_id")
                                        cartID = jsonCartItem.getString("cart_id")
                                        Log.e("get userID",userID)
                                        Log.e("get cartID",cartID)


                                        //pass the user cart_id to next activity
                                        var user_id:Int = userID.toInt()
                                        var cart_id:Int = cartID.toInt()
                                        val intent = Intent(baseContext, MainActivity::class.java)
                                        intent.putExtra("cart_id", cart_id.toString())
                                        intent.putExtra("user_id",user_id.toString())
                                        startActivity(intent)

                                    }
                                }
                            }catch (e:Exception){
                                Log.d("Main", "Response: %s".format(e.message.toString()))
                            }
                        },
                        Response.ErrorListener { error ->
                            Log.d("Main", "Response: %s".format(error.message.toString()))
                        }
                    )

                    //Volley request policy, only one time request
                    jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        0, //no retry
                        1f
                    )
                    // Access the RequestQueue through your singleton class.
                    MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


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