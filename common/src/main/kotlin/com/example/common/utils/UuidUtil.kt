package com.example.common.utils

import java.nio.ByteBuffer
import java.util.UUID

object UuidUtil {
    fun generate(): String {
        return ByteBuffer
            .wrap(UUID.randomUUID().toString().toByteArray())
            .long
            .toString(9)
    }

    fun generateCardTransactionId(): String {
        return UUID.randomUUID().toString()
    }
}
