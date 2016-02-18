package pl.marchuck.majormallstrikesback.observables

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import rx.Observable

/**
 * Created by Łukasz Marczak

 * @since 14.02.16
 */
class RxSuggestionWrapper {

    fun init(searchView: EditText): Observable<String> {
        return Observable.defer {
            Observable.create<String> {
                subscriber ->
                searchView.addTextChangedListener(object : TextWatcher {
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        Log.d("TAG", "onTextChanged: " + p0.toString())
                        if (p0?.length ?: 3 > 3) subscriber.onNext(p0.toString())
                    }

                    override fun afterTextChanged(p0: Editable?) {
                        // subscriber.onNext(p0.toString())
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        //subscriber.onNext(p0.toString())
                    }
                })
            }
        }
    }
}
