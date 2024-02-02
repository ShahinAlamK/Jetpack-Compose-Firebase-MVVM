package com.example.task.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.task.R
import com.example.task.data.view.TaskViewModel
import kotlinx.coroutines.launch

@Composable
fun DrawerSheet(drawerState: DrawerState, viewModel: TaskViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()

    ModalDrawerSheet(
        drawerShape = RoundedCornerShape(0.dp),
        drawerContainerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(end = 100.dp)
    ) {
        Box(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(id = R.string.app_name).uppercase(),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        Divider(thickness = 1.dp)
        LazyColumn {
            item {

                Spacer(modifier = Modifier.height(10.dp))
                NavigationDrawerItem(
                    shape = RoundedCornerShape(0.dp),
                    icon = { Icon(imageVector = Icons.Outlined.List, contentDescription = "") },
                    label = { Text(text = "All Task") },
                    badge = { Text(text = viewModel.response.value.size.toString()) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            viewModel.fetchTask()
                            drawerState.close() }
                    }
                )

                NavigationDrawerItem(
                    shape = RoundedCornerShape(0.dp),
                    icon = { Icon(imageVector = Icons.Outlined.Favorite, contentDescription = "") },
                    label = { Text(text = "Favorite") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                    }
                )

                NavigationDrawerItem(
                    shape = RoundedCornerShape(0.dp),
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = ""
                        )
                    },
                    label = { Text(text = "Completed") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } }
                )

                NavigationDrawerItem(
                    shape = RoundedCornerShape(0.dp),
                    icon = { Icon(imageVector = Icons.Outlined.Info, contentDescription = "") },
                    label = { Text(text = "About") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } }
                )
            }
        }
    }
}