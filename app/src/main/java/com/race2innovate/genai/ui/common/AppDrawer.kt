package com.race2innovate.genai.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.AddComment
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.race2innovate.genai.R
import com.race2innovate.genai.constants.urlToGithub
import com.race2innovate.genai.helpers.UrlLauncher
import com.race2innovate.genai.models.ConversationModel
import com.race2innovate.genai.ui.conversations.ConversationViewModel
import com.race2innovate.genai.ui.theme.iconColorDark
import com.race2innovate.genai.ui.theme.primary
import com.race2innovate.genai.ui.theme.secondary
import com.race2innovate.genai.ui.theme.textColorDark
import kotlinx.coroutines.launch


@Composable
fun AppDrawer(
    onChatClicked: (String) -> Unit,
    onNewChatClicked: () -> Unit,
    conversationViewModel: ConversationViewModel = hiltViewModel(),
    onIconClicked: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        AppDrawerIn(
            onChatClicked = onChatClicked,
            onNewChatClicked = onNewChatClicked,
            onIconClicked = onIconClicked,
            conversationViewModel = { conversationViewModel.newConversation() },
            deleteConversation = { text ->
                coroutineScope.launch {
                    conversationViewModel.deleteConversation(text)
                }
            },
            deleteMessages = { text ->
                coroutineScope.launch {
                    conversationViewModel.deleteMessages(text)
                }
            },
            onConversation = { conversationModel: ConversationModel ->
                coroutineScope.launch { conversationViewModel.onConversation(conversationModel) }
            },
            currentConversationState = conversationViewModel.currentConversationState.collectAsState().value,
            conversationState = conversationViewModel.conversationsState.collectAsState().value
        )
    }
}


@Composable
private fun AppDrawerIn(
    onChatClicked: (String) -> Unit,
    onNewChatClicked: () -> Unit,
    onIconClicked: () -> Unit,
    conversationViewModel: () -> Unit,
    deleteConversation: (String) -> Unit,
    deleteMessages: (String) -> Unit,
    onConversation: (ConversationModel) -> Unit,
    currentConversationState: String,
    conversationState: MutableList<ConversationModel>,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        DrawerHeader(clickAction = onIconClicked)
        DividerItem()
        DrawerItemHeader(stringResource(id = R.string.text_chats))
        ChatItem(stringResource(id = R.string.text_new_chat), Icons.Outlined.AddComment, false) {
            onNewChatClicked()
            conversationViewModel()
        }
        HistoryConversations(
            onChatClicked,
            deleteConversation,
            onConversation,
            deleteMessages,
            currentConversationState,
            conversationState
        )
        DividerItem(modifier = Modifier.padding(horizontal = 28.dp))
        DrawerItemHeader(stringResource(id = R.string.settings))
        ChatItem(
            stringResource(id = R.string.settings),
            Icons.Filled.Settings,
            false
        ) { onChatClicked("Settings") }
        ProfileItem(
            stringResource(id = R.string.author_name),
            Icons.Filled.Person,
        ) {
            UrlLauncher().openUrl(context = context, urlToGithub)
        }
    }
}

@Composable
private fun DrawerHeader(
    clickAction: () -> Unit = {}
) {
    val paddingSizeModifier = Modifier
        .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
        .size(34.dp)

    Row(verticalAlignment = CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .weight(1f), verticalAlignment = CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_chatgpt_drawer),
                modifier = paddingSizeModifier.then(Modifier.clip(RoundedCornerShape(6.dp))),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                Text(
                    stringResource(id = R.string.app_name),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary,
                )
                Text(
                    stringResource(id = R.string.powered_by),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
        // This used to shuffle in between dark and light themes
        // It can be enabled later once UI gets stable for specific themes
        IconButton(
            onClick = {
                clickAction.invoke()
            },
        ) {
            Icon(
                imageVector = Icons.Filled.WbSunny,
                contentDescription = stringResource(id = R.string.cd_toggle_theme),
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onTertiary,
            )
        }
    }
}

@Composable
private fun ColumnScope.HistoryConversations(
    onChatClicked: (String) -> Unit,
    deleteConversation: (String) -> Unit,
    onConversation: (ConversationModel) -> Unit,
    deleteMessages: (String) -> Unit,
    currentConversationState: String,
    conversationState: List<ConversationModel>
) {
    val scope = rememberCoroutineScope()

    LazyColumn(
        Modifier
            .fillMaxWidth()
            .weight(1f, false),
    ) {
        items(conversationState.size) { index ->
            RecycleChatItem(
                text = conversationState[index].title,
                Icons.Filled.Message,
                selected = conversationState[index].id == currentConversationState,
                onChatClicked = {
                    onChatClicked(conversationState[index].id)

                    scope.launch {
                        onConversation(conversationState[index])
                    }
                },
                onDeleteClicked = {
                    scope.launch {
                        deleteConversation(conversationState[index].id)
                        deleteMessages(conversationState[index].id)
                    }
                }
            )
        }
    }
}

@Composable
private fun DrawerItemHeader(text: String) {
    Box(
        modifier = Modifier
            .heightIn(min = 52.dp)
            .padding(horizontal = 28.dp),
        contentAlignment = CenterStart
    ) {
        Text(
            text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
private fun ChatItem(
    text: String,
    icon: ImageVector = Icons.Filled.Edit,
    selected: Boolean,
    onChatClicked: () -> Unit
) {
    val background = if (selected) {
        Modifier.background(MaterialTheme.colorScheme.secondary)
    } else {
        Modifier
    }
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clip(CircleShape)
            .then(background)
            .clickable(onClick = onChatClicked),
        verticalAlignment = CenterVertically
    ) {
        val iconTint = if (selected) {
            iconColorDark
        } else {
            MaterialTheme.colorScheme.onTertiary
        }
        Icon(
            icon,
            tint = iconTint,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                .size(24.dp),
            contentDescription = null,
        )
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(start = 12.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun RecycleChatItem(
    text: String,
    icon: ImageVector = Icons.Filled.Edit,
    selected: Boolean,
    onChatClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    val background = if (selected) {
        Modifier.background(secondary)
    } else {
        Modifier
    }
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(horizontal = 34.dp)
            .clip(CircleShape)
            .then(background)
            .clickable(onClick = onChatClicked),
        verticalAlignment = CenterVertically
    ) {
        val iconTint = if (selected) {
            iconColorDark
        } else {
            MaterialTheme.colorScheme.onTertiary
        }
        Icon(
            icon,
            tint = iconTint,
            modifier = Modifier
                .padding(start = 16.dp)
                .size(24.dp),
            contentDescription = null,
        )
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium,
            color = if (selected) {
                textColorDark
            } else {
                MaterialTheme.colorScheme.tertiary
            },
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(0.80f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.cd_delete_chat),
            tint = iconTint,
            modifier = Modifier
                .padding(end = 16.dp)
                .size(24.dp)
                .clickable { onDeleteClicked() }
        )
    }
}

@Composable
private fun ProfileItem(
    text: String,
    icon: ImageVector = Icons.Filled.Edit,
    onProfileClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clip(CircleShape)
            .clickable(onClick = onProfileClicked),
        verticalAlignment = CenterVertically
    ) {
        val paddingSizeModifier = Modifier
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
            .size(24.dp)
        Icon(
            icon,
            tint = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                .size(24.dp),
            contentDescription = null,
        )
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
fun DividerItem(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    )
}

@Preview
@Composable
fun PreviewAppDrawerIn(
) {
    AppDrawerIn(
        onChatClicked = {},
        onNewChatClicked = {},
        onIconClicked = {},
        conversationViewModel = {},
        deleteConversation = {},
        deleteMessages = {},
        conversationState = mutableListOf(),
        currentConversationState = String(),
        onConversation = { _: ConversationModel -> }

    )

}

