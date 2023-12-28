package com.example.myapplication.model.request

import com.google.gson.annotations.SerializedName

data class QueryPaymentRequest(
  @SerializedName("paymentID")
  var paymentID: String? = null,
  )