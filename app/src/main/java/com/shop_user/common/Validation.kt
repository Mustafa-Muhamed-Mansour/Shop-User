package com.shop_user.common

import android.content.Context
import android.text.TextUtils
import android.widget.Toast

class Validation
{

    fun checkImage(context: Context, image: String): String
    {
        if (image == null)
        {
            Toast.makeText(context, "Please enter your image", Toast.LENGTH_SHORT).show()
        }

        return image
    }

    fun checkEmail(context: Context, email: String): String
    {
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
        }

        return email
    }

    fun checkName(context: Context, name: String): String
    {
        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show()
        }

        return name
    }

    fun checkPassword(context: Context, password: String): String
    {
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show()
        }

        return password
    }

    fun checkPhone(context: Context, phone: String): String
    {
        if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(context, "Please enter your phone", Toast.LENGTH_SHORT).show()
        }

        return phone
    }

    fun checkNumberOfQuantity(context: Context, quantity: String): String
    {

        if (quantity.toInt() == 0)
        {
            Toast.makeText(context, "Please enter of quantity", Toast.LENGTH_SHORT).show()
        }

        return quantity
    }
}