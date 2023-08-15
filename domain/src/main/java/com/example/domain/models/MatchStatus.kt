package com.example.domain.models

enum class MatchStatus {
    RUNNING,
    NOT_STARTED,
    FINISHED,
    UNKNOWN;

    companion object {
        fun get(value: String) = values().firstOrNull {
            it.name.contains(value, ignoreCase = true)
        } ?: UNKNOWN
    }
}
