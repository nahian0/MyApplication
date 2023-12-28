package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.request.CreatePaymentRequest
import com.example.myapplication.model.request.ExecutePaymentRequest
import com.example.myapplication.model.request.GrantTokenRequest
import com.example.myapplication.model.request.QueryPaymentRequest
import com.example.myapplication.model.response.CreatePaymentResponse
import com.example.myapplication.model.response.ExecutePaymentResponse
import com.example.myapplication.model.response.GrantTokenResponse
import com.example.myapplication.model.response.QueryPaymentResponse
import com.example.myapplication.network.ApiInterface
import com.example.myapplication.network.BkashApiClient
import com.example.myapplication.util.Constants.bkashSandboxAppKey
import com.example.myapplication.util.Constants.bkashSandboxAppSecret
import com.example.myapplication.util.Constants.bkashSandboxPassword
import com.example.myapplication.util.Constants.bkashSandboxUsername
import com.example.myapplication.util.Constants
import com.example.myapplication.util.Constants.amount
import com.example.myapplication.util.Constants.callbackURL
import com.example.myapplication.util.Constants.currency
import com.example.myapplication.util.Constants.intents
import com.example.myapplication.util.Constants.merchantAssociationInfo
import com.example.myapplication.util.Constants.merchantInvoiceNumber
import com.example.myapplication.util.Constants.mode
import com.example.myapplication.util.Constants.payerReference
import com.example.myapplication.util.Constants.paymentIDBkash
import com.example.myapplication.util.Constants.sessionIdToken
import com.example.myapplication.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val job = Job()
    val grantTokenLiveData = SingleLiveEvent<GrantTokenResponse?>()
    private val createPaymentLiveData = SingleLiveEvent<CreatePaymentResponse?>()
    private val executePaymentLiveData = SingleLiveEvent<ExecutePaymentResponse?>()
    private val queryPaymentLiveData = SingleLiveEvent<QueryPaymentResponse?>()
    fun getGrantTokenObserver(): SingleLiveEvent<GrantTokenResponse?> {
        return grantTokenLiveData
    }
    fun getCreatePaymentObserver(): SingleLiveEvent<CreatePaymentResponse?> {
        return createPaymentLiveData
    }
    fun getExecutePaymentObserver(): SingleLiveEvent<ExecutePaymentResponse?> {
        return executePaymentLiveData
    }
    fun getQueryPaymentObserver(): SingleLiveEvent<QueryPaymentResponse?> {
        return queryPaymentLiveData
    }
    fun grantTokenApiCall() {
        viewModelScope.launch(Dispatchers.IO + job) {
            val bkashApiClient = BkashApiClient.client?.create(ApiInterface::class.java)
            val response  = bkashApiClient?.postGrantToken(
                username = bkashSandboxUsername,
                password = bkashSandboxPassword,
                GrantTokenRequest(
                    appKey = bkashSandboxAppKey,
                    appSecret = bkashSandboxAppSecret
                )
            )
            grantTokenLiveData.postValue(response)
        }
    }

    fun createPaymentApiCall() {
        viewModelScope.launch(Dispatchers.IO + job) {
            val bkashApiClient = BkashApiClient.client?.create(ApiInterface::class.java)
            val response  = bkashApiClient?.postPaymentCreate(
                authorization = "Bearer $sessionIdToken",
                xAppKey = bkashSandboxAppKey,
                CreatePaymentRequest(
                    mode = mode,
                    payerReference = payerReference,
                    callbackURL = callbackURL,
                    merchantAssociationInfo = merchantAssociationInfo,
                    amount = amount,
                    currency = currency,
                    intent = intents,
                    merchantInvoiceNumber = merchantInvoiceNumber,
                )
            )
            createPaymentLiveData.postValue(response)
        }
    }

    fun executePaymentApiCall() {
        viewModelScope.launch(Dispatchers.IO + job) {
            val bkashApiClient = BkashApiClient.client?.create(ApiInterface::class.java)
            val response  = bkashApiClient?.postPaymentExecute(
                authorization = "Bearer $sessionIdToken",
                xAppKey = bkashSandboxAppKey,
                ExecutePaymentRequest(
                    paymentID = paymentIDBkash
                )
            )
            executePaymentLiveData.postValue(response)
        }
    }

    fun queryPaymentApiCall() {
        viewModelScope.launch(Dispatchers.IO + job) {
            val bkashApiClient = BkashApiClient.client?.create(ApiInterface::class.java)
            val response  = bkashApiClient?.postQueryPayment(
                authorization = "Bearer $sessionIdToken",
                xAppKey = bkashSandboxAppKey,
                QueryPaymentRequest(
                    paymentID = paymentIDBkash
                )
            )
            queryPaymentLiveData.postValue(response)
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        Constants.pd?.dismiss()
        throwable.printStackTrace()
    }
}