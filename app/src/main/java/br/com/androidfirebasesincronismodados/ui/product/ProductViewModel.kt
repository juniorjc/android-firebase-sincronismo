package br.com.androidfirebasesincronismodados.ui.product

import android.util.Log
import androidx.lifecycle.*
import br.com.androidfirebasesincronismodados.TAG
import br.com.androidfirebasesincronismodados.data.model.MarketList
import br.com.androidfirebasesincronismodados.data.model.Product
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

    private val _marketList = MutableLiveData<MarketList?>()
    val marketListLiveData: LiveData<MarketList?>
            get() = _marketList;

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        fetchProducts()
    }

    fun fetchProducts(){
        try {
            db.collection("products")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result){
                        Log.d("ProductViewModel", "${document.id} => ${document.data}")
                    }
                }

        }catch (e: Exception){
            _marketList.postValue(null)
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

        db.collection("products").addSnapshotListener(onMarketCollectionSnapshotListener)

       // db.collection("products").document("1").addSnapshotListener(onMarketlistSnapshotListener)

    }

    val onMarketCollectionSnapshotListener: EventListener<QuerySnapshot> = EventListener { querySnapshot, e ->
        if (e != null) {
            Log.w(TAG, "listen:error", e)
            return@EventListener
        }

        querySnapshot?.let { snap ->

            Log.d(TAG, "# MarketList onMarketCollectionSnapshotListener ---> ${snap.documents.joinToString(", ")}")

            val obj: MutableList<out MarketList> = snap.toObjects(MarketList::class.java)
            Log.d(TAG, "# MarketList onMarketCollectionSnapshotListener ---> ${obj.toString()}")
        }
    }

    val onMarketlistSnapshotListener: EventListener<DocumentSnapshot> = EventListener { docSnapshot, e ->
        if (e != null) {
            Log.w(TAG, "listen:error", e)
            return@EventListener
        }
        docSnapshot?.let { snap ->
            val obj: MarketList? = snap.toObject(MarketList::class.java)

            Log.d(TAG,  "# MarketList onPermissionSnapshotListener ---> ${obj.toString()}")

            /**
             * 1- Verifica se o valor é diferente de NULL
             * 2- Verifica se o documento foi obtido do servidor do Firebase
             *
             * Resultado esperado:
             * -- Se o valor não for nulo, atualiza os produtos
             * -- Se o valor for nulo, e caso tenha sido obtido do servidor, quer dizer que a lista está vazia
             */
            if (obj != null || !snap.metadata.isFromCache) {
                _marketList.postValue(obj)
            } else {
                _marketList.postValue(null)
            }

        }
    }
}