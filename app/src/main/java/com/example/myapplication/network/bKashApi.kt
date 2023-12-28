package com.example.myapplication.network
import com.example.myapplication.model.request.CreatePaymentRequest
import com.example.myapplication.model.request.ExecutePaymentRequest
import com.example.myapplication.model.request.GrantTokenRequest
import com.example.myapplication.model.request.QueryPaymentRequest
import com.example.myapplication.model.request.SearchTransactionRequest
import com.example.myapplication.model.response.CreatePaymentResponse
import com.example.myapplication.model.response.ExecutePaymentResponse
import com.example.myapplication.model.response.GrantTokenResponse
import com.example.myapplication.model.response.QueryPaymentResponse
import com.example.myapplication.model.response.SearchTransactionResponse
import retrofit2.http.*


interface ApiInterface {
    @POST("/v1.2.0-beta/tokenized/checkout/token/grant")
    suspend fun postGrantToken(
        @Header("username") username: String?,
        @Header("password") password: String?,
        @Body grantTokenRequest: GrantTokenRequest
    ): GrantTokenResponse

    @POST("/v1.2.0-beta/tokenized/checkout/create")
    suspend fun postPaymentCreate(
        @Header("authorization") authorization: String?,
        @Header("x-app-key") xAppKey: String?,
        @Body createPaymentRequest: CreatePaymentRequest
    ): CreatePaymentResponse

    @POST("/v1.2.0-beta/tokenized/checkout/execute")
    suspend fun postPaymentExecute(
        @Header("authorization") authorization: String?,
        @Header("x-app-key") xAppKey: String?,
        @Body executePaymentRequest: ExecutePaymentRequest
    ): ExecutePaymentResponse

    @POST("/v1.2.0-beta/tokenized/checkout/general/searchTransaction")
    suspend fun postSearchTransaction(
        @Header("authorization") authorization: String?,
        @Header("x-app-key") xAppKey: String?,
        @Body searchTransactionRequest: SearchTransactionRequest
    ): SearchTransactionResponse

    @POST("/v1.2.0-beta/tokenized/checkout/payment/status")
    suspend fun postQueryPayment(
        @Header("authorization") authorization: String?,
        @Header("x-app-key") xAppKey: String?,
        @Body queryPaymentRequest: QueryPaymentRequest
    ): QueryPaymentResponse
}