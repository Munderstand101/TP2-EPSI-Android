package dev.munderstand.epsi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RegistrationFormActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_form)
        setHeaderTxt("Création de compte")

    }
}