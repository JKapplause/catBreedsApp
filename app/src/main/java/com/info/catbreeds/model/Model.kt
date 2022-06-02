package com.info.catbreeds.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Cat(
    @ColumnInfo(name="image")
    @SerializedName("image")
    val catImage : Image,
    @ColumnInfo(name="name")
    @SerializedName("name")
    val catName : String?,
    @ColumnInfo(name="origin")
    @SerializedName("origin")
    val catOrigin : String?,
    @ColumnInfo(name="id")
    @SerializedName("id")
    val catId : String?,
    @ColumnInfo(name="temperament")
    @SerializedName("temperament")
    val catTemperament : String?,
    @ColumnInfo(name="life_span")
    @SerializedName("life_span")
    val catLife_span : String?,
    @ColumnInfo(name="wikipedia_url")
    @SerializedName("wikipedia_url")
    val catWikipedia_url : String?,
    @ColumnInfo(name="dog_friendly")
    @SerializedName("dog_friendly")
    val catDog_friendly : Int,
    @ColumnInfo(name="weight_imperial")
    @SerializedName("weight_imperial")
    val catWeight_imperial : String?
    ) {
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}


@Entity
data class Image (
    @SerializedName("id")
    val id : String,
    @SerializedName("url")
    val url : String
)