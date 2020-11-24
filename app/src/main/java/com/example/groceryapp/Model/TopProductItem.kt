package com.example.groceryapp.Model


import com.google.gson.annotations.SerializedName

data class TopProductItem(
    @SerializedName("ID")
    val id: String,
    @SerializedName("Link")
    val link: String,
    @SerializedName("Name")
    val name: String
)