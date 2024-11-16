package com.example.kotlinlab9_1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var progressRabbit = 0
    private var progressTurtle = 0
    private lateinit var btnStart: Button
    private lateinit var sbRabbit: SeekBar
    private lateinit var sbTurtle: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        // Adjust padding for system bars (status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnStart = findViewById(R.id.btnStart)
        sbRabbit = findViewById(R.id.sbRabbit)
        sbTurtle = findViewById(R.id.sbTurtle)

        btnStart.setOnClickListener {
            // Disable the start button while the race is running
            btnStart.isEnabled = false

            // Reset progress
            progressRabbit = 0
            progressTurtle = 0
            sbRabbit.progress = 0
            sbTurtle.progress = 0

            // Start the race
            runRabbit()
            runTurtle()
        }
    }

    private fun showToast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    private val handler = Handler(Looper.getMainLooper()) { msg ->
        when (msg.what) {
            1 -> {
                sbRabbit.progress = progressRabbit
                if (progressRabbit >= 100 && progressTurtle < 100) {
                    showToast("兔子勝利")
                    btnStart.isEnabled = true
                }
            }
            2 -> {
                sbTurtle.progress = progressTurtle
                if (progressTurtle >= 100 && progressRabbit < 100) {
                    showToast("烏龜勝利")
                    btnStart.isEnabled = true
                }
            }
        }
        true
    }
    private fun runRabbit() {
        // 兔子可能偷懶，三分之二的機率偷懶
        val sleepProbability = arrayOf(true, true, false)
        val runnableRabbit = object : Runnable {
            override fun run() {
                if (progressRabbit < 100 && progressTurtle < 100) {
                    // 兔子每次跑 3 步，有 2/3 的機率偷懶
                    if (sleepProbability.random()) {
                        progressRabbit += 3
                    }

                    val msg = Message().apply { what = 1 }
                    handler.sendMessage(msg)

                    // 使用 postDelayed 來模擬延遲，而非 Thread.sleep()
                    handler.postDelayed(this, 100) // 每 100ms 更新一次
                }
            }
        }
        handler.post(runnableRabbit) // 開始兔子的賽跑
    }

    private fun runTurtle() {
        val runnableTurtle = object : Runnable {
            override fun run() {
                if (progressTurtle < 100 && progressRabbit < 100) {
                    progressTurtle += 1 // 烏龜每次跑一步
                    val msg = Message().apply { what = 2 }
                    handler.sendMessage(msg)

                    // 使用 postDelayed 來模擬延遲，而非 Thread.sleep()
                    handler.postDelayed(this, 100) // 每 100ms 更新一次
                }
            }
        }
        handler.post(runnableTurtle) // 開始烏龜的賽跑
    }
}
