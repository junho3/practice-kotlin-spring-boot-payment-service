package com.example.domain.transaction.service

import com.example.common.enums.TransactionType
import com.example.common.exceptions.BusinessException
import com.example.common.exceptions.ErrorCode

object CancelServiceHelper {
    fun getTransactionType(
        paymentAmount: Long,
        vatAmount: Long,
        canceledPaymentAmount: Long,
        canceledVatAmount: Long,
        cancelPaymentAmount: Long,
        cancelVatAmount: Long
    ): TransactionType {
        return when {
            paymentAmount.minus(canceledPaymentAmount).minus(cancelPaymentAmount) == 0L &&
                vatAmount.minus(canceledVatAmount).minus(cancelVatAmount) == 0L -> TransactionType.CANCEL
            paymentAmount.minus(canceledPaymentAmount).minus(cancelPaymentAmount) > 0L &&
                vatAmount.minus(canceledVatAmount).minus(cancelVatAmount) > 0L -> TransactionType.PARTIAL_CANCEL
            else -> throw BusinessException(ErrorCode.INVALID_CANCEL_AMOUNT)
        }
    }
}
