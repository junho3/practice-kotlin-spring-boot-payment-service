package com.example.persistence.entity.balance

import com.example.persistence.config.UpdatableAuditingEntity
import com.example.common.enums.PaymentType
import org.springframework.data.domain.Persistable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "payment_balance")
class PaymentBalanceEntity(
    @Id
    @Column(name = "payment_id", length = 20)
    val paymentId: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id", nullable = false, updatable = false)
    var transactionBalance: TransactionBalanceEntity? = null,

    @Column(name = "payment_amount", updatable = false, nullable = false)
    val paymentAmount: Long = 0,

    @Column(name = "vat_amount", updatable = false, nullable = false)
    val vatAmount: Long = 0,

    @Column(name = "payment_balance_amount", updatable = false, nullable = false)
    val paymentBalanceAmount: Long = 0,

    @Column(name = "vat_balance_amount", updatable = false, nullable = false)
    val vatBalanceAmount: Long = 0,

    @Column(name = "encrypt_card_data", updatable = false, nullable = false, length = 300)
    val encryptCardData: String,

    @Column(name = "card_installment_month", updatable = false, nullable = false)
    val cardInstallmentMonth: Int,

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", updatable = false, nullable = false)
    val paymentType: PaymentType
) : UpdatableAuditingEntity(), Persistable<String> {
    override fun getId(): String = paymentId

    override fun isNew(): Boolean = true
}
