package com.example.persistence.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.time.OffsetDateTime
import java.util.Optional

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
class AuditingConfig {

    @Bean // Makes OffsetDateTime compatible with auditing fields
    fun auditingDateTimeProvider() = DateTimeProvider { Optional.of(OffsetDateTime.now()) }
}
