package com.capgemini.firebasedemo.AppData.Menu

import com.google.firebase.Timestamp

val itemlist = mutableListOf<Item>()

 data class Item(
    val image: String="",
    val item: String="",
    val price: Int=0,
    val tag: String="",
    val description : String=""
)
{

}
data class Food(
        val name : String="",
        var quantity : Int =0,
        val totalprice : String="",
        val tag : String
)
{

}
data class Orders(
    val date : Timestamp= Timestamp.now(),
    val location : String="",
    var order : String = "",
    val total : Int=0
)