package com.race2innovate.genai.ui.conversations.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.CodeBlockStyle
import com.halilibo.richtext.ui.RichText
import com.halilibo.richtext.ui.RichTextStyle
import com.halilibo.richtext.ui.material3.SetupMaterial3RichText
import com.race2innovate.genai.R
import com.race2innovate.genai.models.MessageModel
import java.util.Date

@Composable
fun MessageCard(message: MessageModel, isHuman: Boolean = false) {
    /*val humanPaddingStart = if (isHuman) 32.dp else 0.dp
    val botPaddingEnd = if (isHuman) 0.dp else 32.dp*/
    Column(
        horizontalAlignment = if (isHuman) Alignment.End else Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
        /*.padding(start = humanPaddingStart, end = botPaddingEnd)*/
    ) {
        if (isHuman) {
            HumanMessageCard(message = message)
        } else {
            BotMessageCard(message = message)
        }
    }
}

@Composable
fun HumanMessageCard(message: MessageModel, isHuman: Boolean = false) {
    Row {
        Column(
            modifier = Modifier.weight(9f),
            horizontalAlignment = Alignment.End
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .wrapContentWidth(),
            ) {
                Text(
                    text = message.question,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                    textAlign = TextAlign.Justify,
                )
            }
        }
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = stringResource(id = R.string.cd_message_sent_by_you),
            modifier = Modifier
                .weight(1f)
                .size(24.dp),
            tint = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
fun BotMessageCard(message: MessageModel, isHuman: Boolean = false) {
    Row {
        Image(
            painter = painterResource(id = R.drawable.icon_chatgpt_appbar),
            modifier = Modifier
                .size(24.dp)
                .padding(2.dp),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(id = R.string.cd_message_from_genai_llm)
        )
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            SetupMaterial3RichText {
                RichText(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                    style = RichTextStyle(
                        codeBlockStyle = CodeBlockStyle(
                            textStyle = TextStyle(
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Normal,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.tertiary
                            ),
                            wordWrap = true,
                            modifier = Modifier.background(
                                color = Color.Black,
                                shape = RoundedCornerShape(6.dp)
                            )
                        )
                    )
                ) {
                    Markdown(
                        message.answer.trimIndent()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessageCardPreviewHuman() {
    MessageCard(
        message = MessageModel(
            id = "",
            conversationId = "",
            question = "question text field by Human ",
            answer = "question text field by Human ",
            createdAt = Date()
        ),
        isHuman = true
    )
}

@Preview(showBackground = true)
@Composable
fun MessageCardPreviewBot() {
    MessageCard(
        message = MessageModel(
            id = "",
            conversationId = "",
            question = "answer text field by Bot ",
            answer = "answer text field by Bot ",
            createdAt = Date()
        ),
        isHuman = false
    )
}