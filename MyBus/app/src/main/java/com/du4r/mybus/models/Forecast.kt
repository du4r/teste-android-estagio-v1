package com.du4r.mybus.models

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("p") val stoppage: Stoppage,
    @SerializedName("l") val lines: List<Line>
)
