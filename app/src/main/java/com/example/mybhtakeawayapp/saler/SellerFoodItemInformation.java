package com.example.mybhtakeawayapp.saler;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mybhtakeawayapp.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class SellerFoodItemInformation extends Activity {
    ImageView imageview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_food_item_information);

        init();
        if (savedInstanceState != null) mPath = savedInstanceState.getString("Path");
    }


    private TextView mTxtPath;
    private ImageView mImg;
    private CheckBox mCb;

    private String mPath;
    private boolean isTakePhoto = false;
    private boolean isGetPic = false;

    private static final int PERMISSIONS_REQUEST_PHOTO = 0x01;
    private static final int PERMISSIONS_REQUEST_FILE = 0x02;

    private static final int REQUEST_CODE_TAKING_PHOTO = 0x03;
    private static final int REQUEST_CODE_SELECT_PHOTO_FROM_LOCAL = 0x04;
    private static final int REQUEST_CODE_CUT_PHOTO = 0x05;

    private static final int TARGET_HEAD_SIZE = 150;
    private static final String IMAGE_SAVE_DIR = Environment.getExternalStorageDirectory().getPath() + "/yulin/photo";
    private static final String IMAGE_SAVE_PATH = IMAGE_SAVE_DIR + "/demo.jpg";

    private Uri mUri;

    private CharSequence[] mItems = {"????????????", "????????????"};

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Path", mPath);
    }


    private void init() {
        mImg = (ImageView) this.findViewById(R.id.phout);
        mImg.setOnClickListener(v -> showPhotoDialog());
        //initValue();
        File file = new File(IMAGE_SAVE_DIR);
        if (!file.exists()) file.mkdirs();

    }

    /**
     * ???????????????????????????
     */
    private void showPhotoDialog() {
        new AlertDialog.Builder(this)
                .setTitle("??????")
                .setItems(mItems, (dialog, which) -> {
                    if (which == 0) operTakePhoto();
                    else if (which == 1) operChoosePic();
                }).create().show();
    }

    /**
     * ????????????
     */
    private void operTakePhoto() {
        isTakePhoto = true;
        isGetPic = false;
        if (ContextCompat.checkSelfPermission(SellerFoodItemInformation.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SellerFoodItemInformation.this, Manifest.permission.CAMERA))
                showPhotoPerDialog();
            else
                ActivityCompat.requestPermissions(SellerFoodItemInformation.this, new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_PHOTO);
        } else takePhoto();
    }

    /**
     * ??????????????????
     */
    private void operChoosePic() {
        isTakePhoto = false;
        isGetPic = true;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                showFilePerDialog();
            else
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_FILE);
        } else getPictureFromLocal();
    }

    /**
     * ??????????????????
     */
    private void showPhotoPerDialog() {
        new AlertDialog.Builder(this)
                .setTitle("??????")
                .setMessage("PhotoDemo????????????????????????????????????????????????????????????????????????")
                .setPositiveButton("??????", (dialog, which) -> ActivityCompat.requestPermissions(SellerFoodItemInformation.this, new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_PHOTO)).create().show();
    }

    /**
     * ??????????????????
     */
    private void showFilePerDialog() {
        new AlertDialog.Builder(this)
                .setTitle("??????")
                .setMessage("PhotoDemo???????????????????????????????????????????????????????????????????????????????????????")
                .setPositiveButton("??????", (dialog, which) -> ActivityCompat.requestPermissions(SellerFoodItemInformation.this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, PERMISSIONS_REQUEST_FILE)).create().show();
    }

    /**
     * ??????
     */
    private void takePhoto() {
        String mUUID = UUID.randomUUID().toString();
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        mPath = FileUtils.getStorageDirectory() + mUUID;
        File file = new File(mPath + ".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, REQUEST_CODE_TAKING_PHOTO);
    }

    /**
     * ?????????????????????
     */
    private void getPictureFromLocal() {
        Intent innerIntent =
                new Intent(Intent.ACTION_GET_CONTENT);
        innerIntent.setType("image/*");
        Intent wrapperIntent = Intent.createChooser(innerIntent, null);
        startActivityForResult(wrapperIntent, REQUEST_CODE_SELECT_PHOTO_FROM_LOCAL);
    }

    /**
     * ????????????
     */
    private void startPhotoZoom(Uri uri, int size) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            // crop???true?????????????????????intent??????????????????view????????????
            intent.putExtra("crop", "true");
            // aspectX aspectY ??????????????????
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);


            // outputX,outputY ????????????????????????
            intent.putExtra("outputX", size);
            intent.putExtra("outputY", size);
            //   intent.putExtra("return-data", true);
            mUri = Uri.parse("file:///" + IMAGE_SAVE_PATH);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(intent, REQUEST_CODE_CUT_PHOTO);
        } catch (ActivityNotFoundException e) {
            String errorMessage = "Your device doesn't support the crop action!";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ??????????????????
     */
    private void dealTakePhotoWithoutZoom() {
        String finalPath = mPath + ".jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(finalPath);
        if (bitmap != null) {
            boolean b = BitmapUtils.compressBitmap2file(bitmap, IMAGE_SAVE_PATH);
            if (b) {
                mImg.setImageBitmap(BitmapFactory.decodeFile(IMAGE_SAVE_PATH));
                mTxtPath.setText("???????????????" + IMAGE_SAVE_PATH);
            }
        }
    }

    /**
     * ?????????????????????
     */
    private void dealTakePhotoThenZoom() {
        startPhotoZoom(Uri.fromFile(new File(mPath + ".jpg")), TARGET_HEAD_SIZE);
    }

    private void initValue() {
        ActivityCompat.requestPermissions(SellerFoodItemInformation.this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, PERMISSIONS_REQUEST_FILE);
        Uri uri = Uri.parse("content://com.android.providers.media.documents/document/image%3A1000000039");
        if (uri != null) {
            Bitmap bitmap = BitmapUtils.uriToBitmap(this, uri);
            if (bitmap != null) {
                mImg.setImageBitmap(bitmap);
                boolean b = BitmapUtils.compressBitmap2file(bitmap, IMAGE_SAVE_PATH);
                if (b) mTxtPath.setText("???????????????" + IMAGE_SAVE_PATH);
            }
        }
    }

    /**
     * ????????????????????????
     */
    private void dealChoosePhotoWithoutZoom(Intent data) {
        Uri uri = data.getData();
        if (uri != null) {
            Bitmap bitmap = BitmapUtils.uriToBitmap(this, uri);
            if (bitmap != null) {
                mImg.setImageBitmap(bitmap);
                boolean b = BitmapUtils.compressBitmap2file(bitmap, IMAGE_SAVE_PATH);
                if (b) mTxtPath.setText("???????????????" + IMAGE_SAVE_PATH);
            }
        }
    }

    /**
     * ???????????????????????????
     */
    private void dealChoosePhotoThenZoom(Intent data) {
        if (mCb.isChecked()) {
            Uri uri = data.getData();
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(uri);
                if (inputStream != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    if (bitmap != null) BitmapUtils.compressBitmap2file(bitmap, IMAGE_SAVE_PATH);
                }
                startPhotoZoom(Uri.fromFile(new File(IMAGE_SAVE_PATH)), TARGET_HEAD_SIZE);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * ???????????????????????????
     */
    private void dealZoomPhoto() {
        try {
            if (mUri != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mUri));
                if (bitmap != null) {
                    mImg.setImageBitmap(bitmap);
                    boolean b = BitmapUtils.compressBitmap2file(bitmap,IMAGE_SAVE_PATH);
                    if(b) mTxtPath.setText("????????????:" + IMAGE_SAVE_PATH);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ??????????????????
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_PHOTO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (isTakePhoto) takePhoto();
                    if (isGetPic) getPictureFromLocal();
                }
            }
            case PERMISSIONS_REQUEST_FILE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    dealZoomPhoto();
                break;
        }
    }

    /**
     * Gets the corresponding path to a file from the given content:// URI
     * @param selectedVideoUri The content:// URI to find the file path from
     * @param contentResolver The content resolver to use to perform the query.
     * @return the file path as a string
     */
    public static String getFilePathFromContentUri(Uri selectedVideoUri,
                                                   ContentResolver contentResolver) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};

        Cursor cursor = contentResolver.query(selectedVideoUri, filePathColumn, null, null, null);
//      ??????????????????????????????cursor
//      Cursor cursor = this.context.managedQuery(selectedVideoUri, filePathColumn, null, null, null);

        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_TAKING_PHOTO: // ???????????????
                    if (false) dealTakePhotoThenZoom();
                    else dealTakePhotoWithoutZoom();
                    break;
                case REQUEST_CODE_SELECT_PHOTO_FROM_LOCAL://?????????????????????
                    if (false) dealChoosePhotoThenZoom(data);
                    else dealChoosePhotoWithoutZoom(data);
                    break;
                case REQUEST_CODE_CUT_PHOTO: // ?????????????????????
                    dealZoomPhoto();
                    break;
            }
        }
    }


}
