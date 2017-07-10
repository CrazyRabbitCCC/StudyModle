package study.lzy.studymodle.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.lzy.studymodle.R;
import study.lzy.studymodle.Utils.BaseUtil;
import study.lzy.studymodle.Utils.PrefUtils;

// @author: lzy  time: 2016/11/15.


public class ImageConfigureActivity extends Activity {

    @Bind(R.id.rv)
    RecyclerView rv;
    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    ImageAdapter adapter=new ImageAdapter();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Bundle data = getIntent().getExtras();
//        if (data != null) {
//            mAppWidgetId = data.getInt(
//                    AppWidgetManager.EXTRA_APPWIDGET_ID,
//                    AppWidgetManager.INVALID_APPWIDGET_ID);
//        }
        setResult(RESULT_CANCELED);
//        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
//            finish();
//            return;
//        }
//        TextView textView=new TextView(this);
//        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        textView.setText("wait...");
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextSize(20);
//        setContentView(textView);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
        new Thread(() -> getImage(new File(basePath))).start();




//        Intent i = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//调用android的图库
//        startActivityForResult(i,1000);
    }

    String basePath= Environment.getExternalStorageDirectory().getAbsolutePath();
    FilenameFilter  filenameFilter= (dir, name) -> {
        if(new File(dir,name).isDirectory()) {
            return true;

        }
        if (BaseUtil.getMIMEType(name).equals("image"))
            return true;
        return false;
    };

    FileFilter fileFilter= file -> {
        if (file.isDirectory())
            return  true;

        String mimeType = BaseUtil.getMIMEType(file);
        if (mimeType!=null&&mimeType.equals("image"))
            return true;
        return false;
    };

    public void getImage(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles(fileFilter);
            if (files!=null)
            for (File fileEE : files)
                getImage(fileEE);
        } else {
            String mimeType = BaseUtil.getMIMEType(file);
            if (mimeType!=null&&mimeType.equals("image")) {
            mHandler.post(() -> adapter.add(file));
        }
        }

    }
    private Handler mHandler=new Handler();
    private int imageId=0x20;
    class ImageAdapter extends RecyclerView.Adapter<ImageHolder>{
        List<File > list=new ArrayList<>();


        public void add(File file){
            list.add(file);
            notifyItemInserted(list.size()-1);
        }

        @Override
        public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            FrameLayout view=new FrameLayout(parent.getContext());
            Resources resources = parent.getContext().getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            float density = dm.density;
            int height = dm.heightPixels/3-2;
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(height, height);
            params.setMargins(1,1,1,1);
            view.setLayoutParams(params);
            ImageView imageView=new ImageView(parent.getContext());
            imageView.setId(imageId);
            view.setPadding(1,1,1,1);
            view.addView(imageView, height -2,height-2);
            ImageHolder holder = new ImageHolder(view);
            holder.width= height -2;
            holder.height=height-2;
            return holder;
        }

        @Override
        public void onBindViewHolder(ImageHolder holder, int position) {
            loadBitmap(holder.imageView,list.get(position).getPath(),holder.width,holder.height);

        }

        void loadBitmap(ImageView image,String FilePath,int width,int height){
            new Thread(()->{
                BitmapFactory.Options options=new BitmapFactory.Options();
                options.inJustDecodeBounds=true;
                boolean flag=false;
                double wV=0.0;
                double hV=0.0;
                BitmapFactory.decodeFile(FilePath,options);
                if (options.outWidth>width||options.outHeight>height){
                    wV = options.outWidth * 1.0 /width;
                    hV = options.outHeight * 1.0 /height;
                    if (wV<hV)
                        options.inSampleSize= (int) (wV);
                    else
                        options.inSampleSize= (int) (hV);
                    flag=true;
                }
                options.inJustDecodeBounds=false;


                boolean finalFlag = flag;
                double finalWV = wV;
                double finalHV = hV;
                mHandler.post(()->{
                    if (image!=null) {
                        Bitmap bitmap = BitmapFactory.decodeFile(FilePath, options);
                        if (finalFlag){
                            if (finalWV > finalHV)
                                bitmap= Bitmap.createBitmap(bitmap, 0, ((options.outHeight -height)/2), bitmap.getWidth(),height);
                            else
                                bitmap=Bitmap.createBitmap(bitmap, ((options.outWidth -width)/2), 0, width,bitmap.getHeight());
                        }
                        image.setImageBitmap(bitmap);
                    }
                });
            }).start();


        }
        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {

            case RESULT_OK:
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null,
                        null, null, null);
                cursor.moveToFirst();
                String imgNo = cursor.getString(0); // 图片编号
                String imgPath = cursor.getString(1); // 图片文件路径
                String imgSize = cursor.getString(2); // 图片大小
                String imgName = cursor.getString(3); // 图片文件名
                cursor.close();
                if (requestCode == 1000) {
                    Intent resultValue = new Intent();
                    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                    setResult(RESULT_OK, resultValue);
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                    ImageWidget.updateAppWidget(this, appWidgetManager, mAppWidgetId);
                    PrefUtils.savePref(this, mAppWidgetId, imgPath);
                }
                break;
            case RESULT_CANCELED:// 取消
                break;
        }
        finish();
    }

    @Override
    protected void onPause() {
        Logger.d("onPause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Logger.d("onDestroy");
        super.onDestroy();
    }

    private class ImageHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        int width;
        int height;
        public ImageHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(imageId);
        }
    }
}
