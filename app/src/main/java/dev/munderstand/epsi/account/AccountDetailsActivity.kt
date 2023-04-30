package dev.munderstand.epsi.account

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import dev.munderstand.epsi.R
import dev.munderstand.epsi.common.BaseActivity
import dev.munderstand.epsi.common.HomeActivity

class AccountDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_details)

        val buttonSave = findViewById<Button>(R.id.createFormButton)

        setHeaderTxt("Account Details")
        showBack()

        val sharedPreferences: SharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE)

        val etFirstName: EditText = findViewById(R.id.et_firstName)
        val etLastName: EditText = findViewById(R.id.et_lastName)
        val etEmail: EditText = findViewById(R.id.et_email)
        val etAddress: EditText = findViewById(R.id.et_adress)
        val etPostalCode: EditText = findViewById(R.id.et_postalCode)
        val etCity: EditText = findViewById(R.id.et_city)
        val etReem: EditText = findViewById(R.id.et_reem)

        etFirstName.setText(sharedPreferences.getString("firstName", ""))
        etLastName.setText(sharedPreferences.getString("lastName", ""))
        etEmail.setText(sharedPreferences.getString("email", ""))
        etAddress.setText(sharedPreferences.getString("address", ""))
        etPostalCode.setText(sharedPreferences.getString("zipcode", ""))
        etCity.setText(sharedPreferences.getString("city", ""))
        etReem.setText(sharedPreferences.getString("cardRef", ""))

        buttonSave.setOnClickListener {
            val firstName: String = etFirstName.text.toString().trim()
            val lastName: String = etLastName.text.toString().trim()
            val email: String = etEmail.text.toString().trim()
            val address: String = etAddress.text.toString().trim()
            val postalCode: String = etPostalCode.text.toString().trim()
            val city: String = etCity.text.toString().trim()
            val reem: String = etReem.text.toString().trim()

            if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && address.isNotEmpty() && postalCode.isNotEmpty() && city.isNotEmpty() && reem.isNotEmpty()) {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("firstName", firstName)
                editor.putString("lastName", lastName)
                editor.putString("email", email)
                editor.putString("address", address)
                editor.putString("postalCode", postalCode)
                editor.putString("city", city)
                editor.putString("reem", reem)
                editor.apply()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Please fill all the fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}