package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.example.myapplication.ui.HomeViewModel
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCAdditionalInitializer
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCCustomerInfoInitializer
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCEMITransactionInitializer
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCProductInitializer
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCShipmentInfoInitializer
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener

class MainActivity : AppCompatActivity() ,SSLCTransactionResponseListener {


    lateinit var button: Button
    lateinit var editText: EditText

    lateinit var sslCommerzInitialization: SSLCommerzInitialization
    lateinit var additionalInitializer: SSLCAdditionalInitializer


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText =findViewById(R.id.amountId)
        button =findViewById(R.id.payButtonId)

        button.setOnClickListener {
            val amount =editText.text.toString().trim()
            Log.e("amount", amount)

            if (amount.isNotEmpty()){
                sslSetUp(amount)
            }else{
                editText.error="Error"
            }
        }
    }

    private fun sslSetUp(amount: String){
        val sslCommerzInitialization = SSLCommerzInitialization(
            "nahia658a91c80a403",
            "nahia658a91c80a403@ssl",
            amount.toDouble(),
            SSLCCurrencyType.BDT,
            "123456789098765",
            "yourProductType",
            SSLCSdkType.TESTBOX
        )


        additionalInitializer = SSLCAdditionalInitializer()
        additionalInitializer!!.valueA="value Option 1"
        additionalInitializer!!.valueB="value Option 2"
        additionalInitializer!!.valueC="value Option 3"
        additionalInitializer!!.valueD="value Option 4"

        val customerInfoInitializer = SSLCCustomerInfoInitializer(
            "customer name", "customer email",
            "address", "dhaka", "1214", "Bangladesh", "phoneNumber"
        )

        val emiTransactionInitializer = SSLCEMITransactionInitializer(1)


        val productInitializer = SSLCProductInitializer(
            "food", "food",
            SSLCProductInitializer.ProductProfile.TravelVertical(
                "Travel", "10", "A", "12", "Dhk-Syl"
            )
        )

        val shipmentInfoInitializer = SSLCShipmentInfoInitializer(
            "Courier",
            2,
            SSLCShipmentInfoInitializer.ShipmentDetails("AA", "Address 1", "Dhaka", "1000", "BD")
        )

        IntegrateSSLCommerz
            .getInstance(this)
            .addSSLCommerzInitialization(sslCommerzInitialization)
            .addCustomerInfoInitializer(customerInfoInitializer)
            .addEMITransactionInitializer(emiTransactionInitializer)
            .addProductInitializer(productInitializer)
            .addAdditionalInitializer(additionalInitializer)
            .buildApiCall(this)




    }



    override fun transactionSuccess(p0: SSLCTransactionInfoModel?) {
        Toast.makeText(applicationContext, "payment succesfull", Toast.LENGTH_SHORT).show()
    }

    override fun transactionFail(p0: String?) {
        Toast.makeText(applicationContext, "payment failed", Toast.LENGTH_SHORT).show()
    }

    override fun closed(p0: String?) {
        TODO("Not yet implemented")
    }







}