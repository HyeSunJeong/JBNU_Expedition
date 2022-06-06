package kr.ac.jbnu.se.JBNU_Expedition;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class ExpeditionDiaryActivity extends AppCompatActivity {

    private static final int NUM_OF_LOCATIONS = 7;      // 탐험할 장소 개수
    public ImageView locViews[] = new ImageView[NUM_OF_LOCATIONS];  // 각 장소에 대한 이미지뷰
    public Dialog dialogs[] = new Dialog[NUM_OF_LOCATIONS];         // 각 장소에 대한 대화창
    public static Context context_expDiary;     // ImageView 다른 클래스에서 사용하기 위함
    public ImageView closeDialogBtn[] = new ImageView[NUM_OF_LOCATIONS]; // 각 장소에 대한 닫기 버튼

    private ImageView diaryBack;                // 뒤로 가기 버튼

    // Dialog location0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expedition_diary);
        context_expDiary = this;      // ImageView 다른 클래스에서 사용하기 위함
        diaryBack = (ImageView) findViewById(R.id.diary_back);


        // 각 이미지뷰 초기 세팅
        locViews[0] = (ImageView) findViewById(R.id.loc0);
        locViews[1] = (ImageView) findViewById(R.id.loc1);
        locViews[2] = (ImageView) findViewById(R.id.loc2);
        locViews[3] = (ImageView) findViewById(R.id.loc3);
        locViews[4] = (ImageView) findViewById(R.id.loc4);
        locViews[5] = (ImageView) findViewById(R.id.loc5);
        locViews[6] = (ImageView) findViewById(R.id.loc6);

        //for (int i=0; i<NUM_OF_LOCATIONS; i++) {
        //    locViews[i].setVisibility(View.INVISIBLE);    // 아직 탐험(방문)하지 않았으면 안보이도록
        //    locViews[i].setClickable(false);              // 아직 탐험(방문)하지 않았으면 클릭할 수 없도록
        //}

        // Dialog
        dialogs[0] = new Dialog(ExpeditionDiaryActivity.this); // Dialog 초기화
        dialogs[0].setContentView(R.layout.location0); // xml 연결

        // 탐험 일지 창에서 뒤로가기 버튼 누르면, 지도 화면으로 이동
        diaryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LocationTrackingActivity.class);
                startActivity(intent);
            }
        });

        // 각 ImageView를 누르면 해당하는 Dialog 띄우기
        for (int i=0; i<NUM_OF_LOCATIONS; i++) {
            final int idx = i;
            locViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogs[idx].show();
                    // Dialog 배경 투명!
                    dialogs[idx].getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            });
        }

        // 닫기 버튼
        closeDialogBtn[0] = (ImageView) findViewById(R.id.close_btn);
        closeDialogBtn[1] = (ImageView) findViewById(R.id.close_btn);
        closeDialogBtn[2] = (ImageView) findViewById(R.id.close_btn);
        closeDialogBtn[3] = (ImageView) findViewById(R.id.close_btn);
        closeDialogBtn[4] = (ImageView) findViewById(R.id.close_btn);
        closeDialogBtn[5] = (ImageView) findViewById(R.id.close_btn);
        closeDialogBtn[6] = (ImageView) findViewById(R.id.close_btn);

        // Dialog 배경 투명!
        // location0.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // TODO 주의사항: findViewById()를 쓸 때는 앞에 반드시 다이얼로그 붙이기!


        // Dialog 닫기 버튼 동작 등록

        for (int i=0; i<NUM_OF_LOCATIONS; i++) {
            final int idx = i;
            closeDialogBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogs[idx].dismiss();
                }
            });
        }
    }
}