package com.example.groceryapp.Model

class Product {
    private var ID: String? = null
    private var Name: String? = null
    private var Link: String? = null

    constructor(Name: String?, Link: String?) {
        this.Name = Name
        this.Link = Link
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