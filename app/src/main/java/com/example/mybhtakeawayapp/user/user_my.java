package com.example.mybhtakeawayapp.user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybhtakeawayapp.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class user_my extends Fragment {
    private TextView user_name;
    private Button user_erweima;
    private ImageView user_saomiao;
    private View mView;
    private Button changeUserInfo = null;

    private ImageView mImg;
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
    private CharSequence[] mItems = {"选择图片"};

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Path", mPath);
    }

    public class News {
        public String title; // 标题
        public String content; //内容
        public News(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }
    RecyclerView mRecyclerView;
    MyAdapter mMyAdapter ;
    List<News> mNewsList = new ArrayList<>();

    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {

        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(getActivity(), R.layout.user_fav_item, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            News news = mNewsList.get(position);
            holder.mTitleTv.setText(news.title);
            holder.mTitleContent.setText(news.content);
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView mTitleTv;
        TextView mTitleContent;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            mTitleTv = itemView.findViewById(R.id.user_fav_1);
            mTitleContent = itemView.findViewById(R.id.user_fav_2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.mView = inflater.inflate(R.layout.user_activity_my, container, false);
        user_name = mView.findViewById(R.id.user_name);
        mRecyclerView = mView.findViewById(R.id.fav_ed_list);
        // 构造一些数据 todo
        mNewsList.add(new News("鱼香肉丝", "￥10"));
        mNewsList.add(new News("麻婆豆腐", "￥10"));
        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        init();
        user_erweima = mView.findViewById(R.id.user_erweima);
        user_saomiao = mView.findViewById(R.id.user_saomiao);
        if (savedInstanceState != null) mPath = savedInstanceState.getString("Path");

        changeUserInfo = mView.findViewById(R.id.changeUserInfo);
        if (changeUserInfo!=null) {
            changeUserInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Intent intent = new Intent(getActivity(), setUserInformation.class);
                    //startActivity(intent);
                }
            });
        }

        return this.mView;
    }

    private void init() {
        mImg = (ImageView) mView.findViewById(R.id.user_saomiao);
        mImg.setOnClickListener(v -> showPhotoDialog());
        File file = new File(IMAGE_SAVE_DIR);
        if (!file.exists()) file.mkdirs();
    }

    private void showPhotoDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("提示")
                .setItems(mItems, (dialog, which) -> {
                    operChoosePic();
                }).create().show();
    }

    /**
     * 选择图片操作
     */
    private void operChoosePic() {
        isTakePhoto = false;
        isGetPic = true;
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE))
                showFilePerDialog();
            else
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_FILE);
        } else getPictureFromLocal();
    }

    /**
     * 拍照
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
     * 文件权限提示
     */
    private void showFilePerDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("提示")
                .setMessage("PhotoDemo需要获取存储文件权限，以确保可以正常保存拍摄或选取的图片。")
                .setPositiveButton("确定", (dialog, which) -> ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, PERMISSIONS_REQUEST_FILE)).create().show();
    }

    /**
     * 从本地选择图片
     */
    private void getPictureFromLocal() {
        Intent innerIntent =
                new Intent(Intent.ACTION_GET_CONTENT);
        innerIntent.setType("image/*");
        Intent wrapperIntent = Intent.createChooser(innerIntent, null);
        startActivityForResult(wrapperIntent, REQUEST_CODE_SELECT_PHOTO_FROM_LOCAL);
    }


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
     * 处理选择图片结果
     */
    private String dealChoosePhotoWithoutZoom(Intent data) {
        Uri uri = data.getData();
        if (uri != null) {
            Bitmap bitmap = BitmapUtils.uriToBitmap(getActivity(), uri);
            if (bitmap != null) {
                String username = syncDecodeQRCode(bitmap);
                return username;
                // todo username是用户名
                //mImg.setImageBitmap(bitmap);
                //boolean b = BitmapUtils.compressBitmap2file(bitmap, IMAGE_SAVE_PATH);
            }
        }
        return null;
    }

    /**
     * 处理选择图片并剪裁
     */
    private void dealChoosePhotoThenZoom(Intent data) {
        if (false) {
            Uri uri = data.getData();
            InputStream inputStream = null;
            try {
                inputStream = getActivity().getContentResolver().openInputStream(uri);
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
     * 处理剪裁图片的结果
     */
    private void dealZoomPhoto() {
        try {
            if (mUri != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(mUri));
                if (bitmap != null) {
                    mImg.setImageBitmap(bitmap);
                    boolean b = BitmapUtils.compressBitmap2file(bitmap,IMAGE_SAVE_PATH);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理拍照结果
     */
    private void dealTakePhotoWithoutZoom() {
        String finalPath = mPath + ".jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(finalPath);
        if (bitmap != null) {
            boolean b = BitmapUtils.compressBitmap2file(bitmap, IMAGE_SAVE_PATH);
            if (b) {
                mImg.setImageBitmap(BitmapFactory.decodeFile(IMAGE_SAVE_PATH));
            }
        }
    }

    /**
     * 剪裁图片
     */
    private void startPhotoZoom(Uri uri, int size) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop", "true");
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);

            // outputX,outputY 是剪裁图片的宽高
            intent.putExtra("outputX", size);
            intent.putExtra("outputY", size);
            //   intent.putExtra("return-data", true);
            mUri = Uri.parse("file:///" + IMAGE_SAVE_PATH);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(intent, REQUEST_CODE_CUT_PHOTO);
        } catch (ActivityNotFoundException e) {
            String errorMessage = "Your device doesn't support the crop action!";
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理拍照并剪裁
     */
    private void dealTakePhotoThenZoom() {
        startPhotoZoom(Uri.fromFile(new File(mPath + ".jpg")), TARGET_HEAD_SIZE);
    }


    public static final Map<DecodeHintType, Object> HINTS = new EnumMap<>(DecodeHintType.class);
    static {
        List<BarcodeFormat> allFormats = new ArrayList<>();
        allFormats.add(BarcodeFormat.AZTEC);
        allFormats.add(BarcodeFormat.CODABAR);
        allFormats.add(BarcodeFormat.CODE_39);
        allFormats.add(BarcodeFormat.CODE_93);
        allFormats.add(BarcodeFormat.CODE_128);
        allFormats.add(BarcodeFormat.DATA_MATRIX);
        allFormats.add(BarcodeFormat.EAN_8);
        allFormats.add(BarcodeFormat.EAN_13);
        allFormats.add(BarcodeFormat.ITF);
        allFormats.add(BarcodeFormat.MAXICODE);
        allFormats.add(BarcodeFormat.PDF_417);
        allFormats.add(BarcodeFormat.QR_CODE);
        allFormats.add(BarcodeFormat.RSS_14);
        allFormats.add(BarcodeFormat.RSS_EXPANDED);
        allFormats.add(BarcodeFormat.UPC_A);
        allFormats.add(BarcodeFormat.UPC_E);
        allFormats.add(BarcodeFormat.UPC_EAN_EXTENSION);
        HINTS.put(DecodeHintType.TRY_HARDER, BarcodeFormat.QR_CODE);
        HINTS.put(DecodeHintType.POSSIBLE_FORMATS, allFormats);
        HINTS.put(DecodeHintType.CHARACTER_SET, "utf-8");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_TAKING_PHOTO: // 拍照的结果
                    if (false) dealTakePhotoThenZoom();
                    else dealTakePhotoWithoutZoom();
                    break;
                case REQUEST_CODE_SELECT_PHOTO_FROM_LOCAL://选择图片的结果
                    if (false) dealChoosePhotoThenZoom(data);
                    else dealChoosePhotoWithoutZoom(data);
                    break;
                case REQUEST_CODE_CUT_PHOTO: // 剪裁图片的结果
                    dealZoomPhoto();
                    break;
            }
        }
    }

    public static String syncDecodeQRCode(Bitmap bitmap) {
        Result result = null;
        RGBLuminanceSource source = null;
        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int[] pixels = new int[width * height];
            bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
            source = new RGBLuminanceSource(width, height, pixels);
            result = new MultiFormatReader().decode(new BinaryBitmap(new HybridBinarizer(source)), HINTS);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
            if (source != null) {
                try {
                    result = new MultiFormatReader().decode(new BinaryBitmap(new GlobalHistogramBinarizer(source)), HINTS);
                    return result.getText();
                } catch (Throwable e2) {
                    e2.printStackTrace();
                }
            }
            return null;
        }
    }


}
