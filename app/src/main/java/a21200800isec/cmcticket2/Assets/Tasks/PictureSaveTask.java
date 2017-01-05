package a21200800isec.cmcticket2.Assets.Tasks;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import a21200800isec.cmcticket2.Assets.AsyncTaskCompleteListener;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

/**
 * Created by red_f on 05/01/2017.
 */

public class PictureSaveTask extends AsyncTask<byte[], String, Boolean> {
    private static final int MEDIA_TYPE_IMAGE = 1;
    private AsyncTaskCompleteListener mListener;

    public PictureSaveTask(AsyncTaskCompleteListener l){
        mListener = l;
    }
    @Override
    protected Boolean doInBackground(byte[]... bytes) {
        File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (pictureFile == null){
            Log.d("DEBUG", "Error creating media file, check storage permissions: ");
            return false;
        }

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(bytes[0],0,bytes.length);
            fos.close();
            return true;

        } catch (FileNotFoundException e) {
            Log.d("DEBUG", "File not found: " + e.getMessage());
            return false;
        } catch (IOException e) {
            Log.d("DEBUG", "Error accessing file: " + e.getMessage());
            return false;
        }

    }

    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CMCTicket");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("CMCTicket", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");

        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        mListener.onTaskComplete(result);
    }
}
