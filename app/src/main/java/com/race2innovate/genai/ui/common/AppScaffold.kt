package com.race2innovate.genai.ui.common

import android.annotation.SuppressLint
import androidx.compose.material3.*
import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.race2innovate.genai.ui.conversations.ConversationViewModel
import com.race2innovate.genai.ui.theme.backgroundDark
import kotlinx.coroutines.launch

//import androidx.compose.material3.ModalDrawerSheet

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AppScaffold(
    drawerState: DrawerState = rememberDrawerState(initialValue = Closed),
    onChatClicked: (String) -> Unit,
    onNewChatClicked: () -> Unit,
    onIconClicked: () -> Unit = {},
    conversationViewModel: ConversationViewModel = hiltViewModel(),
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()

    scope.launch {
        conversationViewModel.initialize()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = backgroundDark) {
                AppDrawer(
                    onChatClicked = onChatClicked,
                    onNewChatClicked = onNewChatClicked,
                    onIconClicked = onIconClicked,
                )
            }
        },
        content = content
    )

}