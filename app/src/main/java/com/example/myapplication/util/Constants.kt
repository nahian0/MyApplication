package com.example.myapplication.util

import android.app.ProgressDialog
import androidx.lifecycle.MutableLiveData

object Constants {
    var bkashSandboxUsername = "01619830121"
    var bkashSandboxPassword = "<x6>@+ki0Gm"
    var bkashSandboxAppKey = "EoAXzGA3OZ2EGcRoOiymvDbEtc"
    var bkashSandboxAppSecret = "jMt6JKLuPONg0izdPbLn88OOjPbiIbbza5sUsIBnWpsMygTY08Um"
    var mode = "0011"
    var payerReference = "number"
    var callbackURL = "http://mamtechit.com/callback"
    var merchantAssociationInfo = ""
    var amount = "30"
    var currency = "BDT"
    var intents = "sale"
    var merchantInvoiceNumber = "Inv0124"

    var sessionIdToken = ""
    var paymentIDBkash = ""
    var searchTextInput = ""
    var pd: ProgressDialog?= null
    val liveData = MutableLiveData<Boolean>()
}
