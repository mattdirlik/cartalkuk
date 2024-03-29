package com.example.cartalkuk.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cartalkuk.ui.queryconfirmation.QueryConfirmationCard
import com.example.cartalkuk.ui.vehicledetails.VehicleDetailsScreen
import com.example.cartalkuk.vm.home.HomeViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        with(homeViewModel.uiState) {
            when (this) {
                is HomeUiState.VehicleDetails -> {
                    VehicleDetailsScreen(
                        vehicle = vehicle,
                        onAddToGarageClick = { homeViewModel.upsertVehicleToGarage(vehicle)},
                        onBackPressed = { homeViewModel.setVehicleDetailsOpenStatus(false) }
                    )
                }

                else -> {
                    val focusRequester = remember { FocusRequester() }
                    RegistrationInputComponent(
                        value = homeViewModel.uiState.registration,
                        onValueChanged = { homeViewModel.updateRegistration(it) },
                        onSearch = { homeViewModel.getVehicleDetails() },
                        focusRequester = focusRequester
                    )

                    when (this) {
                        is HomeUiState.Default -> {
                            LoadingSpinner(
                                modifier = Modifier.padding(24.dp),
                                isShown = isLoadingSpinnerShown
                            )
                        }

                        is HomeUiState.QueryConfirmation -> {
                            QueryConfirmationCard(
                                onButtonClick = { confirmed ->
                                    homeViewModel.setVehicleDetailsOpenStatus(confirmed)
                                    if (!confirmed) {
                                        focusRequester.requestFocus()
                                    }
                                },
                                colour = colour,
                                make = make
                            )
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingSpinner(
    modifier: Modifier = Modifier,
    isShown: Boolean = true
) {
    val spinnerColour = MaterialTheme.colorScheme.primary
    val backColour = Color.Gray
    val strokeWidth = 4.dp
    if (isShown) {
        CircularProgressIndicator(
            modifier = modifier.drawBehind {
                drawCircle(
                    color = spinnerColour,
                    radius = size.width / 2 - strokeWidth.toPx() / 2,
                    style = Stroke(strokeWidth.toPx())
                )
            },
            color = backColour,
            strokeWidth = strokeWidth
        )
    }
}