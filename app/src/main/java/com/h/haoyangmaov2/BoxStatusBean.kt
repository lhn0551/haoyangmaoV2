package com.h.haoyangmaov2

data class BoxStatusBean(
    val activity_info: List<ActivityInfo>,
    val begin_time: Int,
    val box: Int,
    val classification_id: Int,
    val cn_rule: String,
    val code: Int,
    val create_time: Int,
    val empty_num: Int,
    val en_rule: String,
    val end_time: Int,
    val get_bonus_amount: String,
    val id: Int,
    val msg: String,
    val msg_title: String,
    val page: String,
    val my_box_level: Int,
    val delivery_status: Int,
    val user_in_level: Int,
    val user_level: Int,
    val name: String,
    val provide_num: Int,
    val status: Int,
    val surplus_num: Int,
    val total: Double,
    val contract_name: String,
    val update_time: Int
)

data class ActivityInfo(
    val max: Int,
    val min: Int,
    val name: String,
    val type: Int
)