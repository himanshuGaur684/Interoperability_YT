package gaur.himanshu.interoperabilityyt

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.lifecycle.MutableLiveData
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import gaur.himanshu.interoperabilityyt.databinding.AndroidViewBindBinding
import gaur.himanshu.interoperabilityyt.databinding.BarcodeScannerBinding
import gaur.himanshu.interoperabilityyt.ui.theme.InteroperabilityYTTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel:SliderViewModel by viewModels()

        setContent {
            InteroperabilityYTTheme {
                // A surface container using the 'background' color from the theme
                val state = viewModel.sliderCount.observeAsState()

                SliderComp(value = state.value!!, onValueChange = {
                    viewModel.setSliderCount(it)
                }) {
                    viewModel.resetCount()
                }
            }
        }
    }
}

@Composable
fun SliderComp(value: Float, onValueChange: (Float) -> Unit, reset: () -> Unit) {

    Column() {
        Slider(value = value, onValueChange = {
            onValueChange.invoke(it)
        }, valueRange = 0f..100f)

        AndroidViewBinding(factory = AndroidViewBindBinding::inflate) {
            textView.text = value.toString()
            button.setOnClickListener {
                reset.invoke()
            }
        }
    }

}

