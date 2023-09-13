package com.example.persistence.entity.transaction

import com.example.persistence.config.AuditingEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.MapsId
import javax.persistence.OneToOne
import javax.persistence.PrimaryKeyJoinColumn
import javax.persistence.Table

@Entity
@Table(name = "payment_card")
class PaymentCardEntity(
    @Id
    @Column(name = "payment_id")
    val id: String = "",

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "payment_id", referencedColumnName = "payment_id")
    var payment: PaymentEntity? = null,

    @Column(name = "card_transaction_id", unique = true, updatable = false, nullable = false, length = 36)
    val cardTransactionId: String,

    @Column(name = "card_transaction_data", updatable = false, nullable = false, length = 450)
    val cardTransactionData: String,

    @Column(name = "encrypt_card_data", updatable = false, nullable = false, length = 300)
    val encryptCardData: String,

    @Column(name = "card_installment_month", updatable = false, nullable = false)
    val cardInstallmentMonth: Int
) : AuditingEntity()
