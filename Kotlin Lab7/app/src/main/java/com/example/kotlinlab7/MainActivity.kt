package com.example.kotlinlab7

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ListView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Edge-to-edge UI處理
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 宣告並設定UI元件
        val spinner = findViewById<Spinner>(R.id.spinner)
        val listView = findViewById<ListView>(R.id.listView)
        val gridView = findViewById<GridView>(R.id.gridView)

        // 生成資料
        val countList = List(10) { "${it + 1}個" }
        val itemList = mutableListOf<Item>()
        val priceRange = 10..100
        val imageArray = resources.obtainTypedArray(R.array.image_list)
        for (i in 0 until imageArray.length()) {
            itemList.add(Item(imageArray.getResourceId(i, 0), "水果${i + 1}", priceRange.random()))
        }
        imageArray.recycle()

        // 設定Adapters
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, countList)
        gridView.numColumns = 3
        gridView.adapter = MyAdapter(this, itemList, R.layout.adapter_vertical)
        listView.adapter = MyAdapter(this, itemList, R.layout.adapter_horizontal)
    }
}
