package com.example.cartalkuk.api.datasource

import com.example.cartalkuk.data.model.VehicleEnquiryRequestModel
import com.example.cartalkuk.database.entity.VehicleEntity
import com.squareup.moshi.Moshi

class RegCheckerLocalDataSource {
    fun getVehicleDetails(
        request: VehicleEnquiryRequestModel
    ): VehicleEntity {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(VehicleEntity::class.java)
        return adapter.fromJson(json)!!
    }
}

private const val json = "{\"registrationNumber\":\"YY03TKT\", \"taxStatus\": \"Taxed\", " +
        "\"taxDueDate\": \"2023-05-01\",\"motStatus\": \"Valid\", \"make\": \"Stubbed\", " +
        "\"yearOfManufacture\":2003, \"engineCapacity\": 1298, \"co2Emissions\": 152," +
        "\"fuelType\": \"PETROL\", \"markedForExport\": false, \"colour\":\"SILVER\", " +
        "\"typeApproval\": \"M1\", \"dateOfLastV5CIssued\": \"2021-07-08\",\"motExpiryDate\": " +
        "\"2023-04-26\", \"wheelplan\": \"2 AXLE RIGID BODY\", " +
        "\"monthOfFirstRegistration\":\"2003-05\"}"