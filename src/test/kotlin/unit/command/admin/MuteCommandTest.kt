package unit.command.admin

import com.paulrezzonico.command.admin.MuteCommand
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionMapping
import net.dv8tion.jda.api.requests.restaction.PermissionOverrideAction
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class MuteCommandTest {

    private lateinit var muteCommand: MuteCommand
    private lateinit var event: SlashCommandInteractionEvent
    private lateinit var member: Member
    private lateinit var guild: Guild
    private lateinit var channel: TextChannel
    private lateinit var jda: JDA
    private lateinit var rateLimitPool: ScheduledExecutorService

    @BeforeEach
    fun setUp() {
        muteCommand = MuteCommand()

        // Mock all necessary parts
        event = Mockito.mock(SlashCommandInteractionEvent::class.java)
        member = Mockito.mock(Member::class.java)
        guild = Mockito.mock(Guild::class.java)
        channel = Mockito.mock(TextChannel::class.java)
        jda = Mockito.mock(JDA::class.java)
        rateLimitPool = Mockito.mock(ScheduledExecutorService::class.java)

        // Configure mocks
        `when`(event.name).thenReturn("mute")
        `when`(event.guild).thenReturn(guild)
        `when`(event.jda).thenReturn(jda)
        `when`(jda.rateLimitPool).thenReturn(rateLimitPool)

        val userOption = Mockito.mock(OptionMapping::class.java)
        val durationOption = Mockito.mock(OptionMapping::class.java)

        `when`(event.getOption("user")).thenReturn(userOption)
        `when`(event.getOption("duration")).thenReturn(durationOption)
        `when`(userOption.asMember).thenReturn(member)
        `when`(durationOption.asString).thenReturn("5")

        `when`(guild.channels).thenReturn(listOf(channel))

        val permOverrideAction = Mockito.mock(PermissionOverrideAction::class.java)
        `when`(channel.upsertPermissionOverride(ArgumentMatchers.any())).thenReturn(permOverrideAction)
        `when`(permOverrideAction.deny(ArgumentMatchers.any<Permission>())).thenReturn(permOverrideAction)
        `when`(permOverrideAction.clear(ArgumentMatchers.any<Permission>())).thenReturn(permOverrideAction)

        val replyAction = Mockito.mock(ReplyCallbackAction::class.java)
        `when`(event.reply(ArgumentMatchers.any<String>())).thenReturn(replyAction)
    }

    @Test
    fun `test successful mute command`() {
        val commandMember = Mockito.mock(Member::class.java)
        `when`(event.member).thenReturn(commandMember)
        `when`(commandMember.hasPermission(Permission.MANAGE_PERMISSIONS)).thenReturn(true)

        muteCommand.onSlashCommandInteraction(event)

        Mockito.verify(channel).upsertPermissionOverride(member)
        Mockito.verify(event).reply(ArgumentMatchers.contains("Muted"))
        Mockito.verify(rateLimitPool)
            .schedule(ArgumentMatchers.any(), ArgumentMatchers.eq(5L), ArgumentMatchers.eq(TimeUnit.MINUTES))
    }

    @Test
    fun `test mute command without permissions`() {
        val commandMember = Mockito.mock(Member::class.java)
        `when`(event.member).thenReturn(commandMember)
        `when`(commandMember.hasPermission(Permission.MANAGE_PERMISSIONS)).thenReturn(false)

        // Exécute la commande
        muteCommand.onSlashCommandInteraction(event)

        Mockito.verify(event).reply("You don't have the permission to use this command.")
        Mockito.verify(channel, Mockito.never()).upsertPermissionOverride(ArgumentMatchers.any())
    }

    @Test
    fun `test mute command with invalid duration`() {
        val commandMember = Mockito.mock(Member::class.java)
        `when`(event.member).thenReturn(commandMember)
        `when`(commandMember.hasPermission(Permission.MANAGE_PERMISSIONS)).thenReturn(true)

        val durationOption = Mockito.mock(OptionMapping::class.java)
        `when`(event.getOption("duration")).thenReturn(durationOption)
        `when`(durationOption.asString).thenReturn("invalid")

        muteCommand.onSlashCommandInteraction(event)

        Mockito.verify(event).reply("User not found or invalid duration.")
        Mockito.verify(channel, Mockito.never()).upsertPermissionOverride(ArgumentMatchers.any())
    }
}
