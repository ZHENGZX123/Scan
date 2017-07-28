package scan.scanmoudle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class ScanActivity extends Activity implements QRCodeView.Delegate, Callback {

    private QRCodeView mQRCodeView;
    private ProgressDialog progressDialog;
    private App app;
    private String uploadScanMessageUrl = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App) getApplicationContext();
        setContentView(R.layout.activity_scan);
        try {
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void initView() throws Exception {
        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
        mQRCodeView.setDelegate(this);
        mQRCodeView.changeToScanQRCodeStyle();
        progressDialog = ProgressDialog.show(this, "已扫描到二维码", "正在上传中");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();
        mQRCodeView.startSpot();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        mQRCodeView.stopSpot();
        Request request = new Request.Builder()
                .url(uploadScanMessageUrl)
                .build();
        Call call = app.okHttpClient.newCall(request);
        call.enqueue(this);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e("-----", "打开相机出错");
    }


    @Override
    public void onFailure(Call call, IOException e) {
        mQRCodeView.startSpot();
        progressDialog.dismiss();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        mQRCodeView.startSpot();
        progressDialog.dismiss();
        if (call.request().url().toString().equals(uploadScanMessageUrl)) {
        }
    }
}