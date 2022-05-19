package com.efrain.intercamprueba.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.efrain.intercamprueba.util.Contants.Companion.TABLE_BEERS
import com.google.gson.annotations.SerializedName

@Entity(tableName = TABLE_BEERS)
data class EntityBeers(
    @PrimaryKey
    val id: Int,
    val name: String,

    @SerializedName("image_url")
    val imageUrl: String,

    @SerializedName("contributed_by")
    val contributedBy: String,

)
