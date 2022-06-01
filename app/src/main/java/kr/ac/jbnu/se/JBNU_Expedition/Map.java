package kr.ac.jbnu.se.JBNU_Expedition;

// 기본 import
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

// compat, Fragment import
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

// 지도 위치, 좌표, 마커 import
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

public class Map extends AppCompatActivity implements OnMapReadyCallback {
    // 위치 Permission 정의
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;

    // MainActivity onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) { // 왜 여기서 protected 사용했지?
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        // 지도 객체 생성
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) { // if문 부분 이해 안됨
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        // getMapAsync를 호출하여 비동기로 onMapReady 콜백 메서드 호출
        // onMapReady에서 NaverMap 객체를 받음
        mapFragment.getMapAsync(this); // 비동기가 뭐지? 비동기로 하는 이유는?

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE); // 이 부분도 모르겠어

        // Marker
        Marker marker = new Marker();
        marker.setPosition(new LatLng(37.5670135, 126.9783740)); // 위도, 경도 값 설정
        marker.setMap(naverMap); // naverMap에 마커를 생성한다.

    }

    // onMapReady
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource); naverMap.setLocationTrackingMode (LocationTrackingMode.Follow);
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);

        // 위치 변경 이벤트
        naverMap.addOnLocationChangeListener(location -> // -> 이게 뭐지? 아래랑 연결되네!

                // TODO
                // 이 투두는 task tag 기능으로 여느 주석과 달리 잘 보이게 만들어준다.
                // 근처 위치에 가면 지도에 마커 띄우기.
                // 현재 위도/경도와 json파일에 있는 장소 목록과 비교하여 있으면 마커를 띄움

                // 토스트 띄우기 (테스트)
                Toast.makeText(this,
                        location.getLatitude() + ", " + location.getLongitude(), // Latitude: 위도, Longitude: 경도
                        Toast.LENGTH_SHORT).show());

        UiSettings uiSettings = naverMap.getUiSettings(); //Ui Settings는 네이버에서 제공하는 기능인가?
        uiSettings.setLocationButtonEnabled(true);

    }

    // 위치 정보 permission
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
}
