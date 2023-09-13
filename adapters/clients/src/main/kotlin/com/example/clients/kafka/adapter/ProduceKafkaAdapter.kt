package com.example.clients.kafka.adapter

import com.example.domain.transaction.port.out.ProduceKafkaPort
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class ProduceKafkaAdapter : ProduceKafkaPort {
    companion object {
        val log = KotlinLogging.logger {}
    }

    override fun produce() {
        log.info { "카프카 메세지 발행 !!!" }
    }
}
