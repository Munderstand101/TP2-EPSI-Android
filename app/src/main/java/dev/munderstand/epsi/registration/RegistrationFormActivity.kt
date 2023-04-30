package dev.munderstand.epsi.registration

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import dev.munderstand.epsi.R
import dev.munderstand.epsi.common.BaseActivity
import dev.munderstand.epsi.common.HomeActivity

class RegistrationFormActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_form)
        val buttonCreateFormButton = findViewById<Button>(R.id.createFormButton)

        setHeaderTxt("Création de compte")
        showBack()

        val sharedPreferences: SharedPreferences =
            getSharedPreferences("account", Context.MODE_PRIVATE)

        val etFirstName: EditText = findViewById(R.id.et_firstName)
        val etLastName: EditText = findViewById(R.id.et_lastName)
        val etEmail: EditText = findViewById(R.id.et_email)
        val etAddress: EditText = findViewById(R.id.et_adress)
        val etPostalCode: EditText = findViewById(R.id.et_postalCode)
        val etCity: EditText = findViewById(R.id.et_city)
        val etReem: EditText = findViewById(R.id.et_reem)

        val createFormButton: Button = findViewById(R.id.createFormButton)
        createFormButton.setOnClickListener {
            val firstName: String = etFirstName.text.toString().trim()
            val lastName: String = etLastName.text.toString().trim()
            val email: String = etEmail.text.toString().trim()
            val address: String = etAddress.text.toString().trim()
            val postalCode: String = etPostalCode.text.toString().trim()
            val city: String = etCity.text.toString().trim()
            val reem: String = etReem.text.toString().trim()

            if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && address.isNotEmpty()
                && postalCode.isNotEmpty() && city.isNotEmpty() && reem.isNotEmpty()
            ) {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("firstName", firstName)
                editor.putString("lastName", lastName)
                editor.putString("email", email)
                editor.putString("address", address)
                editor.putString("zipcode", postalCode)
                editor.putString("city", city)
                editor.putString("cardRef", reem)
                editor.apply()

                val intent: Intent = Intent(this@RegistrationFormActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(
                    this@RegistrationFormActivity,
                    "Please fill in all fields",
                    Toast.LENGTH_SHORT
                ).show()
            }

            /*
        val editTextFirstName = findViewById<EditText>(R.id.et_firstName)
        editTextFirstName.setText(readSharedPref("firstName"))

        val editTextLastName = findViewById<EditText>(R.id.et_lastName)
        editTextLastName.setText(readSharedPref("lastName"))

        val editTextTextEmailAddress = findViewById<EditText>(R.id.et_email)
        editTextTextEmailAddress.setText(readSharedPref(("email")))

        val editTextAddress = findViewById<EditText>(R.id.et_adress)
        editTextAddress.setText(readSharedPref("address"))


        buttonGoSignup.setOnClickListener(View.OnClickListener {
            writeSharedPref("firstName", editTextFirstName.text.toString())
            writeSharedPref("lastName", editTextLastName.text.toString())
            writeSharedPref("password", editTextPassword.text.toString())
            writeSharedPref("email", editTextEmail.text.toString())
            writeSharedPref("address", editTextAddress.text.toString())
            writeSharedPref("city", editTextCity.text.toString())
            writeSharedPref("zipCode", editTextZipCode.text.toString())

        })

        buttonCreateFormButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(application, HomeActivity::class.java)
            startActivity(intent)
        })
            */
        }

        /*  fun writeSharedPref(key: String, value: String) {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("account", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun readSharedPref(key: String): String {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("account", Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "").toString()
    }*/

    }
}