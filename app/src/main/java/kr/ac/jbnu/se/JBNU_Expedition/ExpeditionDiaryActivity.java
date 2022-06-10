package kr.ac.jbnu.se.JBNU_Expedition;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ExpeditionDiaryActivity extends AppCompatActivity {

    private static final int NUM_OF_LOCATIONS = 7;                          // 탐험할 장소 개수

    private FrameLayout locFrames[] = new FrameLayout[NUM_OF_LOCATIONS];
    private ImageView locViews[] = new ImageView[NUM_OF_LOCATIONS];          // 각 장소에 대한 이미지뷰
    private Dialog dialogs[] = new Dialog[NUM_OF_LOCATIONS];                 // 각 장소에 대한 대화창
    private ImageView closeDialogBtn[] = new ImageView[NUM_OF_LOCATIONS];    // 각 장소에 대한 닫기 버튼
    private ImageView diaryBack;                // 뒤로 가기 버튼


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        // 지도 activity에서 방문한 기록 가져옴
        Intent intent = getIntent();
        String s = intent.getStringExtra("str");
        Log.d("ttt과연..인텐트로 잘 넘어왔나??", s);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.expedition_diary);

        // 각 장소 프레임 레이아웃 세팅
        locFrames[0] = (FrameLayout) findViewById(R.id.loc0_frame);
        locFrames[1] = (FrameLayout) findViewById(R.id.loc1_frame);
        locFrames[2] = (FrameLayout) findViewById(R.id.loc2_frame);
        locFrames[3] = (FrameLayout) findViewById(R.id.loc3_frame);
        locFrames[4] = (FrameLayout) findViewById(R.id.loc4_frame);
        locFrames[5] = (FrameLayout) findViewById(R.id.loc5_frame);
        locFrames[6] = (FrameLayout) findViewById(R.id.loc6_frame);

        // 처음에는 탐험한 곳이 없으므로 탐험일지에 모두 안보이는 상태에서 시작함
        for (int i=0; i<NUM_OF_LOCATIONS; i++) {
            locFrames[i].setVisibility(View.INVISIBLE);     // 보이지 않고 자리는 남음
            locFrames[i].setClickable(false);               // 터치도 안되게 해야지~
        }

        // Line31-34에서 받은 방문한 탐험장소를 탐험일지에 보이도록 함
        char isVisit[] = s.toCharArray();
        Log.d("ttt isVisit[]", String.valueOf(isVisit));
        for (int i=0; i<NUM_OF_LOCATIONS; i++) {
            if (isVisit[i] == '1') {
                locFrames[i].setVisibility(View.VISIBLE);   // 탐험일지에 방문한 장소 보이게!
                locFrames[i].setClickable(true);            // 터치도 되게 해야지~
            }
        }

        // 탐험 장소 이미지뷰
        locViews[0] = (ImageView) findViewById(R.id.loc0);
        locViews[1] = (ImageView) findViewById(R.id.loc1);
        locViews[2] = (ImageView) findViewById(R.id.loc2);
        locViews[3] = (ImageView) findViewById(R.id.loc3);
        locViews[4] = (ImageView) findViewById(R.id.loc4);
        locViews[5] = (ImageView) findViewById(R.id.loc5);
        locViews[6] = (ImageView) findViewById(R.id.loc6);

        // 탐험 장소에 대한 Dialog
        dialogs[0] = new Dialog(ExpeditionDiaryActivity.this);
        dialogs[1] = new Dialog(ExpeditionDiaryActivity.this);
        dialogs[2] = new Dialog(ExpeditionDiaryActivity.this);
        dialogs[3] = new Dialog(ExpeditionDiaryActivity.this);
        dialogs[4] = new Dialog(ExpeditionDiaryActivity.this);
        dialogs[5] = new Dialog(ExpeditionDiaryActivity.this);
        dialogs[6] = new Dialog(ExpeditionDiaryActivity.this);

        // xml 연결
        dialogs[0].setContentView(R.layout.location0);
        dialogs[1].setContentView(R.layout.location1);
        dialogs[2].setContentView(R.layout.location2);
        dialogs[3].setContentView(R.layout.location3);
        dialogs[4].setContentView(R.layout.location4);
        dialogs[5].setContentView(R.layout.location5);
        dialogs[6].setContentView(R.layout.location6);

        closeDialogBtn[0] = (ImageView) dialogs[0].findViewById(R.id.close_btn0);
        closeDialogBtn[1] = (ImageView) dialogs[1].findViewById(R.id.close_btn1);
        closeDialogBtn[2] = (ImageView) dialogs[2].findViewById(R.id.close_btn2);
        closeDialogBtn[3] = (ImageView) dialogs[3].findViewById(R.id.close_btn3);
        closeDialogBtn[4] = (ImageView) dialogs[4].findViewById(R.id.close_btn4);
        closeDialogBtn[5] = (ImageView) dialogs[5].findViewById(R.id.close_btn5);
        closeDialogBtn[6] = (ImageView) dialogs[6].findViewById(R.id.close_btn6);

        diaryBack = (ImageView) findViewById(R.id.diary_back);


        // 탐험일지 클릭 이벤트 지정
        for (int i = 0; i < NUM_OF_LOCATIONS; i++) {

            final int idx = i;
            locFrames[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 각 탐험장소 ImageView를 누르면 해당하는 Dialog 띄우기
                    dialogs[idx].show();
                    // Dialog 배경 투명!
                    dialogs[idx].getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogs[idx].setCancelable(false); // 영역 외 클릭시 창이 사라지지 않게 하기 위해
                }
            });
        }

        // 각 탐험일지 Dialog 닫기 버튼(X) 동작
        for (int i = 0; i < NUM_OF_LOCATIONS; i++) {

            final int idx = i;
            closeDialogBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogs[idx].dismiss();
                }
            });
        }

        // 탐험 일지 창에서 뒤로가기 버튼 누르면, 지도 화면으로 이동
        diaryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LocationTrackingActivity.class);
                startActivity(intent);
            }
        });

    }
}