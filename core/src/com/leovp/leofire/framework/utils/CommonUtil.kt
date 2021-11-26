package com.leovp.leofire.framework.utils

import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.ln
import kotlin.math.pow

/**
 * Author: Michael Leo
 * Date: 2021/11/22 10:42
 */
fun Long.humanReadableByteCount(si: Boolean = false, precision: Int = 2): String {
    if (this < 0) return "NA"
    val base = if (si) 1000 else 1024
    if (this < base) return "${this}B"
    val exp = (ln(this.toDouble()) / ln(base.toDouble())).toInt()
    val pre = (if (si) "kMGTPEZY" else "KMGTPEZY")[exp - 1].toString()
    return "%.${precision}f%s%s".format(this / base.toDouble().pow(exp.toDouble()), pre, if (si) "B" else "iB")
}

/**
 * If [length] or [srcPos] is not valid, the original string will be returned.
 */
fun String.truncate(length: Int, srcPos: Int = 0): String {
    if (srcPos > this.length - 1) return ""
    val endIdx = if (srcPos + length > this.length) this.length else srcPos + length
    return this.substring(srcPos, endIdx)
}

fun Double.round(precision: Int = 2, roundingMode: RoundingMode = RoundingMode.HALF_UP): Double {
    val df = DecimalFormat("#.${"#".repeat(precision)}")
    df.roundingMode = roundingMode
    return df.format(this).toDouble()
}

fun Float.round(precision: Int = 2, roundingMode: RoundingMode = RoundingMode.HALF_UP): Float {
    return this.toDouble().round(precision, roundingMode).toFloat()
}