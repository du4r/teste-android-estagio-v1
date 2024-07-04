package com.du4r.mybus.models

import com.google.gson.annotations.SerializedName

data class Stoppage(
    @SerializedName("cp") val cp: Int, // Código identificador da parada
    @SerializedName("np") val np: String, // Nome da parada
    @SerializedName("ed") val ed: String, // Endereço de localização da parada
    @SerializedName("py") val py: Double, // Informação de latitude da localização da parada
    @SerializedName("px") val px: Double // Informação de longitude da localização da parada
)
