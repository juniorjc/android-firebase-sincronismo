package br.com.androidfirebasesincronismodados.ui.product

import android.util.Log
import androidx.lifecycle.*
import br.com.androidfirebasesincronismodados.TAG
import br.com.androidfirebasesincronismodados.data.model.MarketList
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class ProductViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is product Fragment"
    }
    val text: LiveData<String> = _text
    private val db: FirebaseFirestore get() = Firebase.firestore

    private val _marketList = MutableLiveData<List<MarketList>>()
    val marketListLiveData: LiveData<List<MarketList>>
            get() = _marketList;

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        fetchProducts()
    }

    fun fetchProducts(){
        try {
            db.collection("market-list")
                .get()
                .addOnSuccessListener { result ->
                    val obj: MutableList<out MarketList> = result.toObjects(MarketList::class.java)
                    _marketList.postValue(obj)
                    Log.d("ProductViewModel -->", "${result.documents.joinToString(", ")}")
                }

            db.collection("market-list").addSnapshotListener(onMarketCollectionSnapshotListener)
        }catch (e: Exception){
            _marketList.postValue(ArrayList())
        }


        addProduct()
    }

    fun addProduct(){
        /*val product = Product(name = "Teste", section = "Teste 1")
        db.collection("products")
            .add(product)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }*/

        //TODO implementar escuta
        //db.collection("market-list").addSnapshotListener(onMarketCollectionSnapshotListener)


    }

    val onMarketCollectionSnapshotListener: EventListener<QuerySnapshot> = EventListener { querySnapshot, e ->
        if (e != null) {
            Log.w(TAG, "listen:error", e)
            return@EventListener
        }

        querySnapshot?.let { snap ->
            val obj: MutableList<out MarketList> = snap.toObjects(MarketList::class.java)
            _marketList.postValue(obj)
            Log.d(TAG, "# MarketList onMarketCollectionSnapshotListener ---> ${snap.documents.joinToString(", ")}")
        }
    }
}