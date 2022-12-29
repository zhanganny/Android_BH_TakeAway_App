package com.example.mybhtakeawayapp.admin;

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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mybhtakeawayapp.Local;
import com.example.mybhtakeawayapp.LoginActivity;
import com.example.mybhtakeawayapp.R;
import com.example.mybhtakeawayapp.RegisterActivity;
import com.example.mybhtakeawayapp.rider.setDeliveryInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdministratorHomeActivity extends Activity {
    private String localIP = Local.getLocalIp();
    private Button addSystemInformation;
    private TextView userNumText;
    private TextView providerNumText;
    private TextView riderNumText;

    public class News {
        // todo
        public String infoToConfirm; // 标题
        public News(String infoToConfirm) {
            this.infoToConfirm = infoToConfirm;
        }
    }

    RecyclerView mRecyclerView;
    MyAdapter mMyAdapter ;
    List<News> mNewsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator_home);
        userNumText = findViewById(R.id.userNum);
        providerNumText = findViewById(R.id.providerNum);
        riderNumText = findViewById(R.id.riderNum);
        JSONObject jsonObject = new JSONObject();
        String userUrl = localIP+"user/getUserNum";
        String providerUrl = localIP+"provider/getProviderNum";
        String riderUrl = localIP+"rider/getRiderNum";
        RequestQueue requestQueue = Volley.newRequestQueue(AdministratorHomeActivity.this);
        System.err.println("g");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, userUrl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    boolean state = jsonObject.getBoolean("state");
                    String msg = jsonObject.getString("msg");
                    System.err.println(state);
                    if (state) {
                        int userNum = jsonObject.getInt("data");
                        userNumText.setText(userNum);
                    } else {
                        Toast.makeText(AdministratorHomeActivity.this, "加载买家数据失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("错误", volleyError.toString());
                Toast.makeText(AdministratorHomeActivity.this, "网络失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, providerUrl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    boolean state = jsonObject.getBoolean("state");
                    String msg = jsonObject.getString("msg");
                    if (state) {
                        int userNum = jsonObject.getInt("data");
                        providerNumText.setText(userNum);
                    } else {
                        Toast.makeText(AdministratorHomeActivity.this, "加载商家数据失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("错误", volleyError.toString());
                Toast.makeText(AdministratorHomeActivity.this, "网络失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
        //
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, riderUrl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    boolean state = jsonObject.getBoolean("state");
                    String msg = jsonObject.getString("msg");
                    if (state) {
                        int userNum = jsonObject.getInt("data");
                        riderNumText.setText(userNum);
                    } else {
                        Toast.makeText(AdministratorHomeActivity.this, "加载骑手数据失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("错误", volleyError.toString());
                Toast.makeText(AdministratorHomeActivity.this, "网络失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
        //添加系统消息
        addSystemInformation = findViewById(R.id.addSystemInformation);
        addSystemInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // 链接recyclerview todo
        mRecyclerView = findViewById(R.id.order_ed_list);
        // 构造一些数据 todo
        mNewsList.add(new News("商家认证"));
        mNewsList.add(new News("骑手认证"));
        mNewsList.add(new News("贫困生认证"));

        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(AdministratorHomeActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);


        init();
        //initValue();
        if (savedInstanceState != null) mPath = savedInstanceState.getString("Path");


    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {

        @Override
        public MyViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(AdministratorHomeActivity.this, R.layout.administrator_information, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }

        @Override
        public void onBindViewHolder(MyViewHoder holder, int position) {
            News news = mNewsList.get(position);
            // todo
            holder.infoToConfirm.setText(news.infoToConfirm);
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView infoToConfirm;
        Button goToConfirm;
        // todo
        public MyViewHoder(View itemView) {
            super(itemView);
            infoToConfirm = itemView.findViewById(R.id.infoToConfirm);
            goToConfirm = itemView.findViewById(R.id.goToConfirm);
        }
    }

    private TextView mTxtPath;
    private Button mImg;
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

    private CharSequence[] mItems = {"拍照上传", "选择图片"};

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Path", mPath);
    }

    private void init() {
        mImg = (Button) this.findViewById(R.id.addSystemInformation);
        //mTxtPath = (TextView) this.findViewById(R.id.path);
        //mCb = (CheckBox) this.findViewById(R.id.cb);
        mImg.setOnClickListener(v -> showPhotoDialog());
        //initValue();
        File file = new File(IMAGE_SAVE_DIR);
        if (!file.exists()) file.mkdirs();

    }

    /**
     * 弹出操作选择对话框
     */
    private void showPhotoDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setItems(mItems, (dialog, which) -> {
                    if (which == 0) operTakePhoto();
                    else if (which == 1) operChoosePic();
                }).create().show();
    }

    /**
     * 拍照操作
     */
    private void operTakePhoto() {
        isTakePhoto = true;
        isGetPic = false;
        if (ContextCompat.checkSelfPermission(AdministratorHomeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(AdministratorHomeActivity.this, Manifest.permission.CAMERA))
                showPhotoPerDialog();
            else
                ActivityCompat.requestPermissions(AdministratorHomeActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_PHOTO);
        } else takePhoto();
    }

    /**
     * 选择图片操作
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
     * 拍照权限提示
     */
    private void showPhotoPerDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("PhotoDemo需要获取访问您的相机权限，以确保您可以正常拍照。")
                .setPositiveButton("确定", (dialog, which) -> ActivityCompat.requestPermissions(AdministratorHomeActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_PHOTO)).create().show();
    }

    /**
     * 文件权限提示
     */
    private void showFilePerDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("PhotoDemo需要获取存储文件权限，以确保可以正常保存拍摄或选取的图片。")
                .setPositiveButton("确定", (dialog, which) -> ActivityCompat.requestPermissions(AdministratorHomeActivity.this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, PERMISSIONS_REQUEST_FILE)).create().show();
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
     * 从本地选择图片
     */
    private void getPictureFromLocal() {
        Intent innerIntent =
                new Intent(Intent.ACTION_GET_CONTENT);
        innerIntent.setType("image/*");
        Intent wrapperIntent = Intent.createChooser(innerIntent, null);
        startActivityForResult(wrapperIntent, REQUEST_CODE_SELECT_PHOTO_FROM_LOCAL);
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
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
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
                //mImg.setImageBitmap(BitmapFactory.decodeFile(IMAGE_SAVE_PATH));
                //mTxtPath.setText("图片路径：" + IMAGE_SAVE_PATH);
            }
        }
    }

    /**
     * 处理拍照并剪裁
     */
    private void dealTakePhotoThenZoom() {
        startPhotoZoom(Uri.fromFile(new File(mPath + ".jpg")), TARGET_HEAD_SIZE);
    }

    private void initValue() {
        ActivityCompat.requestPermissions(AdministratorHomeActivity.this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, PERMISSIONS_REQUEST_FILE);
        Uri uri = Uri.parse("content://com.android.providers.media.documents/document/image%3A1000000039");
        if (uri != null) {
            Bitmap bitmap = BitmapUtils.uriToBitmap(this, uri);
            if (bitmap != null) {
               // mImg.setImageBitmap(bitmap);
                boolean b = BitmapUtils.compressBitmap2file(bitmap, IMAGE_SAVE_PATH);
                if (b) mTxtPath.setText("图片地址：" + IMAGE_SAVE_PATH);
            }
        }
    }

    /**
     * 处理选择图片结果
     */
    private void dealChoosePhotoWithoutZoom(Intent data) {
        Uri uri = data.getData();
        if (uri != null) {
            Bitmap bitmap = BitmapUtils.uriToBitmap(this, uri);
            if (bitmap != null) {
                //mImg.setImageBitmap(bitmap);
                boolean b = BitmapUtils.compressBitmap2file(bitmap, IMAGE_SAVE_PATH);
                if (b) mTxtPath.setText("图片地址：" + IMAGE_SAVE_PATH);
            }
        }
    }

    /**
     * 处理选择图片并剪裁
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
     * 处理剪裁图片的结果
     */
    private void dealZoomPhoto() {
        try {
            if (mUri != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mUri));
                if (bitmap != null) {
                    //mImg.setImageBitmap(bitmap);
                    boolean b = BitmapUtils.compressBitmap2file(bitmap,IMAGE_SAVE_PATH);
                    if(b) mTxtPath.setText("图片地址:" + IMAGE_SAVE_PATH);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 权限申请回调
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
//      也可用下面的方法拿到cursor
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

}
