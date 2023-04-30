package dev.munderstand.epsi

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class OffersAdapter(val products: ArrayList<Offers>, val mcontext: Context) :
    RecyclerView.Adapter<OffersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cell_offers, viewGroup, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products.get(position)
        var productLink: String = product.picture_url
        holder.textViewProductName.text = product.name
        holder.textViewProductDescription.text = product.description + "..."
        Picasso.get().load(productLink).into(holder.imageProduct)

      //  holder.contentLayout.setOnClickListener(View.OnClickListener {
        //    val intent = Intent(mcontext, DetailProductActivity::class.java)
          //  intent.putExtra("name", product.name)
            //intent.putExtra("description", product.description)
           // intent.putExtra("picture_url", product.picture_url)
          //  mcontext.startActivity(intent)
        //})
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageProduct : ImageView = view.findViewById<ImageView>(R.id.imageViewOffer)
        val textViewProductName: TextView = view.findViewById<TextView>(R.id.textViewOfferName)
        val textViewProductDescription: TextView = view.findViewById<TextView>(R.id.textViewOfferDescription)
        val contentLayout: LinearLayout = view.findViewById<LinearLayout>(R.id.contentLayout)

    }
}
