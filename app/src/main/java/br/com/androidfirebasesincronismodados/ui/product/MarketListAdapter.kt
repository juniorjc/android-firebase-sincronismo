package br.com.androidfirebasesincronismodados.ui.product

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.com.androidfirebasesincronismodados.R
import br.com.androidfirebasesincronismodados.data.model.MarketList

class MarketListAdapter(private val context: Context,
                        private val dataSource: List<MarketList>
) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.item_list_product, parent, false)
        val productNameTextView = rowView.findViewById(R.id.product_name) as TextView
        val sectionTextView = rowView.findViewById(R.id.product_list_section) as TextView

        val item = getItem(position) as MarketList

        productNameTextView.text = item.productName
        sectionTextView.text = item.section

        return rowView
    }

}