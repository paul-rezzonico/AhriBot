package com.paulrezzonico.command.admin

object Constants {
    const val MIN_MUTE_DURATION = 0.1 // 10 seconds
    const val MAX_MUTE_DURATION = 40320.0 // 28 days in minutes
    const val MIN_BAN_DURATION = 0.0 // No minimum ban duration
    const val MAX_BAN_DURATION = 525600.0 // 1 year in minutes
    const val MAX_REASON_LENGTH = 512.0 // Maximum length for reason
    const val MAX_DELATION_DAYS = 7.0 // Maximum days of messages to delete when banning
}
