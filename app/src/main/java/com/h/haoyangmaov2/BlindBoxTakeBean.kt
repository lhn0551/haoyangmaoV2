package com.h.haoyangmaov2

data class BlindBoxTakeBean(
    val blind_box_amount_max: String,
    val blind_box_amount_min: String,
    val bonus_amount: String,
    val contract_name: String,
    val receiveStatus: Int,
    var walletAddress:String
)