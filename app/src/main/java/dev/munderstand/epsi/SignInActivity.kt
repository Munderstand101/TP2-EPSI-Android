package dev.munderstand.epsi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class SignInActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val buttonQrCodeScan = findViewById<Button>(R.id.qrCodeScan)
        val buttonRegistrationButton = findViewById<Button>(R.id.registrationButton)


        setHeaderTxt("Création de compte")

        buttonQrCodeScan.setOnClickListener(View.OnClickListener {
            //val intent = Intent(application, InfoStudentActivity::class.java)
            //  startActivity(intent)
        })

        buttonRegistrationButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(application, RegistrationFormActivity::class.java)
            startActivity(intent)
        })

    }
}