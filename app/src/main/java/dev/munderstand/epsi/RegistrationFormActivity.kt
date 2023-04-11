package dev.munderstand.epsi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class RegistrationFormActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_form)
        val buttonCreateFormButton = findViewById<Button>(R.id.createFormButton)

        setHeaderTxt("Création de compte")
        showBack()

        buttonCreateFormButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(application, HomeActivity::class.java)
            startActivity(intent)
        })

    }
}