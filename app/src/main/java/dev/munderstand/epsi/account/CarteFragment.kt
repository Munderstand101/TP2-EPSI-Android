package dev.munderstand.epsi.account


import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import dev.munderstand.epsi.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.oned.Code128Writer


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CarteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CarteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var rootView: View
    private lateinit var imageView: ImageView
    private lateinit var sharedPref: SharedPreferences

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
        sharedPref = requireActivity().getSharedPreferences("account", Context.MODE_PRIVATE)
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_carte, container, false)
        imageView = rootView.findViewById(R.id.iv_image)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val tvName = rootView.findViewById<TextView>(R.id.tv_lblName) // Call findViewById on the root view
        tvName.text = sharedPref.getString("firstName", "")
       // val data = sharedPref.getString("cardRef", "")

     //   val bitmap = encodeAsBitmap(sharedPref.getString("cardRef", "").toString(), sharedPref.getString("cardRef", ""))
        val bitmap = encodeAsBitmap("12345678", "12345678")
        imageView.setImageBitmap(bitmap)


    }


    @Throws(WriterException::class)
    private fun encodeAsBitmap(contents: String, text: String?): Bitmap? {
        val writer = Code128Writer()
        val bitMatrix: BitMatrix = writer.encode(contents, BarcodeFormat.CODE_128, 512, 256)
        val width: Int = bitMatrix.width
        val height: Int = bitMatrix.height + 32 // Add space for text
        val bmp = createBitmap(width, height, Bitmap.Config.RGB_565)
        val canvas = Canvas(bmp)
        canvas.drawColor(Color.WHITE) // Set background to white
        val barcodeBitmap = createBitmap(width, height - 32, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height - 32) {
                barcodeBitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        canvas.drawBitmap(barcodeBitmap, 0f, 0f, null)
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 24f
        canvas.drawText(text.toString(), width / 2f, height - 4f, paint)
        return bmp
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CarteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CarteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}