package dev.munderstand.epsi.account

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import dev.munderstand.epsi.R
import dev.munderstand.epsi.common.BaseActivity
import org.json.JSONObject


class StoreDetailsActivity : BaseActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_details)

        // Retrieve the store data from the intent extras
        val storeJsonString = intent.getStringExtra("store")
        val store = JSONObject(storeJsonString.toString())

        setHeaderTxt(store.getString("name"))
        showBack()
       // showRight()

        val image=findViewById<ImageView>(R.id.iv_image)

        if(intent.extras!=null){
            val url=intent.extras!!.getString("url",store.getString("pictureStore"));
            Picasso.get().load(url).into(image);
        }
        val storeAddress = findViewById<TextView>(R.id.tv_lblAdress)
        storeAddress.text = "Address : "+store.getString("address")

        val storeAddressDetails = findViewById<TextView>(R.id.tv_lblAdressDetails)
        storeAddressDetails.text = store.getString("zipcode")+" - " +store.getString("city")

        val storeDesc = findViewById<TextView>(R.id.tv_description)
        storeDesc.text = store.getString("description")


       /*
       Toast.makeText(
            this@StoreDetailsActivity,
            store.getString("name"),
            Toast.LENGTH_SHORT
        ).show()

*/

    }
}