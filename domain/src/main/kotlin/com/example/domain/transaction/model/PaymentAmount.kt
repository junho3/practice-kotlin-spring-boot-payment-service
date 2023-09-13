package com.example.domain.transaction.model

import com.example.common.consts.ValidationConstant.MAX_PAYMENT_AMOUNT
import com.example.common.consts.ValidationConstant.MIN_PAYMENT_AMOUNT
import com.example.common.exceptions.BusinessException
import com.example.common.exceptions.ErrorCode

@JvmInline
value class PaymentAmount(
    val value: Long
) {
    fun validate(): PaymentAmount {
        when {
            value < MIN_PAYMENT_AMOUNT -> throw BusinessException(ErrorCode.INVALID_MIN_PAYMENT_AMOUNT)
            value > MAX_PAYMENT_AMOUNT -> throw BusinessException(ErrorCode.INVALID_MAX_PAYMENT_AMOUNT)
        }

        return this
    }
}
