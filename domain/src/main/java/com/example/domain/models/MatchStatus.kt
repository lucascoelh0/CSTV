package com.example.domain.models

enum class MatchStatus(val value: String) {
    RUNNING("running"),
    NOT_STARTED("not_started"),
    FINISHED("FINISHED"),
    UNKNOWN("unknown");

    companion object {
        fun get(value: String) = values().firstOrNull {
            it.value == value
        } ?: UNKNOWN
    }
}
