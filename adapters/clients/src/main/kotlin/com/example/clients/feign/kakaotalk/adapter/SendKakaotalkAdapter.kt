package com.example.clients.feign.kakaotalk.adapter

import com.example.domain.transaction.port.out.SendKakaotalkPort
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class SendKakaotalkAdapter : SendKakaotalkPort {
    companion object {
        val log = KotlinLogging.logger {}
    }

    override fun send() {
        log.info { "카카오톡 메세지 발행 !!!" }
    }
}
