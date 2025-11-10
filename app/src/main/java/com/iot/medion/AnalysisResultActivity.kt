package com.iot.medion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AnalysisResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_analysis_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // --- UI 요소들을 찾아옵니다. ---
        // val analysisResultTitle = findViewById<TextView>(R.id.analysisResultTitle)
        // ... (다른 TextView들) ...
        val buttonConfirm = findViewById<Button>(R.id.buttonConfirm) // '확인' 버튼 찾아오기

        // --- '확인' 버튼 클릭 이벤트 처리 ---
        buttonConfirm.setOnClickListener {
            val intent = Intent(this@AnalysisResultActivity, OptionActivity::class.java)
            startActivity(intent)
            finish() // 분석 결과 화면은 종료하고 OptionActivity로 넘어갑니다.
        }
    }
}