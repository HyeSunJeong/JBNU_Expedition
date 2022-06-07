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

// TODO (혜선)
// ImageView가 아닌 location 탐험 UI를 탐험하지 않았다면, 보이지 않게, 탐험 했다면 보이게 설정하기
// Dialog 창에서 X ImageView를 누르면 Dialog 창 닫게 만들기


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

        // Dialog 창 닫기
        // 레이아웃을 형상화 시키기 위해 View 아래에 Inflate를 사용한다.
        // Inflate는 풍선을 불어넣다 = 형상화 시켜주는 아이. 레이아웃에 실체를 부여해주는 아이
        // setContentView는 이 과정이 생략되어 있기 때문에 안보였던거지 원래는 다 이 과정을 거침
        View dialog0=getLayoutInflater().inflate(R.layout.location0,null);
        View dialog1=getLayoutInflater().inflate(R.layout.location1,null);
        View dialog2=getLayoutInflater().inflate(R.layout.location2,null);
        View dialog3=getLayoutInflater().inflate(R.layout.location3,null);
        View dialog4=getLayoutInflater().inflate(R.layout.location4,null);
        View dialog5=getLayoutInflater().inflate(R.layout.location5,null);
        View dialog6=getLayoutInflater().inflate(R.layout.location6,null);


        closeDialogBtn[0] = (ImageView) dialog0.findViewById(R.id.close_btn0);
        closeDialogBtn[1] = (ImageView) dialog1.findViewById(R.id.close_btn1);
        closeDialogBtn[2] = (ImageView) dialog2.findViewById(R.id.close_btn2);
        closeDialogBtn[3] = (ImageView) dialog3.findViewById(R.id.close_btn3);
        closeDialogBtn[4] = (ImageView) dialog4.findViewById(R.id.close_btn4);
        closeDialogBtn[5] = (ImageView) dialog5.findViewById(R.id.close_btn5);
        closeDialogBtn[6] = (ImageView) dialog6.findViewById(R.id.close_btn6);

        // Dialog 선언하기
        for (int i=0; i<NUM_OF_LOCATIONS; i++) {
            final int idx = i;


            // 각 ImageView를 누르면 해당하는 Dialog 띄우기
            locViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogs[idx].show();
                    // Dialog 배경 투명!
                    dialogs[idx].getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            });
        }


        // Dialog 닫기 버튼 동작 등록
//        closeDialogBtn[i].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                switch ()
//            }
//        });


//        closeDialogBtn[idx].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialogs[idx].dismiss();
//            }
//        });


        // 탐험 일지 창에서 뒤로가기 버튼 누르면, 지도 화면으로 이동
        diaryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LocationTrackingActivity.class);
                startActivity(intent);
            }
        });



        // Dialog 배경 투명!
        // location0.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // TODO 주의사항: findViewById()를 쓸 때는 앞에 반드시 다이얼로그 붙이기!
    }
}