package com.tiagohs.script_reader.helpers.enums

import com.tiagohs.script_reader.R

enum class MessageType(val title: Int, val color: Int) {
    ERROR(R.string.error_title, R.color.msg_error_background),
    ATTENTION(R.string.error_attention, R.color.msg_attention_background),
    SUCCESS(R.string.error_success, R.color.msg_success_background);
}