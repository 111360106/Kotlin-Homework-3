package com.example.kotlinlab8

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sec)

        // 適當處理全螢幕模式的系統邊距
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 宣告元件並透過 findViewByID 方法取得
        val edName = findViewById<EditText>(R.id.edName)
        val edPhone = findViewById<EditText>(R.id.edPhone)
        val btnSend = findViewById<Button>(R.id.btnSend)

        // 設定按鈕點擊監聽器
        btnSend.setOnClickListener {
            val name = edName.text.toString().trim()
            val phone = edPhone.text.toString().trim()

            // 判斷是否輸入資料
            when {
                name.isEmpty() -> showToast("請輸入姓名")
                phone.isEmpty() -> showToast("請輸入電話")
                else -> {
                    val bundle = Bundle().apply {
                        putString("name", name)
                        putString("phone", phone)
                    }
                    // 使用 setResult() 傳回聯絡人資料
                    setResult(Activity.RESULT_OK, Intent().putExtras(bundle))
                    finish()
                }
            }
        }
    }

    // 顯示 Toast 訊息
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
