package kr.ac.jbnu.se.JBNU_Expedition;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ExpeditionDiaryActivity extends AppCompatActivity {

    private ImageView diaryBack;
    ImageView loc0;
    Dialog location0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expedition_diary);

        // id 연결
        diaryBack = (ImageView) findViewById(R.id.diary_back);
        loc0 = (ImageView) findViewById(R.id.loc0);

        // Dialog
        location0 = new Dialog(ExpeditionDiaryActivity.this); // Dialog 초기화
        location0.setContentView(R.layout.location0); // xml 연결

        // 탐험 일지 창에서 뒤로가기 버튼 누르면, 지도 화면으로 이동
        diaryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LocationTrackingActivity.class);
                startActivity(intent);
            }
        });

        // expedition_diary.xml에 있는 loc1 버튼 누르면 Dialog 띄우기
        loc0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 아래 함수 호출
                showLocation0();
            }
        });
    }

    // 바깥에 하는 이유: callback 함수가 아닌 새로운 함수를 선언했기 때문
    // Dialog 함수 (행동)
    public void showLocation0 () {
        location0.show(); // 다이얼로그 띄우기

        // Dialog 배경 투명!
        location0.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // TODO 주의사항: findViewById()를 쓸 때는 앞에 반드시 다이얼로그 붙이기!

        // Dialog 닫기
        ImageView loc0Back = location0.findViewById(R.id.loc0_close);
        loc0Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                location0.dismiss(); // Dialog 닫기
            }
        });
    }
}