package kr.ac.jbnu.se.JBNU_Expedition;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;

// TODO (혜선)
// ImageView가 아닌 location 탐험 UI를 탐험하지 않았다면, 보이지 않게, 탐험 했다면 보이게 설정하기
// Dialog 창에서 X ImageView를 누르면 Dialog 창 닫게 만들기


public class ExpeditionDiaryActivity extends AppCompatActivity {

    public static Context context_expDiary;     // ImageView 다른 클래스에서 사용하기 위함

    private static final int NUM_OF_LOCATIONS = 7;                          // 탐험할 장소 개수
    public ImageView locViews[] = new ImageView[NUM_OF_LOCATIONS];          // 각 장소에 대한 이미지뷰
    public Dialog dialogs[] = new Dialog[NUM_OF_LOCATIONS];                 // 각 장소에 대한 대화창
    public ImageView closeDialogBtn[] = new ImageView[NUM_OF_LOCATIONS];    // 각 장소에 대한 닫기 버튼

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

        // Dialog 초기화
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

        // 아직 탐험(방문)하지 않은 곳은 안보이도록 너굴맨이 처리했다구!
        for (int i=0; i<NUM_OF_LOCATIONS; i++) {
            // locViews[i].setVisibility(View.INVISIBLE);    // 아직 탐험(방문)하지 않았으면 안보이도록
            locViews[i].setClickable(false);              // 아직 탐험(방문)하지 않았으면 클릭할 수 없도록
        }

        closeDialogBtn[0] = (ImageView) dialogs[0].findViewById(R.id.close_btn0);
        closeDialogBtn[1] = (ImageView) dialogs[1].findViewById(R.id.close_btn1);
        closeDialogBtn[2] = (ImageView) dialogs[2].findViewById(R.id.close_btn2);
        closeDialogBtn[3] = (ImageView) dialogs[3].findViewById(R.id.close_btn3);
        closeDialogBtn[4] = (ImageView) dialogs[4].findViewById(R.id.close_btn4);
        closeDialogBtn[5] = (ImageView) dialogs[5].findViewById(R.id.close_btn5);
        closeDialogBtn[6] = (ImageView) dialogs[6].findViewById(R.id.close_btn6);

        // 탐험일지 클릭 이벤트 지정
        for (int i=0; i<NUM_OF_LOCATIONS; i++) {

            final int idx = i;
            locViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 각 탐험장소 ImageView를 누르면 해당하는 Dialog 띄우기
                    dialogs[idx].show();
                    // Dialog 배경 투명!
                    dialogs[idx].getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            });
        }

        // 각 탐험일지 Dialog 닫기 버튼(X) 동작
        for (int i=0; i<NUM_OF_LOCATIONS; i++) {

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