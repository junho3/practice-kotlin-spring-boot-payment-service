package com.example.persistence.entity.transaction

import com.example.persistence.config.AuditingEntity
import com.example.common.enums.TransactionFailCode
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "transaction_fail_log")
class TransactionFailLogEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Column(name = "transaction_id", updatable = false, nullable = false, length = 20)
    val transactionId: String,

    @Column(name = "payment_id", updatable = false, nullable = false, length = 20)
    val paymentId: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "code", updatable = false, nullable = false, length = 255)
    val code: TransactionFailCode,

    @Column(name = "reason", updatable = false, length = 4000)
    val reason: String? = null
) : AuditingEntity()
