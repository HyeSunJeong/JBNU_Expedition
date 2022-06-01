package kr.ac.jbnu.se.JBNU_Expedition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import androidx.annotation.Nullable;

public class SignUp extends AppCompatActivity {

    private ImageButton joinBtn;
    private ImageView signUpBack;

    private EditText signUpId;
    private EditText signUpEmail;
    private EditText signUpPassword;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        // 변수와 xml 파일의 id 연결
        joinBtn = (ImageButton) findViewById(R.id.join_btn);
        signUpBack = (ImageView) findViewById(R.id.sign_up_back);

        signUpId = (EditText) findViewById(R.id.sign_up_id);
        signUpEmail = (EditText) findViewById(R.id.sign_up_email);
        signUpPassword = (EditText) findViewById(R.id.sign_up_password);

        firebaseAuth = FirebaseAuth.getInstance();

        // 회원가입: 공백 구별
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!signUpEmail.getText().toString().equals("") && !signUpPassword.getText().toString().equals("")) {
                    // 이메일과 비밀번호가 공백이 아닌 경우
                    createUser(signUpEmail.getText().toString(), signUpPassword.getText().toString(), signUpId.getText().toString());
                } else {
                    // 이메일과 비밀번호가 공백인 경우
                    Toast.makeText(SignUp.this, "이메일과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // 회원 가입 창에서 뒤로가기 버튼 누르면, 로그인 화면으로 이동
        signUpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Map.class);
                startActivity(intent);
            }
        });
    }

    // 회원가입: 성공 및 실패
    private void createUser(String email, String password, String name) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공시
                            Toast.makeText(SignUp.this, name + "님, 환영합니다!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), SignIn.class);
                            startActivity(intent);
                        } else {
                            // 계정이 중복된 경우
                            Toast.makeText(SignUp.this, "이미 등록된 계정입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}