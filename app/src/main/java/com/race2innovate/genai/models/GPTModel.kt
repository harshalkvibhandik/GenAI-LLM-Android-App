package com.race2innovate.genai.models

enum class GPTModel(val model: String, val maxTokens: Int, val isChatCompletion: Boolean = false) {
    /*GPT4("gpt-4", 8192, isChatCompletion = true),
    GPT_4_V0613("gpt-4-0613", 8192, isChatCompletion = true),
    GPT_4_P32K("gpt-4-32k", 32768, isChatCompletion = true),
    GPT_4_P32K_V0613("gpt-4-32k-0613", 32768, isChatCompletion = true),
    GPT_35_TURBO("gpt-3.5-turbo", 4096),
    TEXT_DAVINCI_V003("text-davinci-003", 4096),
    TEXT_CURIE_V001("text-curie-001", 2048),
    TEXT_BABBAGE_V001("text-babbage-001", 2048),
    TEXT_ADA_V001("text-ada-001", 2048),*/
    HSBC_HACKATHON_GPT_35_TURBO_P16K("hsbchackthon-gpt35-turbo-16k", 800, isChatCompletion = true)
}