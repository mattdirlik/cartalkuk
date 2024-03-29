package com.example.cartalkuk.ui.vehicledetails

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cartalkuk.database.entity.VehicleEntity
import com.example.cartalkuk.ui.garage.AddCarToGarageButton
import com.example.cartalkuk.ui.theme.RegCheckerTheme
import com.example.cartalkuk.ui.theme.RegPlateTextStyle
import com.example.cartalkuk.ui.theme.RegistrationPlateBackground
import com.example.cartalkuk.ui.vehicledetails.MotStatus.NoDetailsHeld
import com.example.cartalkuk.ui.vehicledetails.MotStatus.NoResults
import com.example.cartalkuk.ui.vehicledetails.MotStatus.NotValid
import com.example.cartalkuk.ui.vehicledetails.MotStatus.Valid
import com.example.cartalkuk.ui.vehicledetails.TaxStatus.NotRoadTaxed
import com.example.cartalkuk.ui.vehicledetails.TaxStatus.Sorn
import com.example.cartalkuk.ui.vehicledetails.TaxStatus.Taxed
import com.example.cartalkuk.ui.vehicledetails.TaxStatus.Untaxed
import com.example.cartalkuk.ui.vehicledetails.statusvalidity.StatusValidityCard
import com.example.cartalkuk.ui.vehicledetails.statusvalidity.StatusValidityCard.StatusValidityCardVariant.InvalidVariant
import com.example.cartalkuk.ui.vehicledetails.statusvalidity.StatusValidityCard.StatusValidityCardVariant.UnknownVariant
import com.example.cartalkuk.ui.vehicledetails.statusvalidity.StatusValidityCard.StatusValidityCardVariant.ValidVariant


@Composable
fun VehicleDetailsScreen(
    vehicle: VehicleEntity,
    onAddToGarageClick: () -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        with(vehicle) {
            item {
                RegistrationPlate(
                    modifier = Modifier.padding(vertical = 16.dp),
                    registration = registrationNumber
                )
            }

            item {
                TaxAndMotStatusRow(
                    taxStatus = taxStatus,
                    taxDueDate = taxDueDate,
                    motStatus = motStatus,
                    motExpiryDate = motExpiryDate
                )
            }

            item {
                VehicleDetailsList(
                    colour = colour ?: "",
                    make = make ?: "",
                    firstRegDate = monthOfFirstRegistration,
                    firstDvlaRegDate = monthOfFirstDvlaRegistration,
                    engineCapacity = engineCapacity,
                    co2Emissions = co2Emissions,
                    euroStatus = euroStatus,
                    yearOfManufacture = yearOfManufacture,
                    realDrivingEmissions = realDrivingEmissions,
                    exportMarker = markedForExport,
                    fuelType = fuelType,
                    vehicleTypeApproval = typeApproval,
                    wheelplan = wheelplan,
                    revenueWeight = revenueWeight,
                    v5cDate = dateOfLastV5CIssued
                )
            }

            item {
                AddCarToGarageButton(
                    onClick = onAddToGarageClick
                )
            }
        }
    }

    BackHandler {
        onBackPressed()
    }
}

@Composable
fun RegistrationPlate(
    modifier: Modifier = Modifier,
    registration: String
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .background(RegistrationPlateBackground)
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = registration,
            style = RegPlateTextStyle,
            minLines = 1
        )
    }
}

@Composable
fun TaxAndMotStatusRow(
    taxStatus: String?,
    taxDueDate: String?,
    motStatus: String?,
    motExpiryDate: String?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val taxVariant = when (taxStatus) {
            Taxed.validity -> ValidVariant
            Untaxed.validity -> InvalidVariant
            NotRoadTaxed.validity,
            Sorn.validity -> UnknownVariant

            else -> UnknownVariant
        }
        val motVariant = when (motStatus) {
            Valid.validity -> ValidVariant
            NotValid.validity -> InvalidVariant
            NoResults.validity,
            NoDetailsHeld.validity -> UnknownVariant

            else -> UnknownVariant
        }

        StatusValidityCard(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            variant = taxVariant,
            statusText = taxStatus ?: "",
            date = taxDueDate ?: "",
            type = "Tax due: ",
        )
        StatusValidityCard(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            variant = motVariant,
            statusText = "MOT",
            date = motExpiryDate,
            type = "MOT expires: ",
        )
    }
}

@Preview
@Composable
fun RegistrationPlatePreview() {
    RegCheckerTheme {
        RegistrationPlate(registration = "YY03TKT")
    }
}

@Preview
@Composable
fun VehicleDetailsScreenPreview() {
    RegCheckerTheme {
        Surface {
            Column {
                VehicleDetailsScreen(
                    vehicle = VehicleEntity(
                        registrationNumber = "YY03 TKT",
                        colour = "silver",
                        make = "suzuki",
                        taxStatus = "Taxed",
                        taxDueDate = "1998-05-09",
                        motStatus = "Valid",
                        motExpiryDate = "2021-12-30"
                    )
                )
            }
        }
    }
}

enum class MotStatus(val validity: String) {
    Valid("Valid"),
    NotValid("Not valid"),
    NoDetailsHeld("No details held by DVLA"),
    NoResults("No results returned")
}

enum class TaxStatus(val validity: String) {
    Taxed("Taxed"),
    Untaxed("Untaxed"),
    NotRoadTaxed("Not Taxed for on Road Use"),
    Sorn("SORN")
}