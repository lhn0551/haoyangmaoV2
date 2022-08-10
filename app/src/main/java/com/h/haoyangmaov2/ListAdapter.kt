package com.h.haoyangmaov2

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * 首页消息适配器
 */
class ListAdapter(layoutResId: Int) :
    BaseQuickAdapter<ConnectWalletBean, BaseViewHolder>(layoutResId) {

    override fun convert(holder: BaseViewHolder, item: ConnectWalletBean) {
        holder.setText(R.id.tvAddress, item.walletAddress)
            .setText(R.id.tvLevel,"Level:"+item.level+"\tExp:"+item.now_exp+"/"+item.need_exp+"\t"+item.giftStatus)

    }
}