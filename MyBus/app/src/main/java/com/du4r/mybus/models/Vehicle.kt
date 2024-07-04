package com.du4r.mybus.models

import com.google.gson.annotations.SerializedName

data class Vehicle(

    @SerializedName("p")
    val p: Int?,

    @SerializedName("t")
    val t: String?,

    @SerializedName("a")
    val a: Boolean?, //  Indica se o veículo é (true) ou não (false) acessível para pessoas com deficiência

    @SerializedName("py")
    val py: Double?, // Informação de latitude da localização do veículo

    @SerializedName("px")
    val px: Double? //  Informação de longitude da localização do veículo
)
