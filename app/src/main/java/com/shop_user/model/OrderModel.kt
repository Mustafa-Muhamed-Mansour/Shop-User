package com.shop_user.model

class OrderModel
{
    lateinit var id: String
    lateinit var randomKey: String
    lateinit var name: String
    lateinit var phone: String
    lateinit var imageProduct: String
    lateinit var titleProduct: String
    lateinit var priceProduct: String
    lateinit var quantityOfProduct: String


    constructor()
    {
    }

    constructor(id: String, randomKey: String, name: String, phone: String, imageProduct: String, titleProduct: String, priceProduct: String, quantityOfProduct: String)
    {
        this.id = id
        this.randomKey = randomKey
        this.name = name
        this.phone = phone
        this.imageProduct = imageProduct
        this.titleProduct = titleProduct
        this.priceProduct = priceProduct
        this.quantityOfProduct = quantityOfProduct
    }


}