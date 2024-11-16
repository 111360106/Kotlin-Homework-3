package com.example.kotlinlab7

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(
    context: Context,
    private val data: List<Item>,
    private val layout: Int
) : ArrayAdapter<Item>(context, layout, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // 使用 LayoutInflater 來處理 convertView 和建立新 View
        val view = convertView ?: LayoutInflater.from(context).inflate(layout, parent, false)
        val item = data[position]

        // 設定圖片和訊息
        view.findViewById<ImageView>(R.id.imgPhoto).setImageResource(item.photo)
        view.findViewById<TextView>(R.id.tvMsg).text = if (layout == R.layout.adapter_vertical) {
            item.name
        } else {
            "${item.name}: ${item.price}元"
        }
        return view
    }
}
