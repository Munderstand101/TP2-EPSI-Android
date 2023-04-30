package dev.munderstand.epsi.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import dev.munderstand.epsi.R

import org.json.JSONException


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "OffresFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class OffresFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OffresAdapter
    private val items = mutableListOf<Offre>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offres, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rcv_products)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        adapter = OffresAdapter(items)
        recyclerView.adapter = adapter

        fetchOffers()
    }

    private fun fetchOffers() {
        val queue = Volley.newRequestQueue(activity)
        val url = "https://www.ugarit.online/epsi/offers.json"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val jsonArray = response.getJSONArray("items")
                    for (i in 0 until jsonArray.length()) {
                        val item = jsonArray.getJSONObject(i)
                        val name = item.getString("name")
                        val description = item.getString("description")
                        val pictureUrl = item.getString("picture_url")
                        items.add(Offre(name, description, pictureUrl))
                    }
                    adapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    Log.e(TAG, "Error parsing JSON", e)
                }
            },
            { error ->
                Log.e(TAG, "Error fetching data", error)
            })

        queue.add(jsonObjectRequest)
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OffresFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
