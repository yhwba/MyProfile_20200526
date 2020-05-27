package kr.co.yhw.myprofile_20200526;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

import kr.co.yhw.myprofile_20200526.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {


        binding.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PermissionListener pl = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
//                        허가 된 상황 => 실제 전화 걸기
                        //            실제 전화걸기 => 권한 허가가x , 앱이 강제로 종료
                        //           => TedPermission 권한 획득후 실행 할 예정
                        String phoneNum = binding.phoneNumTxt.getText().toString();
                        Uri myUri = Uri.parse(String.format("tel:%s", phoneNum));
                        Intent myIntent = new Intent(Intent.ACTION_CALL, myUri);
                        startActivity(myIntent);
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
//                        최종적으로 거부되었을때 처리할 내용
                        Toast.makeText(mContext, "권한이 거부되어 통화가 불가능합니다.", Toast.LENGTH_SHORT).show();
                    }
                };
//               권한 x =>얼럿으로 허용할지?
//               권한 이전허용 => 곧바로 Granted 실행
                TedPermission
                        .with(mContext)
                        .setPermissionListener(pl)
                        .setDeniedMessage("거부하면 통화가 불가능합니다. \n 설정에서 권한을 설정해주세요.")
                        .setPermissions(Manifest.permission.CALL_PHONE)
                        .check();


            }
        });

        binding.profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, PhotoViewActivity.class);
                intent.putExtra("imgUrl", "https://pbs.twimg.com/profile_images/1166183517060943872/UJlK2ff4_400x400.jpg");
                startActivity(intent);

            }
        });

    }

    @Override
    public void setValues() {
//        인터넷에 있는 이미지 불러오기 => 인터넷 연결 권한 필요 : 주의사항 참고
        Glide.with(mContext).load("https://pbs.twimg.com/profile_images/1166183517060943872/UJlK2ff4_400x400.jpg").into(binding.profileImg);

    }
}
