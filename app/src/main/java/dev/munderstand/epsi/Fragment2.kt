package dev.munderstand.epsi

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment2 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val products = arrayListOf<Offers>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }







    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.activity_offer, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerviewOffers)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = OffersAdapter(products,  requireContext())

        recyclerView.adapter = adapter


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
                    requireActivity().runOnUiThread(Runnable {
                        adapter.notifyDataSetChanged()
                    })
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                requireActivity().runOnUiThread(Runnable {
                })
            }
        })

        return view


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}