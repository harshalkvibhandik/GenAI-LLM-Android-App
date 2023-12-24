package com.race2innovate.genai.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.race2innovate.genai.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(onClickMenu: () -> Unit) {
    Surface(
        shadowElevation = 4.dp,
        tonalElevation = 0.dp
    ) {
        CenterAlignedTopAppBar(
            title = {
                Box {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_chatgpt_appbar),
                            modifier = Modifier.size(24.dp),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.app_name),
                            textAlign = TextAlign.Center,
                            fontSize = 16.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                }
            },
            navigationIcon = {
                IconButton(
                    onClick = {
                        onClickMenu()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(id = R.string.cd_open_drawer),
                        modifier = Modifier.size(26.dp),
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.tertiary,
                navigationIconContentColor = MaterialTheme.colorScheme.onTertiary,
                actionIconContentColor = MaterialTheme.colorScheme.onTertiary
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppBarPreview() {
    AppBar(
        onClickMenu = { }
    )
}
