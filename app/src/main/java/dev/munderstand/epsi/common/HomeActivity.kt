package dev.munderstand.epsi.common

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import dev.munderstand.epsi.R
import dev.munderstand.epsi.account.*

class HomeActivity : BaseActivity() {


    val tab1FragmentMap = MapFragment.newInstance("", "")
    val tab2FragmentCarte = CarteFragment.newInstance("", "")
    val tab3FragmentOffres = OffresFragment.newInstance("", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val textViewTab1Map = findViewById<TextView>(R.id.textViewTabMap)
        val textViewTab2Cartes = findViewById<TextView>(R.id.textViewTabCatres)
        val textViewTab3Offres = findViewById<TextView>(R.id.textViewTabOffres)


        // Get the SharedPreferences instance
        val sharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE)

        // Retrieve a value from SharedPreferences
        val firstName = sharedPreferences.getString("firstName", "")

        setHeaderTxt("LOGO")
      //  showBack()
        showRight()

        showTab2()

        textViewTab1Map.setOnClickListener(View.OnClickListener {
            showTab1()
        })

        textViewTab2Cartes.setOnClickListener(View.OnClickListener {
            showTab2()
        })
        textViewTab3Offres.setOnClickListener(View.OnClickListener {
            showTab3()
        })
    }


    fun showTab1() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack("Map")
        fragmentTransaction.replace(R.id.fragmentContent, tab1FragmentMap)
        fragmentTransaction.commit()
    }

    fun showTab2() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack("Cartes")
        fragmentTransaction.replace(R.id.fragmentContent, tab2FragmentCarte)
        fragmentTransaction.commit()
    }

    private fun showTab3() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack("Offres")
        fragmentTransaction.replace(R.id.fragmentContent, tab3FragmentOffres)
        fragmentTransaction.commit()
    }


}