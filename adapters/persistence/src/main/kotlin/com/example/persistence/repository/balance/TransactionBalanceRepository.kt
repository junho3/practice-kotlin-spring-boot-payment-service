package com.example.persistence.repository.balance

import com.example.persistence.entity.balance.TransactionBalanceEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionBalanceRepository : JpaRepository<TransactionBalanceEntity, String>
