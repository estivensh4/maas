package com.estivensh4.maasapp.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.estivensh4.maasapp.R
import com.estivensh4.maasapp.domain.model.GetInformationOutput
import com.estivensh4.maasapp.presentation.components.CustomButton
import com.estivensh4.maasapp.presentation.components.CustomOutlinedTextField
import com.estivensh4.maasapp.presentation.viewmodel.DashboardViewModel
import com.estivensh4.maasapp.util.Keyboard
import com.estivensh4.maasapp.util.UiEvent
import com.estivensh4.maasapp.util.keyboardAsState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import uz.yusufbekibragimov.swipecard.CardShadowSide
import uz.yusufbekibragimov.swipecard.SwipeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    dashboardViewModel: DashboardViewModel = koinViewModel()
) {
    val focusManager = LocalFocusManager.current
    var showAlert by remember { mutableStateOf(false) }
    var itemDelete by remember { mutableStateOf(GetInformationOutput()) }
    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            skipPartiallyExpanded = false,
            initialValue = SheetValue.Hidden
        )
    )
    val scope = rememberCoroutineScope()
    val cardNumber by dashboardViewModel.cardNumber.collectAsState()
    val enabled by dashboardViewModel.enabled.collectAsState()
    val isLoading by dashboardViewModel.isLoading.collectAsState()
    val cardList by dashboardViewModel.cardList.collectAsState()

    LaunchedEffect(true) {
        dashboardViewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowMessage -> {
                    scope.launch {
                        sheetState.snackbarHostState.showSnackbar(uiEvent.message)
                    }
                }

                UiEvent.CloseModal -> {
                    scope.launch {
                        focusManager.clearFocus()
                        sheetState.bottomSheetState.hide()
                    }
                }

                else -> println("Nothing event")
            }
        }
    }


    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            val isKeyboardOpen by keyboardAsState()
            val showKeyboard = isKeyboardOpen == Keyboard.Opened
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.text_register_card),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.size(24.dp))
                AnimatedVisibility(visible = !showKeyboard) {
                    Column {
                        Card(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 10.dp
                            )
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.card_variant),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(150.dp)
                                    .wrapContentWidth(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                    }
                }
                CustomOutlinedTextField(
                    value = cardNumber,
                    onValueChange = dashboardViewModel::setCardNumber,
                    label = stringResource(id = R.string.text_card_number)
                )
                Spacer(modifier = Modifier.size(12.dp))
                CustomButton(
                    text = stringResource(id = R.string.btn_add),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    isLoading = isLoading
                ) {
                    dashboardViewModel.onEvent(
                        DashboardViewModel.DashboardEvents.ValidCard
                    )
                }
            }
        }
    ) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            sheetState.bottomSheetState.expand()
                        }
                    },
                    contentColor = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null
                    )
                }
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                Text(
                    text = stringResource(id = R.string.text_dashboard),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.size(24.dp))
                if (cardList.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = stringResource(id = R.string.text_empty_card_list))
                    }
                } else {

                    if (cardList.size == 1) {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(cardList) { item ->
                                Card(
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 10.dp,
                                    )
                                ) {
                                    Box {
                                        Image(
                                            painter = painterResource(id = R.drawable.card),
                                            contentDescription = null,
                                            modifier = Modifier.fillMaxWidth(),
                                            contentScale = ContentScale.Crop
                                        )
                                        Column(
                                            modifier = Modifier
                                                .align(Alignment.BottomStart)
                                                .padding(16.dp)
                                        ) {
                                            Text(text = item.userName)
                                            Text(text = item.cardNumber)
                                        }
                                        FilledIconButton(
                                            onClick = {
                                                itemDelete = item
                                                showAlert = true
                                            },
                                            colors = IconButtonDefaults.filledIconButtonColors(
                                                containerColor = MaterialTheme.colorScheme.error
                                            ),
                                            modifier = Modifier
                                                .align(Alignment.BottomEnd)
                                                .padding(16.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.Delete,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        SwipeCard(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            itemsList = cardList.toMutableList(),
                            shadowSide = CardShadowSide.ShadowTop,
                            orientation = Orientation.Vertical,
                            heightCard = 250.dp,
                            betweenMargin = 40.dp
                        ) { item ->
                            Card(
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 10.dp,
                                )
                            ) {
                                Box {
                                    Image(
                                        painter = painterResource(id = R.drawable.card),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxWidth(),
                                        contentScale = ContentScale.Crop
                                    )
                                    Column(
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .padding(16.dp)
                                    ) {
                                        Text(text = item.userName)
                                        Text(text = item.cardNumber)
                                    }
                                    FilledIconButton(
                                        onClick = {
                                            itemDelete = item
                                            showAlert = true
                                        },
                                        colors = IconButtonDefaults.filledIconButtonColors(
                                            containerColor = MaterialTheme.colorScheme.error
                                        ),
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .padding(16.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Delete,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            AnimatedVisibility(visible = showAlert) {
                AlertDialogDelete(
                    onDismissRequest = {
                        showAlert = false
                    },
                    cardNumber = itemDelete.cardNumber
                ) {
                    dashboardViewModel.onEvent(
                        DashboardViewModel.DashboardEvents.DeleteCard(itemDelete)
                    )
                    showAlert = false
                }
            }
        }
    }
}


@Composable
fun AlertDialogDelete(
    cardNumber: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.onSurface,
        icon = {
            Icon(Icons.Rounded.Info, contentDescription = null)
        },
        title = {
            Text(
                text = stringResource(id = R.string.btn_delete),
                color = Color.Black
            )
        },
        text = {
            Text(text = stringResource(id = R.string.text_delete_card, cardNumber))
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(stringResource(id = R.string.btn_delete))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(id = R.string.btn_cancel))
            }
        }
    )
}