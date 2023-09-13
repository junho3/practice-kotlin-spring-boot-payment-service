package com.example.persistence.repository.transaction

import com.example.persistence.entity.transaction.TransactionFailLogEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionFailLogRepository : JpaRepository<TransactionFailLogEntity, Long>
