package com.iot.medion

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random
import android.widget.Toast
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.graphics.Bitmap // 사진(비트맵) 처리
import android.provider.MediaStore // 카메라 인텐트 사용


class SelfDiagnosisActivity : AppCompatActivity() {

    private lateinit var takePictureLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_self_diagnosis)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        takePictureLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) { // 사진을 성공적으로 찍었을 때
                    val imageBitmap = result.data?.extras?.get("data") as Bitmap? // 찍힌 사진 (작은 썸네일)
                    if (imageBitmap != null) {
                        Toast.makeText(this, "사진이 성공적으로 찍혔습니다!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                    }
                } else if (result.resultCode == RESULT_CANCELED) { // 사용자가 사진 찍기를 취소했을 때
                    Toast.makeText(this, "사진 촬영을 취소했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

        // 1. UI 요소들을 찾아옵니다.
        val buttonMeasureTemperature = findViewById<Button>(R.id.buttonMeasureTemperature)
        val textViewTemperatureValue = findViewById<TextView>(R.id.textViewTemperatureValue)
        val buttonSubmitDiagnosis = findViewById<Button>(R.id.buttonSubmitDiagnosis)
        val loadingLayout = findViewById<LinearLayout>(R.id.loadingLayout)
        val buttonTakePhoto = findViewById<Button>(R.id.buttonTakePhoto)

        // 2. '온도 측정' 버튼 클릭 이벤트 처리
        buttonMeasureTemperature.setOnClickListener {
            val randomTemperature = Random.nextDouble(36.0, 40.0) // 36.0 이상 40.0 미만의 랜덤 실수 생성
            val formattedTemperature = String.format("%.2f", randomTemperature)
            textViewTemperatureValue.text = "현재 온도: $formattedTemperature °C"
        }
        // 3. '기록 제출' 버튼 클릭 이벤트 처리 (로딩 창을 보여주는 핵심 로직)
        buttonSubmitDiagnosis.setOnClickListener {
            loadingLayout.visibility = View.VISIBLE //로딩창 보이게 하기

            // 가상의 '분석' 작업을 위해 3초 (3000 밀리초) 동안 기다립니다.
            Handler(Looper.getMainLooper()).postDelayed({
                // 3초 후 로딩 창을 다시 숨깁니다.
                loadingLayout.visibility = View.GONE // 로딩창을 숨기기
                Toast.makeText(this@SelfDiagnosisActivity, "분석이 완료되었습니다!", Toast.LENGTH_SHORT)
                    .show()

                val intent = Intent(this@SelfDiagnosisActivity, AnalysisResultActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }
        buttonTakePhoto.setOnClickListener {
            val takePictureIntent =
                Intent(MediaStore.ACTION_IMAGE_CAPTURE) // 카메라 앱을 실행하라는 Intent 생성
            // 카메라 앱이 있는지 확인 (없으면 에러 발생 가능성)
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                takePictureLauncher.launch(takePictureIntent) // 카메라 앱 실행 및 결과 받기 준비
            } else {
                Toast.makeText(this, "카메라 앱을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}