package dev.munderstand.epsi.common

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import dev.munderstand.epsi.R
import dev.munderstand.epsi.account.AccountDetailsActivity

open class BaseActivity: AppCompatActivity() {

    fun setHeaderTxt(txt:String) {
        val textViewTitle = findViewById<TextView>(R.id.tv_title)
        textViewTitle.text = txt
    }

    fun showBack(){
        val imageViewBack=findViewById<ImageView>(R.id.iv_Left_Arrow)
        imageViewBack.visibility=View.VISIBLE
        imageViewBack.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    fun showRight() {
        val imageViewBack = findViewById<ImageView>(R.id.iv_Right_Arrow)
        imageViewBack.visibility = View.VISIBLE
        imageViewBack.setOnClickListener {
            val intent = Intent(this, AccountDetailsActivity::class.java)
            startActivity(intent)
        }
    }



}