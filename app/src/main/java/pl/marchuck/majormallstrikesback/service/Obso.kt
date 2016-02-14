package pl.marchuck.majormallstrikesback.service

import rx.Observable

/**
 * Created by Łukasz Marczak

 * @since 14.02.16
 */
object Obso {
    fun create(): Observable<String> {
        return Observable.create { subscriber ->
            subscriber.onNext("xd")
            subscriber.onCompleted()
        }
    }
}
