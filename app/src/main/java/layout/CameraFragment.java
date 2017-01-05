package layout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;


import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;

import android.view.LayoutInflater;

import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import a21200800isec.cmcticket2.Assets.AsyncTaskCompleteListener;
import a21200800isec.cmcticket2.Assets.Tasks.PictureSaveTask;
import a21200800isec.cmcticket2.R;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

//TODO: Picture Saving Task, more testing on orientation and pic quality

@SuppressWarnings("deprecation")
public class CameraFragment extends Fragment implements SurfaceHolder.Callback,AsyncTaskCompleteListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    private String mParam1;
    private String mParam2;

    //components
    private SurfaceHolder mHolder;
    private Button btnPhoto;
    private SurfaceView surfaceView;
    private ProgressDialog progressDialog;

    //camera elements
    private Camera camera;
    private static int cameraID;
    private AsyncTaskCompleteListener mAsyncListener;







    private a21200800isec.cmcticket2.OnFragmentInteractionListener mListener;

    public CameraFragment() {
        // Required empty public constructor
        mAsyncListener = this;
    }


    public static CameraFragment newInstance(String param1, String param2) {
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //code


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_camera, container, false);
        //camera = getCameraInstance();
        btnPhoto = (Button)v.findViewById(R.id.btnPhoto);
        surfaceView = (SurfaceView)v.findViewById(R.id.surfaceView);
        //setMyPreviewSize();
        mHolder = surfaceView.getHolder();

        mHolder.addCallback(this);
        //mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.takePicture(null, null, mPicture);
                //Log.d("DEBUG", "CAMERA WIDTH: " + camera.getParameters().getPreviewSize().width + "\nCAMERA HEIGHT: " + camera.getParameters().getPreviewSize().height);

            }
        });
        Log.d("DEBUG", "------------------------------------------------->VIEW");
        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
           // mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        if(camera == null && mHolder!=null)
            camera = getCameraInstance();
        try {
            setCameraDisplayOrientation(getActivity(),cameraID);

            camera.setPreviewDisplay(mHolder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(cameraID); // attempt to get a Camera instance
        }
        catch (Exception e){
            Log.d("DEBUG", "Error accessing camera: " + e.getMessage());
        }
        Log.d("DEBUG", "CAMARA IS: " + c.toString());
        return c; // returns null if camera is unavailable
    }


    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Log.d("DEBUG", "HAS CAMERA: ");
            // this device has a camera
            return true;
        } else {
            Log.d("DEBUG", "NO CAMERA: ");
            // no camera on this device
            return false;
        }
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera cam) {
            progressDialog = ProgressDialog.show(getContext(), "SAVING", "", true, true);
            new PictureSaveTask(mAsyncListener).execute(data);
        }
    };

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        if(camera == null)
            camera = getCameraInstance();
        try {
            setCameraDisplayOrientation(getActivity(),cameraID);

            camera.setPreviewDisplay(mHolder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("DEBUG", "------------------------------------------------->CREATED"+ camera.toString());

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.d("DEBUG", "------------------------------------------------->CHANGED"+ camera.toString());
        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e){

            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        configCamera(i1,i2);
        // start preview with new settings
        try {
            camera.setPreviewDisplay(mHolder);
            camera.startPreview();

        } catch (Exception e){
            Log.d("DEBUG", "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        //releaseCamera();
    }




    private void releaseCamera(){
        if (camera != null){
            camera.release();        // release the camera for other applications

        }
        camera = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(camera!= null)
            Log.d("DEBUG", "------------------------------------------------->PAUSED"+ camera.toString());
        else
            Log.d("DEBUG", "------------------------------------------------->PAUSED");
        releaseCamera();
    }



    public void setCameraDisplayOrientation(Activity activity, int cameraId) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    public void configCamera(int width, int height){
        setCameraDisplayOrientation(getActivity(), cameraID);
        Camera.Parameters parameters=camera.getParameters();
        Camera.Size bestPreviewSize=getBestPreviewSize(width, height, parameters);
        Camera.Size bestPictureSize=getBestPictureSize(width, height, parameters);
        parameters.setPreviewSize(bestPreviewSize.width, bestPreviewSize.height);
        parameters.setPictureSize(bestPictureSize.width,bestPictureSize.height);
        List<String> supportedFocusModes = camera.getParameters().getSupportedFocusModes();
        boolean hasAutoFocus = supportedFocusModes != null && supportedFocusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO);
        if(hasAutoFocus)
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        camera.setParameters(parameters);
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseCamera();
    }

    private Camera.Size getBestPreviewSize(int width, int height,Camera.Parameters parameters) {
        Camera.Size result=null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result=size;
                }
                else {
                    int resultArea=result.width * result.height;
                    int newArea=size.width * size.height;

                    if (newArea > resultArea) {
                        result=size;
                    }
                }
            }
        }

        return(result);
    }

    private Camera.Size getBestPictureSize(int width, int height,Camera.Parameters parameters) {
        Camera.Size result=null;

        for (Camera.Size size : parameters.getSupportedPictureSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result=size;
                }
                else {
                    int resultArea=result.width * result.height;
                    int newArea=size.width * size.height;

                    if (newArea > resultArea) {
                        result=size;
                    }
                }
            }
        }

        return(result);
    }

    @Override
    public void onTaskComplete(boolean result) {
        if (progressDialog!=null && result){
            progressDialog.dismiss();

        }
    }
}
