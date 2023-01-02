package gaur.himanshu.interoperabilityyt

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class SliderViewModel : ViewModel() {

    val sliderCount = MutableLiveData<Float>(0f)

    fun setSliderCount(value:Float) = sliderCount.postValue(value)

    fun resetCount()= sliderCount.postValue(0f)
}