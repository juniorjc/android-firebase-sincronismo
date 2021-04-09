package br.com.androidfirebasesincronismodados.ui.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.androidfirebasesincronismodados.R
import br.com.androidfirebasesincronismodados.TAG

class ProductFragment : Fragment() {

    private lateinit var productViewModel: ProductViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        productViewModel =
                ViewModelProvider(this).get(ProductViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_product, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        productViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }

    override fun onResume() {
        super.onResume()
        productViewModel.onResume()

        productViewModel.marketListLiveData.observe(viewLifecycleOwner,{
            Log.d(TAG, "marketListLiveData : ${it}")
        })
    }
}