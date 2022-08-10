package com.h.haoyangmaov2

/**
 * socket请求base
 */
class SocketRequestBase<T> {
    var controller: String? = null
        private set
    var action: String? = null
        private set
    var params: T? = null
        private set

    var totp: String? = null
        private set

    var idfa: Int? = null
        private set

    fun setParams(params: T) {
        this.params = params
    }

    fun setAction(action: String) {
        this.action = action
    }

    fun setController(controller: String) {
        this.controller = controller
    }

    fun setTotp(totp: String) {
        this.totp = totp
    }

    fun setIdfa(idfa: Int) {
        this.idfa = idfa
    }
}