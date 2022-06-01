package kr.ac.jbnu.se.JBNU_Expedition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    // 변수 선언
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private EditText signInEmail;
    private EditText signInPassword;
    private ImageButton signInBtn;
    private TextView signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        FirebaseApp.initializeApp(this);

        // 변수와 xml 파일의 id 연결
        firebaseAuth = FirebaseAuth.getInstance();

        signInEmail = (EditText) findViewById(R.id.sign_in_email);
        signInPassword = (EditText) findViewById(R.id.sign_in_password);

        signInBtn = (ImageButton) findViewById(R.id.sign_in_btn);
        signUpBtn = (TextView) findViewById(R.id.sign_up_btn);



        // 로그인 버튼: 공백 구별
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!signInEmail.getText().toString().equals("") && !signInPassword.getText().toString().equals("")) {
                    loginUser(signInEmail.getText().toString(), signInPassword.getText().toString());
                } else {
                    Toast.makeText(SignIn.this, "이메일과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // 회원가입 버튼
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });
    }

    // 로그인 버튼: 성공 및 실패
    public void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Toast.makeText(SignIn.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            firebaseAuth.addAuthStateListener(firebaseAuthListener);
                            Intent intent = new Intent(getApplicationContext(), Map.class);
                            startActivity(intent);

                        } else {
                            // 로그인 실패
                            Toast.makeText(SignIn.this, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // 파이어베이스 실행, 중지
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

}