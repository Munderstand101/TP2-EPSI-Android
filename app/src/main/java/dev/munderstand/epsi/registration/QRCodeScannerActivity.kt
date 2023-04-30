package dev.munderstand.epsi.registration
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import dev.munderstand.epsi.R
import dev.munderstand.epsi.common.HomeActivity
import org.json.JSONObject
import java.io.IOException

class QRCodeScannerActivity : AppCompatActivity() {

    private val TAG = QRCodeScannerActivity::class.java.simpleName
    private val RC_HANDLE_CAMERA_PERM = 2

    private lateinit var mSurfaceView: SurfaceView
    private lateinit var mCameraSource: CameraSource
  //  private var mCameraSource: CameraSource1? = null

    private lateinit var mBarcodeDetector: BarcodeDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_scanner)

        mSurfaceView = findViewById(R.id.surface_view)

        val rc = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (rc == PackageManager.PERMISSION_GRANTED) {
            startCameraSource()
        } else {
            requestCameraPermission()
        }
    }

    private fun requestCameraPermission() {
        val permissions = arrayOf(Manifest.permission.CAMERA)
        ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RC_HANDLE_CAMERA_PERM) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCameraSource()
            } else {
                Toast.makeText(this, "Camera permission not granted", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun startCameraSource() {
        // Check that Google Play services is installed and up to date.
        val code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(applicationContext)
        if (code != ConnectionResult.SUCCESS) {
            GoogleApiAvailability.getInstance().getErrorDialog(this, code, 0)?.show()
            finish()
        }

        // Create the barcode detector and camera source
        mBarcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.QR_CODE)
            .build()

        mCameraSource = CameraSource.Builder(this, mBarcodeDetector)
            .setRequestedPreviewSize(640, 480)
            .setAutoFocusEnabled(true)
            .build()

        // Set up the surface holder callback to start the camera source when the surface is ready
        mSurfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return
                    }
                    mCameraSource?.let {
                        it.start(mSurfaceView.holder)
                    } ?: run {
                        Log.e(TAG, "CameraSource is null.")
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "Unable to start camera source.", e)
                    mCameraSource?.release()
                 //   mCameraSource = null
                }
            }


            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                // Do nothing
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                if (mCameraSource != null) {
                    mCameraSource.stop()
                }
            }
        })

        // Set up the barcode detector processor

        var isBarcodeProcessed = false

        mBarcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                // Do nothing
            }
            override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
                if (!isBarcodeProcessed) {
                    val barcodes: SparseArray<Barcode> = detections?.detectedItems ?: return
                    if (barcodes.size() > 0) {
                        isBarcodeProcessed = true
                        val barcodeValue = barcodes.valueAt(0).displayValue
                        runOnUiThread {
                            Toast.makeText(applicationContext, "QR Code Scanned: $barcodeValue", Toast.LENGTH_SHORT).show()

                            // Save scanned JSON data to SharedPreferences
                            val jsonData = JSONObject(barcodeValue)
                            val sharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("firstName", jsonData.optString("firstName"))
                            editor.putString("lastName", jsonData.optString("lastName"))
                            editor.putString("email", jsonData.optString("email"))
                            editor.putString("address", jsonData.optString("address"))
                            editor.putString("zipcode", jsonData.optString("zipcode"))
                            editor.putString("city", jsonData.optString("city"))
                            editor.putString("cardRef", jsonData.optString("cardRef"))
                            editor.apply()

                            // Start new activity to display scanned data
                            val intent = Intent(this@QRCodeScannerActivity, HomeActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        })


    }

    companion object {
        const val REQUEST_QR_SCAN = 101
    }

}