package com.example.data.remote.models

enum class MatchStatusDto(val status: String) {
    RUNNING("running"),
    NOT_STARTED("not_started"),
    FINISHED("FINISHED"),
    CANCELED("canceled"),
    NO_STATUS("no_status"),
    UNKNOWN("unknown");

    companion object {
        fun get(value: String) = values().firstOrNull {
            it.status.contains(value)
        } ?: UNKNOWN
    }
}
