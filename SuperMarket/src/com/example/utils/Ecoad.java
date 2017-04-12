package com.example.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
 
import android.graphics.Bitmap;
 
//����ͼƬ
public class Ecoad {
    private int width;
    private int height;
    public Ecoad(int width,int height){
        this.width=width;
        this.height=height;
    }
    //���ƶ�ά��
    public Bitmap code2(String s) throws Exception{ 
        //��ά��QR_CODE
        BarcodeFormat fomt=BarcodeFormat.QR_CODE;
        //����ת��
        String a=new String(s.getBytes("utf-8"),"ISO-8859-1");
        BitMatrix matrix=new MultiFormatWriter().encode(a, fomt, width, height);
        int width=matrix.getWidth();
        int height=matrix.getHeight();
        int[] pixel=new int[width*height];
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                if(matrix.get(j,i))
                pixel[i*width+j]=0xff000000;
            }
        }
        Bitmap bmap=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        bmap.setPixels(pixel, 0, width, 0, 0, width, height);
        return bmap;
    }
    //����������
    public Bitmap code1(String ss) throws Exception{
        //������CODE_128
        BarcodeFormat fomt=BarcodeFormat.CODE_128;
        BitMatrix matrix=new MultiFormatWriter().encode(ss, fomt, width, height);
        int width=matrix.getWidth();
        int height=matrix.getHeight();
        int[] pixel=new int[width*height];
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                if(matrix.get(j,i))
                pixel[i*width+j]=0xff000000;
            }
        }
        Bitmap bmapp=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        bmapp.setPixels(pixel, 0, width, 0, 0, width, height);
        return bmapp;
    }
}