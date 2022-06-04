package kr.ac.jbnu.se.JBNU_Expedition;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


public class LocationTrackingActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;         // 지자기, 가속도 센서를 활용해 최적의 위치를 반환 제공
    private NaverMap naverMap;

    private static final int NUM_OF_LOCATIONS = 7;      // 탐험할 장소 개수

    private ImageView diaryBtn;     // 탐험 일지 화면으로 가는 버튼 (이미지뷰)

    // jbnu_locations.json 데이터를 저장할 자료구조
    private static Map<String, Double[]> jbnuLocInfo = new LinkedHashMap<>();

    Double latitude[] = new Double[NUM_OF_LOCATIONS];
    Double longitude[] = new Double[NUM_OF_LOCATIONS];

    Set<String> visitLocations = new HashSet<>();
    boolean isVisited[] = new boolean[NUM_OF_LOCATIONS];    // 방문(탐험) 여부 저장



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_tracking);

        // 탐험 일지 화면으로 가는 버튼 (이미지뷰)
        diaryBtn = (ImageView) findViewById(R.id.diary_btn);
        diaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationTrackingActivity.this, ExpeditionDiaryActivity.class);
                startActivity(intent);

            }
        });

//        // 탐험 일지 화면에서 각 이미지뷰 초기 세팅
//        for (int i=0; i<NUM_OF_LOCATIONS; i++) {
//            locViews[0] = (ImageView) findViewById(R.id.loc0);
//            locViews[i].setVisibility(View.INVISIBLE);    // 아직 탐험(방문)하지 않았으면 안보이도록
//            locViews[i].setClickable(false);              // 아직 탐험(방문)하지 않았으면 클릭할 수 없도록
//        }



        // 지도 객체 생성
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        // getMapAsync를 호출하여 비동기로 onMapReady 콜백 메서드 호출
        // onMapReady에서 NaverMap 객체를 받음
        mapFragment.getMapAsync(this);
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        // parse JSON
        String jsonStr = getJsonStr("jsons/jbnu_locations.json");
        parseJson(jsonStr);
    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource); // 현재 위치
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);

        // 지도 상에 마커 표시
        Marker markers[] = new Marker[NUM_OF_LOCATIONS];    // 각 위치에 표시할 마커 객체 배열
        String locNames[] = jbnuLocInfo.keySet().toArray(new String[0]);

        for (int i = 0; i < NUM_OF_LOCATIONS; i++) {
            Double latlon[] = jbnuLocInfo.get(locNames[i]);
            latitude[i] = latlon[0];
            longitude[i] = latlon[1];
        }

        for (int i = 0; i < NUM_OF_LOCATIONS; i++) {
            markers[i] = new Marker();
            setMarker(markers[i], latitude[i], longitude[i]);
            markers[i].setCaptionText(locNames[i]);
        }
        // 모든 마커 비활성화
        for (int i=0; i< NUM_OF_LOCATIONS; i++) {
            markers[i].setMap(null);
        }


        // 현재 위치 좌표 (위도,경도) 가져오기
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.d("tttt", lm.toString());
        Criteria criteria = new Criteria();
        String provider = lm.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location lastLocation = lm.getLastKnownLocation(provider);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lastLocation = lm.getLastKnownLocation(locationProvider);
        Log.d("tttt", lastLocation.toString());

        double curLat = 0.0;        // 사용자 현재 위치 위도
        double curLon = 0.0;        // 사용자 현재 위치 경도
        curLat = lastLocation.getLatitude();
        curLon = lastLocation.getLongitude();
        Log.d("현재 위치 좌표 ", String.valueOf(curLat) + String.valueOf(curLon));
        String test = getClosestLocation(curLat, curLon);
        Log.d("현재 위치와 가장 가까운 장소는? ", test);

        naverMap.addOnLocationChangeListener(this::saveVisitLocation);  // 현재 위치가 변경될 때마다 위치 저장.

        // 사용자 위치 변경에 대한 이벤트 리스너
        // 방문한 장소의 마커만 표시함 !!
        // 방문한 장소 (현재 위치와 등록된 장소를 비교해서 일정 이상 가까이 가면 방문한 것으로 함) 마커만 띄우기..
        naverMap.addOnLocationChangeListener(location -> {
            String s = getClosestLocation(location.getLatitude(), location.getLongitude()); // 현재 위치와 가장 가까운 장소를 구함
            Log.d("tttt", s);
            int idx = Arrays.binarySearch(locNames, s); // 장소 인덱스를 구함
            Log.d("ttt", String.valueOf(idx));
            setMarker(markers[idx], latitude[idx], longitude[idx]);     // 마커를 세팅!


            /// 방문(탐험) 했으면 -> 탐험 일지에 해당 장소의 ImageView를 visible하게 변경 & 해당 ImageView의 onClick 활성화 시킴 (또는 clickable 속성 변경)
            isVisited[idx] = true;
            setVisible(true);

//            locViews[idx].setVisibility(View.VISIBLE);  // 방문한 ImageView를 visible하게 변경
//            locViews[idx].setClickable(true);           // 방문한 ImageView를 클릭(터치)가능하도록 변경
            // Log.d("방문 여부", String.valueOf(isVisited[idx]));



            Log.d("tt", markers[idx].toString() + latitude[idx].toString() + longitude[idx].toString());
        });


        // UI
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);  // 현재 위치 버튼 표시
        uiSettings.setCompassEnabled(true);         // 나침반 표시
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // request code와 권한획득 여부 확인
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        }
    }


    // marker 커스텀 세팅 함수 (위치, 크기, 이미지)
    // @param   marker                  설정을 적용할 marker 인스턴스
    // @param   latitude, longtiude     위도, 경도
    private void setMarker(Marker marker, double latitude, double longitude) {
        marker.setPosition(new LatLng(latitude, longitude));
        marker.setMap(naverMap);
        marker.setWidth(100);
        marker.setHeight(100);
        marker.setIcon(OverlayImage.fromResource(R.drawable.diary_map));
        marker.setIconPerspectiveEnabled(true); // 마커에 원근 효과 적용
        marker.setHideCollidedMarkers(true);    // 다른 마커와 겹칠 경우 마커가 숨겨짐
        marker.setCaptionColor(Color.rgb(255,139,139));     // 캡션 색 지정
//        marker.setCaptionMinZoom(12);   // 해당 줌 레벨 "이상" 에서만 캡션이 나타남
//        marker.setCaptionMaxZoom(16);   // 해당 줌 레벨 "이하" 에서만 캡션이 나타남
    }


    // JSON file --> string
    private String getJsonStr(String fileName)
    {
        String jsonStr = "";

        try {
            InputStream is = getAssets().open(fileName);
            int fileSize = is.available();
            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();
            jsonStr = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }


    // parse JSON --> Map<String, [latitude, longitude]>
    private void parseJson(String jsonStr) {

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray locArray = jsonObject.getJSONArray("jbnu_locations");

            for (int i=0; i<locArray.length(); i++) {
                JSONObject locObj = locArray.getJSONObject(i);  // 배열의 요소(객체)를 하나씩 가져옴..
                String name = locObj.getString("name");
                Double latitude = locObj.getDouble("latitude");
                Double longitude = locObj.getDouble("longitude");
                Double loc[] = new Double[2];
                loc[0] = latitude;
                loc[1] = longitude;
                jbnuLocInfo.put(name, loc); // Map<String, [latitude, longitude]>
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // 현재 위치와 지정된 장소의 위치값을 비교하여 가장 가까운 위치를 반환
    private String getClosestLocation(double curLatitude, double curLongitude) {
        Double LatDist[] = new Double[NUM_OF_LOCATIONS];    // 위도 차이 저장
        Double LonDist[] = new Double[NUM_OF_LOCATIONS];    // 경도 차이 저장
        Double metric[] = new Double[NUM_OF_LOCATIONS];     // 가중치 값 저장

        // 거리 차이 가중치 곱해서 구하기
        for (int i=0; i<NUM_OF_LOCATIONS; i++) {
            LatDist[i] = Math.abs(curLatitude - latitude[i]);
            LonDist[i] = Math.abs(curLongitude - longitude[i]);
            metric[i] = LatDist[i] * 0.5 + LonDist[i] * 0.5;    // 가중치는 0.5로 반반씩
        }

        double minMetric = metric[0];
        int minIndex = 0;

       // 가장 가까운 장소의 인덱스 구하기
        for (int i=0; i<NUM_OF_LOCATIONS; i++) {
            if (metric[i] < minMetric) {
                minMetric = metric[i];
                minIndex = i;
            }
        }

        String locNames[] = jbnuLocInfo.keySet().toArray(new String[0]);
        String cloestLoc = locNames[minIndex];
        Log.d("ttt", cloestLoc);

        return cloestLoc;
    }


    private void saveVisitLocation(Location location) {

        double lat = Math.round(location.getLatitude() * 10000) / 10000.0;    // 위도 -> 소수점 아래 5자리에서 반올림
        double lon = Math.round(location.getLongitude() * 1000) / 1000.0;     // 경도 -> 소수점 아래 4자리에서 반올림
        String currentLoc = String.valueOf(lat) + "@" + String.valueOf(lon);
        Log.d("location test", currentLoc);

        visitLocations.add(currentLoc);  // 방문한 위치 좌표 저장 Set<double, double>
        Log.d("HashSet<String> test ", visitLocations.toString());
    }

}
