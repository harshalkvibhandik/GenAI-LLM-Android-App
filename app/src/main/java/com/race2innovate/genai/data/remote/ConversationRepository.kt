package com.race2innovate.genai.data.remote

import com.race2innovate.genai.models.ConversationModel

interface ConversationRepository {
    suspend fun fetchConversations(): MutableList<ConversationModel>
    fun newConversation(conversation: ConversationModel): ConversationModel
    suspend fun deleteConversation(conversationId: String)
}