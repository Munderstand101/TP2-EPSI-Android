package dev.munderstand.epsi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class HomeActivity : BaseActivity() {


    val tab1Fragment = Fragment1.newInstance("", "")
    val tab2Fragment = Fragment2.newInstance("", "")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val textViewTab1 = findViewById<TextView>(R.id.textViewTabMap)
        val textViewTab2 = findViewById<TextView>(R.id.textViewTabStore)


        setHeaderTxt("Logo")
        showBack()

        textViewTab1.setOnClickListener(View.OnClickListener {
            showTab1()
        })
        textViewTab2.setOnClickListener(View.OnClickListener {
            showTab2()
        })
    }

    fun showTab1() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack("Tab1")
        fragmentTransaction.replace(R.id.fragmentContent, tab1Fragment)
        fragmentTransaction.commit()
    }

    fun showTab2() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack("Tab2")
        fragmentTransaction.replace(R.id.fragmentContent, tab2Fragment)
        fragmentTransaction.commit()
    }

}