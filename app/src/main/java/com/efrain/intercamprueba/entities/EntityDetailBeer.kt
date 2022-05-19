package com.efrain.intercamprueba.entities

import com.google.gson.annotations.SerializedName

data class EntityDetailBeer(
    val id: Int,
    val name: String,

    @SerializedName("tagline")
    val tagLine: String,

    @SerializedName("first_brewed")
    val firstBrewed: String,

    val description: String,

    @SerializedName("image_url")
    val imageUrl: String,

    val abv: Double,
    val ibu: Double,

    @SerializedName("target_fg")
    val targetFg: String,

    @SerializedName("target_og")
    val targetOg: String,

    val volume: EntityVolume,

    @SerializedName("brewers_tips")
    val brewersTips: String,

    @SerializedName("contributed_by")
    val contributedBy: String,

    @SerializedName("food_pairing")
    val foodPairing: List<String>,

    val ingredients: EntityIngredients,
)

data class EntityVolume(
    val value: Double,
    val unit: String
)

data class EntityIngredients(
    val malt: List<EntityMalt>,
    val hops: List<EntityMalt>,
    val yeast: String,
    )

data class EntityMalt(
    val name: String,
    val amount: EntityVolume,
)
