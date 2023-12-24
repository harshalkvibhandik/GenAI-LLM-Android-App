package com.race2innovate.genai.ui.conversations

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.race2innovate.genai.R
import com.race2innovate.genai.constants.conversationTestTag
import com.race2innovate.genai.models.MessageModel
import com.race2innovate.genai.ui.conversations.components.MessageCard
import com.race2innovate.genai.ui.conversations.components.TextInput
import com.race2innovate.genai.ui.conversations.ui.theme.ChatGPTLiteTheme
import com.race2innovate.genai.ui.theme.BackGroundColor

@Composable
fun Conversation() {
    ChatGPTLiteTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = BackGroundColor,
        ) {
            Box(Modifier.fillMaxSize()) {
                Column(Modifier.fillMaxSize()) {
                    MessageList(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp, end = 16.dp)
                    )
                    TextInput()
                }
            }
        }
    }
}

@Composable
fun MessageList(
    modifier: Modifier = Modifier,
    conversationViewModel: ConversationViewModel = hiltViewModel(),
) {
    val listState = rememberLazyListState()

    val conversationId by conversationViewModel.currentConversationState.collectAsState()
    val messagesMap by conversationViewModel.messagesState.collectAsState()
    val isFabExpanded by conversationViewModel.isFabExpanded.collectAsState()

    val messages: List<MessageModel> =
        if (messagesMap[conversationId] == null) listOf() else messagesMap[conversationId]!!

    if (messages.isEmpty()) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.icon_azure),
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(bottom = 32.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                )
                Text(
                    stringResource(id = R.string.start_chatting),
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
                Text(
                    stringResource(id = R.string.start_chatting_hint),
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            }
        }
    } else {
        Box(modifier = modifier) {
            LazyColumn(
                contentPadding =
                WindowInsets.statusBars.add(WindowInsets(top = 90.dp)).asPaddingValues(),
                modifier = Modifier
                    .testTag(conversationTestTag)
                    .fillMaxSize(),
                reverseLayout = true,
                state = listState,
            ) {
                items(messages.size) { index ->
                    Box(modifier = Modifier.padding(bottom = if (index == 0) 10.dp else 0.dp)) {
                        Column {
                            MessageCard(
                                message = messages[index],
                                isLast = index == messages.size - 1,
                                isHuman = true
                            )
                            MessageCard(message = messages[index])
                        }
                    }
                }
            }
            // This feature would gets added later on
            /*ExtendedFloatingActionButton(
                text = {
                    Text(
                        text = stringResource(id = R.string.fab_stop_generating),
                        color = Color.White
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Stop,
                        contentDescription = stringResource(id = R.string.cd_stop_generating),
                        tint = Color.White,
                        modifier = Modifier.size(35.dp)
                    )
                },
                onClick = {
                    conversationViewModel.stopReceivingResults()
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp)
                    .animateContentSize(),
                expanded = isFabExpanded,
                containerColor = Blue225
            )*/
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    ChatGPTLiteTheme {
//        Greeting2("Android")
    }
}