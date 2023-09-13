package com.example.api.controller.v1.cancel.response

import com.example.domain.transaction.port.result.CancelResult

data class CancelResponse(
    val transactionId: String,
    val paymentId: String,
    val cardTransactionData: String
) {
    companion object {
        fun of(cancelResult: CancelResult): CancelResponse = CancelResponse(
            transactionId = cancelResult.transactionId.value,
            paymentId = cancelResult.paymentId.value,
            cardTransactionData = cancelResult.cardTransactionData.value
        )
    }
}
