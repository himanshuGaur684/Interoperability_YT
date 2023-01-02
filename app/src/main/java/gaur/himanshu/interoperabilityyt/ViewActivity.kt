package gaur.himanshu.interoperabilityyt

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.LifecycleOwner
import gaur.himanshu.interoperabilityyt.databinding.ActivityComposeBinding


class ViewActivity : AppCompatActivity() {

    private var _binding: ActivityComposeBinding? = null
    private val binding: ActivityComposeBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityComposeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel : MainViewModel by viewModels()

        viewModel.state.observe(this as LifecycleOwner){
            it?.let {
                binding.textView.text = it
            }
        }

        binding.composeView.run {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
            setContent {
                val state = viewModel.state.observeAsState()
                OutlinedTextExample(value = state.value!!, onValueChange = {
                    viewModel.postValue(it)
                })
            }
        }

    }
}

@Composable
fun OutlinedTextExample(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(value = value, onValueChange = onValueChange)
}


