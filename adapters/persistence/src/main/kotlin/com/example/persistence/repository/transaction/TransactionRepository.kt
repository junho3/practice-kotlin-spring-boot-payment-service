package com.example.persistence.repository.transaction

import com.example.persistence.entity.transaction.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository : JpaRepository<TransactionEntity, String>
