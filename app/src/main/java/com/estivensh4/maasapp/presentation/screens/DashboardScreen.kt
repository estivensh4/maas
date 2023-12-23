package com.estivensh4.maasapp.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            skipPartiallyExpanded = true,
            initialValue = SheetValue.Hidden
        )
    )
    val scope = rememberCoroutineScope()
    val cardNumber by dashboardViewModel.cardNumber.collectAsState()
    val enabled by dashboardViewModel.enabled.collectAsState()
    val isLoading by dashboardViewModel.isLoading.collectAsState()

    LaunchedEffect(true) {
        dashboardViewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowMessage -> {
                    scope.launch {
                        sheetState.snackbarHostState.showSnackbar(uiEvent.message)
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
                    label = "Numero de tarjeta"
                )
                Spacer(modifier = Modifier.size(8.dp))
                CustomButton(
                    text = "Añadir",
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
                    }
                ) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                }
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                val list = listOf(
                    GetInformationOutput(
                        cardNumber = "1010000008551426",
                        profileEs = "e",
                        profileCode = "",
                        profile = "ese",
                        bankCode = "co",
                        bankName = "Bancolombia",
                        userName = "Test",
                        userLastName = "User"
                    ),
                    GetInformationOutput(
                        cardNumber = "1010000008553091",
                        profileEs = "e",
                        profileCode = "",
                        profile = "ese",
                        bankCode = "co",
                        bankName = "Bancolombia",
                        userName = "Pepito",
                        userLastName = "Perez"
                    ),
                    GetInformationOutput(
                        cardNumber = "1010000008553067",
                        profileEs = "e",
                        profileCode = "",
                        profile = "ese",
                        bankCode = "co",
                        bankName = "Bancolombia",
                        userName = "Pepita",
                        userLastName = "Perez"
                    )
                )
                SwipeCard(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    itemsList = list.toMutableList(),
                    shadowSide = CardShadowSide.ShadowTop,
                    orientation = Orientation.Vertical,
                    heightCard = 250.dp,
                    betweenMargin = 40.dp,
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
                                Text(text = "${item.userName} ${item.userLastName}")
                                Text(text = item.cardNumber)
                            }
                        }
                    }
                }
            }
        }
    }
}