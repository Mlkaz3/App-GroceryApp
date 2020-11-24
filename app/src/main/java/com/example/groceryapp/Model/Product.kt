package com.example.groceryapp.Model

class Product {
    private var ID: String? = null
    private var Name: String? = null
    private var Link: String? = null

    fun TopProduct(Name: String?, Link: String?) {
        this.Link = Link
        this.Name = Name
    }

    fun getID(): String? {
        return ID
    }

    fun setID(ID: String?) {
        this.ID = ID
    }

    fun getName(): String? {
        return Name
    }

    fun setName(name: String) {
        Name = name
    }

    fun getLink(): String? {
        return Link
    }

    fun setLink(link: String) {
        Link = link
    }
}