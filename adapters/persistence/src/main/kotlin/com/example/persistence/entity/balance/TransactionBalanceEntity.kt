package com.example.persistence.entity.balance

import com.example.persistence.config.UpdatableAuditingEntity
import com.example.common.enums.TransactionType
import org.springframework.data.domain.Persistable
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "transaction_balance")
class TransactionBalanceEntity(
    @Id
    @Column(name = "transaction_id", length = 20)
    val transactionId: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false, length = 32)
    var transactionType: TransactionType,

    @Column(name = "payment_transaction_amount", updatable = false, nullable = false)
    val paymentTransactionAmount: Long = 0,

    @Column(name = "cancel_transaction_amount", nullable = false)
    var cancelTransactionAmount: Long = 0,

    @Column(name = "payment_vat_amount", updatable = false, nullable = false)
    var paymentVatAmount: Long = 0,

    @Column(name = "cancel_vat_amount", nullable = false)
    var cancelVatAmount: Long = 0,

    @OneToMany(mappedBy = "transactionBalance", cascade = [CascadeType.PERSIST])
    var paymentBalancesEntities: MutableList<PaymentBalanceEntity> = mutableListOf()
) : UpdatableAuditingEntity(), Persistable<String> {
    override fun getId(): String = transactionId

    override fun isNew(): Boolean = true

    internal fun addPaymentBalance(paymentBalanceEntity: PaymentBalanceEntity): TransactionBalanceEntity {
        paymentBalanceEntity.transactionBalance = this
        paymentBalancesEntities.add(paymentBalanceEntity)
        return this
    }

    internal fun updateTransactionType(transactionType: TransactionType): TransactionBalanceEntity {
        this.transactionType = transactionType
        return this
    }

    internal fun updateCancelTransactionAmount(cancelTransactionAmount: Long): TransactionBalanceEntity {
        this.cancelTransactionAmount = cancelTransactionAmount
        return this
    }

    internal fun updateCancelVatAmount(cancelVatAmount: Long): TransactionBalanceEntity {
        this.cancelVatAmount = cancelVatAmount
        return this
    }
}
