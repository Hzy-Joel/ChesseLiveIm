package com.hzy.chatim.utils

import com.hzy.chatim.base.TAG
import java.time.LocalDateTime

object Log {
    fun i(message: Any?, tag: String = TAG) {
        println("[${LocalDateTime.now()}]:   $tag :$message \n")
    }
}