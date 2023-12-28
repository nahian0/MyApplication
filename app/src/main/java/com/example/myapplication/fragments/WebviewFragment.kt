package com.example.myapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
import com.example.myapplication.util.Constants


class WebviewFragment : DialogFragment() {

    var alert: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NORMAL,
            R.style.FullScreenDialogStyle
        )
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        return inflater.inflate(R.layout.fragment_webview, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val HtmlUrl = arguments?.getString("bKashUrl")

        val myWebView = getView()?.findViewById<WebView>(R.id.WebView)




        myWebView?.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                if(url.toString().contains("status=success")){
                    Constants.liveData.value = true
                    dialog?.dismiss()
                }
                else if(url.toString().contains("status=failure")){
                    val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext(), R.style.DialogStyle).apply {
                        setTitle("Payment Failure!!")
                        setCancelable(false)
                        setPositiveButton("Try Again") { _, _ ->
                            try {
                                alert?.dismiss()
                            }
                            catch (e: ActivityNotFoundException) {
                                throw e
                            }
                        }
                    }
                    alert = builder.create()
                    alert?.show()
                    val updateButton: Button? = alert?.getButton(DialogInterface.BUTTON_POSITIVE)
                    updateButton?.setTextColor(Color.parseColor("#E91E63"))
                    dialog?.dismiss()
                }
                else if(url.toString().contains("status=cancel")){
                    val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext(), R.style.DialogStyle).apply {
                        setTitle("Payment Canceled!!")
                        setCancelable(false)
                        setPositiveButton("Okay") { _, _ ->
                            try {
                                alert?.dismiss()
                            }
                            catch (e: ActivityNotFoundException) {
                                throw e
                            }
                        }
                    }
                    alert = builder.create()
                    alert?.show()
                    val updateButton: Button? = alert?.getButton(DialogInterface.BUTTON_POSITIVE)
                    updateButton?.setTextColor(Color.parseColor("#E91E63"))
                    dialog?.dismiss()
                }
            }
        }

        if (HtmlUrl != null) {
            myWebView?.loadUrl(HtmlUrl)
        }
        myWebView?.settings?.javaScriptEnabled = true
        myWebView?.settings?.allowContentAccess = true
        myWebView?.settings?.domStorageEnabled = true
        myWebView?.settings?.useWideViewPort = true
    }


}