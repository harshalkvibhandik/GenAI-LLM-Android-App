package com.race2innovate.genai.models

import com.google.gson.annotations.SerializedName

enum class TurboRole(val value: String) {
    @SerializedName("system")
    SYSTEM("system"),

    @SerializedName("assistant")
    ASSISTANT("assistant"),

    @SerializedName("user")
    USER("user")
}