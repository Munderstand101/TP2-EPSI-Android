package dev.munderstand.epsi.registration


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import dev.munderstand.epsi.R
import dev.munderstand.epsi.common.BaseActivity


class SignInActivity : BaseActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val buttonQrCodeScan = findViewById<Button>(R.id.qrCodeScan)
        val buttonRegistrationButton = findViewById<Button>(R.id.registrationButton)


        setHeaderTxt("Bienvenue !")
       // showBack()

/*
        val scanButton = findViewById<Button>(R.id.scan_button)
        scanButton.setOnClickListener {
            val intent = Intent(this@HomeActivity, QRCodeScanActivity::class.java)
            startActivity(intent)
        }
        */

        buttonQrCodeScan.setOnClickListener(View.OnClickListener {
            val intent = Intent(application, QRCodeScannerActivity::class.java)
              startActivity(intent)
        })

        buttonRegistrationButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(application, RegistrationFormActivity::class.java)
            startActivity(intent)
        })

    }
}