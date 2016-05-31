package com.example.hellovisionworld;


import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.WindowManager;

public class HelloVisionActivity extends AppCompatActivity implements CvCameraViewListener2 {

    private static final String  TAG = "Ex::HelloVisionWorld";
    private CameraBridgeViewBase mOpenCvCameraView ;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };


    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, this, mLoaderCallback);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
       getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_hello_vision);
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.HelloVisionView);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hello_vision, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();

    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();

    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onCameraViewStopped() {
        // TODO Auto-generated method stub

    }

    @Override
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        Mat cameraFram=inputFrame.rgba();
        double [] pixelValue=cameraFram.get(0, 0);
        double blueChannelValue=pixelValue[0];
        double greenChannelValue=pixelValue[1];
        double redChannelValue=pixelValue[2];
        Log.i(TAG, "blue channel value: "+blueChannelValue);
        Log.i(TAG, "green channel value: "+greenChannelValue);
        Log.i(TAG, "red channel value: "+redChannelValue);
        return inputFrame.rgba();
    }
}
