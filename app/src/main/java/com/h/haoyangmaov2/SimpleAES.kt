package com.h.haoyangmaov2

import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.Charset
import java.nio.charset.CharsetDecoder
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


private const val CIPHERMODE = "AES/CBC/ZeroBytePadding"
private const val newIvParameter = "0x821ac6df81b2c7"
private const val newKey: String = "0x7ab832ed934bca062c7d153fb250ce"

/**
 * AES 加密操作
 *
 * @param content 待加密内容
 * @param password 加密密码
 * @return
 */
fun simpleAesEncrypt(content: String): String? {
    try {
        val cipher = Cipher.getInstance(CIPHERMODE) // 创建密码器
        cipher.init(
            Cipher.ENCRYPT_MODE, getSecretKey(), IvParameterSpec(newIvParameter.toByteArray())
        ) // 初始化为加密模式的密码器
        val byte = cipher.doFinal(content.toByteArray())
        return toHexStr(byte)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return null
}

fun toHexStr(byteArray: ByteArray) =
    with(StringBuilder()) {
        byteArray.forEach {
            val hex = it.toInt() and (0xFF)
            val hexStr = Integer.toHexString(hex)
            if (hexStr.length == 1) append("0").append(hexStr)
            else append(hexStr)
        }
        toString()
    }

fun hexToByteArray(hex: String): ByteArray {
    var inHex = hex
    var hexlen = inHex.length
    val result: ByteArray
    if (hexlen % 2 == 1) {
        //奇数
        hexlen++
        result = ByteArray(hexlen / 2)
        inHex = "0$inHex"
    } else {
        //偶数
        result = ByteArray(hexlen / 2)
    }
    var j = 0
    var i = 0
    while (i < hexlen) {
        result[j] = hexToByte(inHex.substring(i, i + 2))
        j++
        i += 2
    }
    return result
}

fun hexToByte(inHex: String): Byte {
    return inHex.toInt(16).toByte()
}

fun checkHexString(string: String): Boolean {
    for (element in string) {
        val cInt: Int = element.code
        if ((cInt in 48..57)
            || (cInt in 65..70)
            || (cInt in 97..102)
        ) {

        } else {
            return false
        }
    }
    return true
}

/**
 * AES 解密操作
 *
 * @param content
 * @return
 */
fun simpleAesDecrypt(content: String): String? {
    try {
//     val newContent=   hexToString(content.toString())
//        Logs.e("$newContent")
        // 实例化
        val cipher = Cipher.getInstance(CIPHERMODE)
        cipher.init(
            Cipher.DECRYPT_MODE,
            getSecretKey(),
            IvParameterSpec(newIvParameter.toByteArray(StandardCharsets.UTF_8))
        ) // 初始化为加密模式的密码器
        val result = cipher.doFinal(hexToByteArray(content)) // 解密

        return String(result, StandardCharsets.UTF_8)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return null
}

/**
 * 生成加密秘钥
 *
 * @return
 */
private fun getSecretKey(): SecretKeySpec {
    return SecretKeySpec(newKey.toByteArray(), "AES")
}


fun byteBufferToString(buffer: ByteBuffer): String? {
    var charBuffer: CharBuffer? = null
    return try {
        val charset = Charset.forName("UTF-8")
        val decoder: CharsetDecoder = charset.newDecoder()
        charBuffer = decoder.decode(buffer)
        buffer.flip()
        charBuffer.toString()
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
        null
    }
}

