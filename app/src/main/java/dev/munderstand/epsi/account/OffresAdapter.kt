package dev.munderstand.epsi.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.munderstand.epsi.R

class OffresAdapter(val offrres: MutableList<Offre>) :
    RecyclerView.Adapter<OffresAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cell_produit, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val offre = offrres.get(position)
        holder.textViewName.text = offre.titre
        holder.textViewDesc.text = offre.description
        Picasso.get().load(offre.picture_url).into(holder.imageViewProduit)
        holder.contentLayout.setOnClickListener(View.OnClickListener {
            Toast.makeText(holder.contentLayout.context, offre.titre, Toast.LENGTH_SHORT).show()
        })
    }

    override fun getItemCount(): Int {
        return offrres.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName = view.findViewById<TextView>(R.id.tv_ProductName)
        val textViewDesc = view.findViewById<TextView>(R.id.tv_Desc)
        val imageViewProduit= view.findViewById<ImageView>(R.id.iv_Product)
        val contentLayout = view.findViewById<LinearLayout>(R.id.contentLayout)
    }
}
