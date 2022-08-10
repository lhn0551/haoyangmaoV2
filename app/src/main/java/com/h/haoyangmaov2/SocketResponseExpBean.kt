package com.h.haoyangmaov2

data class SocketResponseExpBean(
    val address: String,
    val controller: String,
    val exp_info: ExpInfo
)

data class ExpInfo(
    val all_exp: Int,
    val member_level: Int,
    val need_exp: String,
    val now_level_exp: String
)