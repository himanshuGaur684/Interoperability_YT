package gaur.himanshu.interoperabilityyt

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.MutableLiveData
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import gaur.himanshu.interoperabilityyt.databinding.BarcodeScannerBinding
import gaur.himanshu.interoperabilityyt.ui.theme.InteroperabilityYTTheme

class MainActivity : ComponentActivity() {

    private var _binding: BarcodeScannerBinding? = null
    private val binding: BarcodeScannerBinding get() = _binding!!

    private val text = MutableLiveData<String>("")

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            it?.let {
                if (it) binding.barcodeScannerView.resume()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        startActivity(Intent(this,ViewActivity::class.java))



        _binding = BarcodeScannerBinding.inflate(layoutInflater)
        val format = listOf(BarcodeFormat.QR_CODE)
        binding.barcodeScannerView.decoderFactory = DefaultDecoderFactory(format)
        binding.barcodeScannerView.initializeFromIntent(intent)

        val callback = object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                result?.let {
                    if (it.text == null || it.text == text.value) return
                    text.value = it.text
                }
            }
        }

        binding.barcodeScannerView.decodeContinuous(callback)

        setContent {
            InteroperabilityYTTheme {
                // A surface container using the 'background' color from the theme
                val state = text.observeAsState()
                InteroperabilityExample(root = binding.root, value = state.value!!)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requestPermission.launch(android.Manifest.permission.CAMERA)
    }

    override fun onPause() {
        super.onPause()
        binding.barcodeScannerView.pause()
    }

}

@Composable
fun InteroperabilityExample(root: View, value: String) {
    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = { root }, modifier = Modifier.wrapContentSize())
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = value, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    }
}
