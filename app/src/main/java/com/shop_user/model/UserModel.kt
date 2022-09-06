package com.shop_user.model

class UserModel
{
    lateinit var id: String
    lateinit var randomKey: String
    lateinit var image: String
    lateinit var email: String
    lateinit var name: String
    lateinit var phone: String


    constructor()
    {
    }

    constructor(id: String, randomKey: String, image: String, email: String, name: String, phone: String)
    {
        this.id = id
        this.randomKey = randomKey
        this.image = image
        this.email = email
        this.name = name
        this.phone = phone
    }


}
