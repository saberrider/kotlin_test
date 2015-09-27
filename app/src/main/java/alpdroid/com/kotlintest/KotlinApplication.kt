package alpdroid.com.kotlintest

import android.app.Application
import com.github.salomonbrys.kodein.KodeinHolder
import com.github.salomonbrys.kodein.lazyKodein
import com.github.salomonbrys.kodein.singleton

/**
 * Created by Florian on 9/27/2015.
 */
public open class KotlinApplication : Application(), KodeinHolder {
    override val kodein by lazyKodein {
        bind<Service>() with singleton {
            Service()
        }
    }
}

/**
 * fake singleton service
 */
public class Service {

    var counter: Int = 0

    fun activate() {
        counter++
        println("service called $counter times.")
    }
}