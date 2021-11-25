package com.leovp.leofire.framework.utils

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