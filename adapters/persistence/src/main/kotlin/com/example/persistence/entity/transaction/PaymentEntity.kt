package com.example.persistence.entity.transaction

import com.example.persistence.config.AuditingEntity
import com.example.common.enums.PaymentStatus
import com.example.common.enums.PaymentType
import org.springframework.data.domain.Persistable
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.PrimaryKeyJoinColumn
import javax.persistence.Table

@Entity
@Table(name = "payment")
class PaymentEntity(
    @Id
    @Column(name = "payment_id", length = 20)
    val paymentId: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    var transaction: TransactionEntity? = null,

    @Column(name = "payment_amount", updatable = false, nullable = false)
    val paymentAmount: Long = 0,

    @Column(name = "vat_amount", updatable = false, nullable = false)
    val vatAmount: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", updatable = false, nullable = false, length = 16)
    val paymentType: PaymentType,

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", updatable = false, nullable = false, length = 16)
    val paymentStatus: PaymentStatus,

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "payment", cascade = [CascadeType.ALL])
    @PrimaryKeyJoinColumn
    var paymentCard: PaymentCardEntity? = null
) : AuditingEntity(), Persistable<String> {
    internal fun addPaymentCard(cardEntity: PaymentCardEntity): PaymentEntity {
        this.paymentCard = cardEntity
        cardEntity.payment = this
        return this
    }

    override fun getId(): String = paymentId

    override fun isNew(): Boolean = true
}
