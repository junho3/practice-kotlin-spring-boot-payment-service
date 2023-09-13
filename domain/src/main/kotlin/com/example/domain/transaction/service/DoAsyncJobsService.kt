package com.example.domain.transaction.service

import com.example.domain.transaction.port.`in`.DoAsyncJobsUseCase
import com.example.domain.transaction.port.out.ProduceKafkaPort
import com.example.domain.transaction.port.out.SendKakaotalkPort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class DoAsyncJobsService(
    private val sendKakaotalkPort: SendKakaotalkPort,
    private val produceKafkaPort: ProduceKafkaPort
) : DoAsyncJobsUseCase {
    companion object {
        val log = KotlinLogging.logger {}
    }

    override fun doAsyncJobs() = runBlocking {
        log.info { "코루틴 비동기 후처리 작업" }

        // 코루틴 에러를 전파하지 않기 위해 supervisorScope 선언
        supervisorScope {
            launch { sendKakaotalk() } // 응답 데이터가 없다는 가정으로 launch로 실행
            launch { produceKafka() } // 응답 데이터가 없다는 가정으로 launch로 실행
        }

        return@runBlocking
    }

    // 카카오톡 API 호출이라고 가정하고 Dispatchers.IO로 선언
    private suspend fun sendKakaotalk() = withContext(Dispatchers.IO) {
        sendKakaotalkPort.send()
    }

    // 카프카 호출이라고 가정하고 Dispatchers.IO로 선언
    private suspend fun produceKafka() = withContext(Dispatchers.IO) {
        produceKafkaPort.produce()
    }
}
