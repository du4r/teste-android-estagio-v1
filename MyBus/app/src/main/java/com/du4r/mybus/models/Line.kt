package com.du4r.mybus.models

import com.google.gson.annotations.SerializedName

data class Line(
    //@SerializedName("c") val c: String?,
    @SerializedName("cl") val cl: Int?, // Código identificador da linha. Este é um código identificador único de cada linha do sistema (por sentido de operação)
    //@SerializedName("lc") val lc: Boolean?, // Indica se uma linha opera no modo circular (sem um terminal secundário)
    //@SerializedName("lt") val lt: String?, // Informa a primeira parte do letreiro numérico da linha
    //@SerializedName("tl") val tl: Int?, // Informa a segunda parte do letreiro numérico da linha, que indica se a linha opera nos modos:
    //BASE (10), ATENDIMENTO (21, 23, 32, 41)
    //@SerializedName("sl") val sl: Int?, //  Informa o sentido ao qual a linha atende, onde 1 significa Terminal Principal para Terminal Secundário e 2 para Terminal Secundário para Terminal Principal
    //@SerializedName("lt0") val lt0: String?,
    //@SerializedName("lt1") val lt1: String?,
    //@SerializedName("qv") val qv: Int?,
    @SerializedName("vs") val vs: List<Vehicle?>?,
    @SerializedName("tp") val tp: String?, //  Informa o letreiro descritivo da linha no sentido Terminal Principal para Terminal Secundário
    //@SerializedName("ts") val ts: String? // Informa o letreiro descritivo da linha no sentido Terminal Secundário para Terminal Principal
)
