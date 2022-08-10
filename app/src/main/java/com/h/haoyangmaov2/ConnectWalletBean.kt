package com.h.haoyangmaov2

data class ConnectWalletBean(
    val exp: Int,
    val grade_id: Int,
    val id: Int,
    val invite_code: String,
    val mem_address: String,
    var walletAddress:String,
    var level: Int,
    var now_exp: String,
    var need_exp: String,
    val token: String,
    var giftStatus:String="0"
)

