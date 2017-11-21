package com.example.ccydemo.gifDemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

/**
 * Created by ccy on 2017-08-24.
 * 打开本地相册并获取真实图片路径帮助类
 * 拷贝自 《第一行代码》（第二版） P.300
 */

public class LocalAlbumHelper {


    /**
     * 打开本地相册
     * @param act
     * @param requestCode
     */
    public static void openLocalAlbum(Activity act,int requestCode){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        act.startActivityForResult(intent,requestCode);
    }

    /**
     * 获取选取的相册图片真实路径
     * @param data onActivityResult返回的data参数
     * @return
     */
    public static String handleImage(Context context,Intent data){
        String imagePath;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            //4.4及以上系统使用这个方法处理图片
            imagePath = LocalAlbumHelper.handleImageOnKitKat(context,data);
        }else {
            //4.4以下系统使用这个方法处理图片
            imagePath = LocalAlbumHelper.handleImageBeforeKitKat(context,data);
        }
        return imagePath;
    }


    /**
     * 获取选取的相册图片真实路径 （>=4.4)
     * @param data onActivityResult返回的data参数
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String  handleImageOnKitKat(Context context,Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(context,uri)){
            //如果是document类型的uri，则通过document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1]; //解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(context,MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(context,contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(context,uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    /**
     * 获取选取的相册图片真实路径 （< 4.4)
     * @param data onActivityResult返回的data参数
     * @return
     */
    public static String handleImageBeforeKitKat(Context ctx,Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(ctx,uri,null);
        return imagePath;
    }

    private static String getImagePath(Context ctx,Uri uri, String selection){
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = ctx.getContentResolver().query(uri,null,selection,null,null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
