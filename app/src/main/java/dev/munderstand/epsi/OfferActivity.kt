package dev.munderstand.epsi

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class OfferActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer)

        val products = arrayListOf<Offers>()


        val recyclerviewProducts = findViewById<RecyclerView>(R.id.recyclerviewOffers)
        recyclerviewProducts.layoutManager = LinearLayoutManager(this)
        val productAdapter = OffersAdapter(products, this)
        recyclerviewProducts.adapter = productAdapter

        val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()
        val request =
            Request.Builder().url("https://www.ugarit.online/epsi/offers.json").cacheControl(CacheControl.FORCE_NETWORK).build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val data = response.body?.string()
                if (data != null) {
                    val jsOffers = JSONObject(data)
                    val jsArrayOffers = jsOffers.getJSONArray("items")

                    for (i in 0
                            until jsArrayOffers.length()) {
                        val jsOffer = jsArrayOffers.getJSONObject(i)
                        val product = Offers(
                            jsOffer.optString("name", "Not found"),
                            jsOffer.optString("description", "Not found"),
                            jsOffer.optString("picture_url", "Not found"),
                        )
                        products.add(product)
                    }
                    runOnUiThread(Runnable {
                        productAdapter.notifyDataSetChanged()
                    })
                }
            }


            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread(Runnable {
                    Toast.makeText(application, e.message, Toast.LENGTH_SHORT).show()
                })
            }
        })
    }
}