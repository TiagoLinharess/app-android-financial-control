package com.example.financial_control.domain.error

import com.example.financial_control.R

enum class FCError {
    USER_PERMISSION,
    USER_CANCELED_LOGIN,
    GENERIC,
    SESSION_EXPIRED,
    PARSE_ERROR,
    CUSTOM;

    fun getMessageId(default: Int = R.string.error_generic): Int {
        return when (this) {
            USER_PERMISSION -> R.string.error_user_permission
            USER_CANCELED_LOGIN -> R.string.error_user_canceled_login
            GENERIC -> R.string.error_generic
            SESSION_EXPIRED -> R.string.error_session_expired
            PARSE_ERROR -> R.string.error_parse_error
            CUSTOM -> default
        }
    }

    companion object {
        fun fromApiString(apiErrorString: String?): FCError {
            return when (apiErrorString?.lowercase()) {
                "user cancelled the selector" -> USER_CANCELED_LOGIN
                else -> GENERIC
            }
        }
    }
}