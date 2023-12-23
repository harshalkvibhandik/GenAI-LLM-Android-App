package com.race2innovate.genai.data.api

import com.google.gson.JsonObject
import com.race2innovate.genai.constants.textCompletionsEndpoint
import com.race2innovate.genai.constants.textCompletionsTurboEndpoint
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface OpenAIApi {
    @POST(textCompletionsEndpoint)
    @Streaming
    fun textCompletionsWithStream(@Body body: JsonObject): Call<ResponseBody>

    @POST(textCompletionsTurboEndpoint)
    @Streaming
    fun textCompletionsTurboWithStream(@Body body: JsonObject): Call<ResponseBody>
}