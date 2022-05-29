package kr.ac.jbnu.se.JBNU_Expedition;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.naver.maps.map.MapView;


public class MainActivity extends AppCompatActivity {
    private MapView mapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);

    }}