package com.example.api.controller.v1.payment.response

import com.example.domain.transaction.port.result.PaymentResult

data class PaymentResponse(
    val transactionId: String,
    val paymentId: String,
    val cardTransactionData: String
) {
    companion object {
        fun of(paymentResult: PaymentResult): PaymentResponse = PaymentResponse(
            transactionId = paymentResult.transactionId.value,
            paymentId = paymentResult.paymentId.value,
            cardTransactionData = paymentResult.cardTransactionData.value
        )
    }
}
