package com.anji.babydiary.database.address

import com.squareup.moshi.Json

data class AddressProperties(
    val id: String,
    @Json(name="img_src")
    val roadFullAddr: String,
    val roadAddrPart1: String,
    val roadAddrPart2: String,
    val jibunAddr: String,
    val engAddr: String,
    val zipNo: String,
    val admCd: String,
    val addrDetail: String,
    val rnMgtSn: String,
    val bdMgtSn: String,
    val detBdNmList: String,
    val bdNm: String,
    val bdKdcd: String,
    val siNm: String,
    val sggNm: String,
    val emdNm: String,
    val liNm: String,
    val rn: String,
    val udrtYn: String,
    val buldMnnm: Long,
    val buldSlno: Long,
    val mtYn: String,
    val lnbrMnnm: Long,
    val lnbrSlno: Long,
    val emdNo: String
)