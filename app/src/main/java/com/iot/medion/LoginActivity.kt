package com.iot.medion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText // EditText 사용을 위해 이 줄을 추가합니다.
import android.widget.Toast // Toast 사용을 위해 이 줄을 추가합니다.
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    private val MASTER_ID = "master"
    private val MASTER_PW = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 1. 필요한 UI 요소들을 찾아옵니다.
        val buttonSignUp = findViewById<Button>(R.id.buttonSignUp) // 회원가입 버튼
        //XML의 "Login\n로그인" 버튼 ID에 맞춰 'buttonLogin' 변수를 연결합니다.
        val buttonLogin = findViewById<Button>(R.id.buttonLogin2)   // LoginActivity에서 로그인 기능을 담당할 버튼.
        // XML에 buttonLogin2 라는 ID가 있으니 이걸 사용합니다.
        //XML에 있는 실제 아이디 입력 EditText의 ID에 맞춰 변수를 연결합니다.
        val editTextId = findViewById<EditText>(R.id.editTextTextEmailAddress2)
        // XML에 있는 실제 비밀번호 입력 EditText의 ID에 맞춰 변수를 연결합니다.
        val editTextPassword = findViewById<EditText>(R.id.editTextTextPassword2)


        // --- 클릭 리스너 설정 ---
        // (기존 코드) 회원가입 버튼 클릭 시
        buttonSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }

        //'로그인' 버튼 (이제 buttonLogin 변수가 XML의 buttonLogin2를 가리킵니다) 클릭 시 유효성 검사 로직
        buttonLogin.setOnClickListener { // buttonLogin 변수에 할당된 (ID: buttonLogin2) 버튼에 리스너를 붙입니다.
            val inputId = editTextId.text.toString()
            val inputPw = editTextPassword.text.toString()

            // 1. 입력 필드가 비어있는지 먼저 확인
            if (inputId.isEmpty()) {
                Toast.makeText(this@LoginActivity, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (inputPw.isEmpty()) {
                Toast.makeText(this@LoginActivity, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 2. 마스터 아이디와 비밀번호가 일치하는지 확인
            if (inputId == MASTER_ID && inputPw == MASTER_PW) {
                Toast.makeText(this@LoginActivity, "마스터 로그인 성공!", Toast.LENGTH_SHORT).show()

                // 로그인 성공 후 자가진단 화면으로 이동
                val successIntent = Intent(this@LoginActivity, SelfDiagnosisActivity::class.java)
                startActivity(successIntent)
                finish() // 로그인 화면은 뒤로 가기 눌러도 다시 보이지 않도록 종료
            } else {
                Toast.makeText(this@LoginActivity, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}