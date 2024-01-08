package com.paulrezzonico.changelogs

import com.github.cloudyrock.mongock.ChangeLog
import com.github.cloudyrock.mongock.ChangeSet

@ChangeLog(order = "001")
class StatusChangelog {

    @ChangeSet(order = "001", id = "createStatusCollection", author = "Paul REZZONICO")
    fun createStatusCollection() {
        //TODO
    }
}