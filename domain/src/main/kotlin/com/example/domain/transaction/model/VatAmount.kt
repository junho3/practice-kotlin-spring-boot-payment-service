package com.example.domain.transaction.model

import com.example.common.consts.ValidationConstant.DIV_VAT
import com.example.common.exceptions.BusinessException
import com.example.common.exceptions.ErrorCode
import kotlin.math.roundToLong

@JvmInline
value class VatAmount(
    val value: Long
) {
    companion object {
        fun of(paymentAmount: Long): VatAmount = VatAmount(
            value = paymentAmount
                .toDouble()
                .div(DIV_VAT)
                .roundToLong()
        )
    }

    fun validate(paymentAmount: Long): VatAmount {
        when {
            value > paymentAmount -> throw BusinessException(ErrorCode.INVALID_VAT_AMOUNT)
        }

        return this
    }
}
