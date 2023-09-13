package com.example.clients.feign.card.entity

import org.springframework.data.domain.Persistable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(
    name = "card",
    uniqueConstraints = [UniqueConstraint(columnNames = ["partner_transaction_id", "status"])]
)
class CardEntity(
    @Id
    @Column(name = "transaction_id", updatable = false, nullable = false, length = 36)
    val transactionId: String,

    @Column(name = "transaction_data", updatable = false, nullable = false, length = 450)
    val transactionData: String,

    @Column(name = "partner_transaction_id", updatable = false, nullable = false, length = 20)
    val partnerTransactionId: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", updatable = false, nullable = false, length = 32)
    val status: CardStatus,

    @Column(name = "created_at", updatable = false, nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) : Persistable<String> {
    override fun getId(): String = transactionId

    override fun isNew(): Boolean = true
}
