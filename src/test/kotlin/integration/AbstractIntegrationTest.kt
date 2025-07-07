package integration

import com.paulrezzonico.Main
import net.dv8tion.jda.api.JDA
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(
    classes = [Main::class],
    properties = [
        "discord.bot.token=dummy",
        "mongock.change-logs-scan-package=com.paulrezzonico.changelogs"
    ]
)
@Testcontainers
abstract class AbstractIntegrationTest {

    @MockBean
    lateinit var jda: JDA

    companion object {
        @Container
        val mongoDBContainer: MongoDBContainer = MongoDBContainer("mongo:latest")
            .withExposedPorts(27017)
            .withReuse(true)

        @JvmStatic
        @DynamicPropertySource
        fun setProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri") { mongoDBContainer.replicaSetUrl }
        }
    }
}
