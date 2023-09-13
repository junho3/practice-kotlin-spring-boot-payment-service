package com.example.persistence.entity.transaction

import com.example.domain.transaction.model.TransactionFailLog

fun TransactionFailLogEntity.toDomain(): TransactionFailLog = TransactionFailLog(
    id = this.id,
    transactionId = this.transactionId,
    paymentId = this.paymentId,
    code = this.code,
    reason = this.reason,
    createdAt = this.createdAt
)

fun TransactionFailLog.toEntity(): TransactionFailLogEntity = TransactionFailLogEntity(
    transactionId = this.transactionId,
    paymentId = this.paymentId,
    code = this.code,
    reason = this.reason
)
