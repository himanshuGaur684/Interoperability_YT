package gaur.himanshu.interoperabilityyt

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){

    val state = MutableLiveData<String>("")

    fun postValue(value:String){
        state.postValue(value)
    }


}