package com.du4r.mybus.models

import com.google.gson.annotations.SerializedName

data class Position(
    @SerializedName("hr")
    val hora: String, // Horário de referência da geração das informações

    @SerializedName("l")
    val lines: List<Line>,

    @SerializedName("vs")
    val vehicles: List<Vehicle>
)
