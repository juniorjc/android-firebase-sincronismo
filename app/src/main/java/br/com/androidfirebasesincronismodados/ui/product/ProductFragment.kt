package br.com.androidfirebasesincronismodados.ui.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.androidfirebasesincronismodados.R
import br.com.androidfirebasesincronismodados.TAG
import br.com.androidfirebasesincronismodados.databinding.FragmentProductBinding

class ProductFragment : Fragment() {

    private lateinit var productViewModel: ProductViewModel

    val layoutId: Int
        get() = R.layout.fragment_product

    private lateinit var binding: FragmentProductBinding

    val rootView: View
        get() = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    fun initViewModel() {
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
    }

    fun initObservers() {
        productViewModel.marketListLiveData.observe(viewLifecycleOwner, Observer {
            it?.let{ list ->
                val adapter = MarketListAdapter(this.requireContext(), list)
                binding.marketListView.adapter = adapter

                binding.progress.visibility = View.GONE
            }
        })
    }

    override fun onResume() {
        super.onResume()
        productViewModel.onResume()

        productViewModel.marketListLiveData.observe(viewLifecycleOwner,{
            Log.d(TAG, "marketListLiveData : ${it}")
        })
    }
}