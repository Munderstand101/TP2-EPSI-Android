package dev.munderstand.epsi.common

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import dev.munderstand.epsi.R
import dev.munderstand.epsi.registration.SignInActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Get the SharedPreferences instance
        val sharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE)

        // Retrieve a value from SharedPreferences
        val firstName = sharedPreferences.getString("firstName", "")

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            var newIntent= Intent(application, SignInActivity::class.java)
            if (firstName!=""){
                newIntent = Intent(application, HomeActivity::class.java)
            }

            startActivity(newIntent)
            finish()
        },2000)





        // Change the logo for dark mode
        val logo = findViewById<ImageView>(R.id.splash_logo)
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            logo.setImageResource(R.drawable.logo)
        }


    }
}