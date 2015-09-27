package alpdroid.com.kotlintest

import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinHolder
import com.github.salomonbrys.kodein.injectInstance
import kotlinx.android.synthetic.activity_main.listview
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by Florian on 9/27/2015.
 */
public class KotlinActivity : Activity(), KodeinHolder {
    override val kodein: Kodein by lazy(LazyThreadSafetyMode.NONE) { (application as KotlinApplication).kodein }

    // dependency injection
    public val service: Service by injectInstance<Service>()

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_main)

        // injected singleton
        service.activate()
        service.activate()
        service.activate()

        // Retrofit api call
        API.getUsers().doOnError {
            e ->
            println("Error: $e")
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    x: MutableList<Item> ->

                    // automatically found listview in layout
                    listview.removeAllViews()
                    for (item in x)
                        listview.addItem(item)
                }

    }

    // ViewGroup extension
    fun ViewGroup.addItem(item: Item) {
        var textView = TextView(applicationContext)
        textView.text = "${item.id}: ${item.title}\n"
        textView.setTextColor(R.color.colorPrimaryDark)
        this.addView(textView)
    }
}

