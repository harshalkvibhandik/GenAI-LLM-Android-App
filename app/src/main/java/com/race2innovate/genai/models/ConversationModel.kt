package com.race2innovate.genai.models

import java.util.Date

data class ConversationModel(
    var id: String = Date().time.toString(),
    var title: String = "",
    var createdAt: Date = Date(),
)