package com.paulrezzonico.command.admin;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class MuteCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_PERMISSIONS)) {
            event.reply("You don't have the permission to use this command.").queue();
            return;
        }

        if (event.getName().equals("mute")) {
            Member member = Objects.requireNonNull(event.getOption("user")).getAsMember();
            long duration = parseDuration(Objects.requireNonNull(event.getOption("duration")).getAsString());

            if (member != null && duration > 0) {
                Guild guild = event.getGuild();
                if (guild != null) {
                    for (GuildChannel channel : guild.getChannels()) {
                        if (channel instanceof TextChannel textChannel) {
                            handleMute(textChannel, member, duration, event);
                        }
                    }

                    event.reply("Muted " + member.getAsMention() + " for " + duration + " minutes.").queue();
                }
            } else {
                event.reply("User not found or invalid duration.").queue();
            }
        }
    }

    private void handleMute(TextChannel channel, Member member, long duration, SlashCommandInteractionEvent event) {
        channel.upsertPermissionOverride(member)
            .deny(Permission.MESSAGE_SEND)
            .queue();

        event.getJDA().getRateLimitPool().schedule(() -> channel.upsertPermissionOverride(member)
            .clear(Permission.MESSAGE_SEND)
            .queue(), duration, TimeUnit.MINUTES);
    }


    private long parseDuration(String durationStr) {
        try {
            return Long.parseLong(durationStr);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
