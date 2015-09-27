package alpdroid.com.kotlintest

import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import retrofit.http.GET
import retrofit.http.Path
import rx.Observable

/**
 * Created by Florian on 9/27/2015.
 */
/**
 * the retrofit interface
 */
public interface KotlinServer {

    @GET("/posts/{number}")
    fun getUser(@Path ("number") name: Int): Observable<Item>

    @GET("/posts")
    fun getUsers(): Observable<MutableList<Item>>

}

/**
 * the service controller class
 */
public object API {

    val service: KotlinServer;

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create<KotlinServer>(KotlinServer::class.java)
    }

    fun getUser(id: Int = 0): Observable<Item> {
        return service.getUser(id)
    }

    fun getUsers(): Observable<MutableList<Item>> {
        return service.getUsers()
    }
}

/**
 * the data model
 */
data public open class Item(val userId: Int,
                            val id: Int,
                            val title: String,
                            val body: String)