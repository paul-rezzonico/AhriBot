package integration.config

import net.dv8tion.jda.api.JDA
import org.mockito.Mockito
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

@TestConfiguration
open class IntegrationTestConfiguration {
    @Bean
    @Primary
    open fun jda(): JDA = Mockito.mock(JDA::class.java)
}