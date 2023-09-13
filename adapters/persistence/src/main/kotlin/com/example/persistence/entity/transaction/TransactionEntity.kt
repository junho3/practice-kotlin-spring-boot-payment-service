package com.example.persistence.entity.transaction

import com.example.persistence.config.AuditingEntity
import org.springframework.data.domain.Persistable
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "transaction")
class TransactionEntity(
    @Id
    @Column(name = "transaction_id", length = 20)
    val transactionId: String,

    @OneToMany(mappedBy = "transaction", cascade = [CascadeType.PERSIST])
    var paymentEntities: MutableList<PaymentEntity> = mutableListOf()
) : AuditingEntity(), Persistable<String> {
    internal fun addPayment(paymentEntity: PaymentEntity): TransactionEntity {
        paymentEntity.transaction = this
        this.paymentEntities.add(paymentEntity)
        return this
    }

    override fun getId(): String = transactionId

    override fun isNew(): Boolean = true
}
