
package com.paulrezzonico.command.admin

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

object AdminPermissionUtils {
    /**
     * Vérifie si l'utilisateur a les permissions administratives nécessaires
     * @param event L'événement de commande slash
     * @return true si l'utilisateur n'a PAS les permissions requises, false sinon
     */
    fun isNotAdmin(event: SlashCommandInteractionEvent): Boolean {
        if (!event.member!!.hasPermission(Permission.MANAGE_PERMISSIONS)) {
            event.reply("Vous n'avez pas la permission d'utiliser cette commande.").queue()
            return true
        }
        return false
    }

    /**
     * Vérifie si l'utilisateur a les permissions administratives nécessaires
     * @param event L'événement de commande slash
     * @return true si l'utilisateur a les permissions requises, false sinon
     */
    fun isAdmin(event: SlashCommandInteractionEvent): Boolean {
        return !isNotAdmin(event)
    }
}