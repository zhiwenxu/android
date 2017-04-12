package com.example.utils;

import android.content.Context;

import cn.app9010.supermarket.R;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

public class ImageLoaderUtil{
	
	private static ImageLoaderUtil mImageLoaderUtil;
	private  ImageLoaderUtil() {}
	public static ImageLoaderUtil getInstance(){
		if(mImageLoaderUtil == null){
			mImageLoaderUtil = new ImageLoaderUtil();
		}
		return mImageLoaderUtil;
	}
	
	//ȫ�ֳ�ʼ������application�е���
	public void init(Context context){
		ImageLoaderConfiguration config = new ImageLoaderConfiguration  
			    .Builder(context)  
			    .memoryCacheExtraOptions(480, 800) // max width, max height���������ÿ�������ļ�����󳤿�  
//			    .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null) // Can slow ImageLoader, use it carefully (Better don't use it)/���û������ϸ��Ϣ����ò�Ҫ�������  
			    .threadPoolSize(3)//�̳߳��ڼ��ص�����  
			    .threadPriority(Thread.NORM_PRIORITY - 2)  
			    .denyCacheImageMultipleSizesInMemory()  
			    .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/�����ͨ���Լ����ڴ滺��ʵ��  
			    .memoryCacheSize(2 * 1024 * 1024)    
			    .discCacheSize(50 * 1024 * 1024)    
			    .discCacheFileNameGenerator(new Md5FileNameGenerator())//�������ʱ���URI������MD5 ����  
			    .tasksProcessingOrder(QueueProcessingType.LIFO)  
			    .discCacheFileCount(100) //������ļ�����  
//			    .discCache(new UnlimitedDiscCache(cacheDir))//�Զ��建��·��  
			    .defaultDisplayImageOptions(DisplayImageOptions.createSimple())  
			    .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)��ʱʱ��  
			    .writeDebugLogs() // Remove for release app  
			    .build();//��ʼ����  
			    // Initialize ImageLoader with configuration.  
		ImageLoader.getInstance().init(config);//ȫ�ֳ�ʼ��������  
	}
	
	public DisplayImageOptions getAdOptions(){
		DisplayImageOptions options;  
		options = new DisplayImageOptions.Builder()  
		 .showImageOnLoading(R.drawable.start) //����ͼƬ�������ڼ���ʾ��ͼƬ  
		 .showImageForEmptyUri(R.drawable.start)//����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ  
		.showImageOnFail(R.drawable.start)  //����ͼƬ����/��������д���ʱ����ʾ��ͼƬ
		.cacheInMemory(false)//�������ص�ͼƬ�Ƿ񻺴����ڴ���  
		.cacheOnDisc()
		.considerExifParams(true)  //�Ƿ���JPEGͼ��EXIF��������ת����ת��
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//����ͼƬ����εı��뷽ʽ��ʾ  
//		.bitmapConfig(Bitmap.Config.RGB_565)//����ͼƬ�Ľ�������//  
//		.decodingOptions(android.graphics.BitmapFactory.Options decodingOptions)//����ͼƬ�Ľ�������  
		//.delayBeforeLoading(int delayInMillis)//int delayInMillisΪ�����õ�����ǰ���ӳ�ʱ��
		//����ͼƬ���뻺��ǰ����bitmap��������  
		//.preProcessor(BitmapProcessor preProcessor)  
		.resetViewBeforeLoading(true)//����ͼƬ������ǰ�Ƿ����ã���λ  
		.displayer(new RoundedBitmapDisplayer(0))//�Ƿ�����ΪԲ�ǣ�����Ϊ����  
		.displayer(new FadeInBitmapDisplayer(100))//�Ƿ�ͼƬ���غú���Ķ���ʱ��  
		.build();//������� 
		return options;
	}
	
	public DisplayImageOptions getGuideOptions2(){
		DisplayImageOptions options;  
		options = new DisplayImageOptions.Builder()  
		 .showImageOnLoading(R.drawable.ydy2) //����ͼƬ�������ڼ���ʾ��ͼƬ  
		 .showImageForEmptyUri(R.drawable.ydy2)//����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ  
		.showImageOnFail(R.drawable.ydy2)  //����ͼƬ����/��������д���ʱ����ʾ��ͼƬ
		.considerExifParams(true)  //�Ƿ���JPEGͼ��EXIF��������ת����ת��
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//����ͼƬ����εı��뷽ʽ��ʾ  
//		.bitmapConfig(Bitmap.Config.RGB_565)//����ͼƬ�Ľ�������//  
//		.decodingOptions(android.graphics.BitmapFactory.Options decodingOptions)//����ͼƬ�Ľ�������  
		//.delayBeforeLoading(int delayInMillis)//int delayInMillisΪ�����õ�����ǰ���ӳ�ʱ��
		//����ͼƬ���뻺��ǰ����bitmap��������  
		//.preProcessor(BitmapProcessor preProcessor)  
		.resetViewBeforeLoading(true)//����ͼƬ������ǰ�Ƿ����ã���λ  
		.displayer(new RoundedBitmapDisplayer(0))//�Ƿ�����ΪԲ�ǣ�����Ϊ����  
		.displayer(new FadeInBitmapDisplayer(100))//�Ƿ�ͼƬ���غú���Ķ���ʱ��  
		.build();//������� 
		return options;
	}
	
	public DisplayImageOptions getGuideOptions3(){
		DisplayImageOptions options;  
		options = new DisplayImageOptions.Builder()  
		 .showImageOnLoading(R.drawable.ydy3) //����ͼƬ�������ڼ���ʾ��ͼƬ  
		 .showImageForEmptyUri(R.drawable.ydy3)//����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ  
		.showImageOnFail(R.drawable.ydy3)  //����ͼƬ����/��������д���ʱ����ʾ��ͼƬ
		.considerExifParams(true)  //�Ƿ���JPEGͼ��EXIF��������ת����ת��
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//����ͼƬ����εı��뷽ʽ��ʾ  
//		.bitmapConfig(Bitmap.Config.RGB_565)//����ͼƬ�Ľ�������//  
//		.decodingOptions(android.graphics.BitmapFactory.Options decodingOptions)//����ͼƬ�Ľ�������  
		//.delayBeforeLoading(int delayInMillis)//int delayInMillisΪ�����õ�����ǰ���ӳ�ʱ��
		//����ͼƬ���뻺��ǰ����bitmap��������  
		//.preProcessor(BitmapProcessor preProcessor)  
		.resetViewBeforeLoading(true)//����ͼƬ������ǰ�Ƿ����ã���λ  
		.displayer(new RoundedBitmapDisplayer(0))//�Ƿ�����ΪԲ�ǣ�����Ϊ����  
		.displayer(new FadeInBitmapDisplayer(100))//�Ƿ�ͼƬ���غú���Ķ���ʱ��  
		.build();//������� 
		return options;
	}
	
	public DisplayImageOptions getGuideOptions(){
		DisplayImageOptions options;  
		options = new DisplayImageOptions.Builder()  
		 .showImageOnLoading(R.drawable.ydy1) //����ͼƬ�������ڼ���ʾ��ͼƬ  
		 .showImageForEmptyUri(R.drawable.ydy1)//����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ  
		.showImageOnFail(R.drawable.ydy1)  //����ͼƬ����/��������д���ʱ����ʾ��ͼƬ
		.considerExifParams(true)  //�Ƿ���JPEGͼ��EXIF��������ת����ת��
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//����ͼƬ����εı��뷽ʽ��ʾ  
//		.bitmapConfig(Bitmap.Config.RGB_565)//����ͼƬ�Ľ�������//  
//		.decodingOptions(android.graphics.BitmapFactory.Options decodingOptions)//����ͼƬ�Ľ�������  
		//.delayBeforeLoading(int delayInMillis)//int delayInMillisΪ�����õ�����ǰ���ӳ�ʱ��
		//����ͼƬ���뻺��ǰ����bitmap��������  
		//.preProcessor(BitmapProcessor preProcessor)  
		.resetViewBeforeLoading(true)//����ͼƬ������ǰ�Ƿ����ã���λ  
		.displayer(new RoundedBitmapDisplayer(0))//�Ƿ�����ΪԲ�ǣ�����Ϊ����  
		.displayer(new FadeInBitmapDisplayer(100))//�Ƿ�ͼƬ���غú���Ķ���ʱ��  
		.build();//������� 
		return options;
	}
	
	public DisplayImageOptions getIconOptions(){
		DisplayImageOptions options;  
		options = new DisplayImageOptions.Builder()  
		 .showImageOnLoading(R.drawable.tx) //����ͼƬ�������ڼ���ʾ��ͼƬ  
		 .showImageForEmptyUri(R.drawable.tx)//����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ  
		.showImageOnFail(R.drawable.tx)  //����ͼƬ����/��������д���ʱ����ʾ��ͼƬ
		.considerExifParams(true)  //�Ƿ���JPEGͼ��EXIF��������ת����ת��
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//����ͼƬ����εı��뷽ʽ��ʾ  
//		.bitmapConfig(Bitmap.Config.RGB_565)//����ͼƬ�Ľ�������//  
//		.decodingOptions(android.graphics.BitmapFactory.Options decodingOptions)//����ͼƬ�Ľ�������  
		//.delayBeforeLoading(int delayInMillis)//int delayInMillisΪ�����õ�����ǰ���ӳ�ʱ��
		//����ͼƬ���뻺��ǰ����bitmap��������  
		//.preProcessor(BitmapProcessor preProcessor)  
		.resetViewBeforeLoading(true)//����ͼƬ������ǰ�Ƿ����ã���λ  
		.displayer(new RoundedBitmapDisplayer(0))//�Ƿ�����ΪԲ�ǣ�����Ϊ����  
		.displayer(new FadeInBitmapDisplayer(100))//�Ƿ�ͼƬ���غú���Ķ���ʱ��  
		.build();//������� 
		return options;
	}
	
	public DisplayImageOptions getOptions(){
		DisplayImageOptions options;  
		options = new DisplayImageOptions.Builder()  
		 .showImageOnLoading(R.drawable.tupian1) //����ͼƬ�������ڼ���ʾ��ͼƬ  
		 .showImageForEmptyUri(R.drawable.tupian1)//����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ  
		.showImageOnFail(R.drawable.tupian1)  //����ͼƬ����/��������д���ʱ����ʾ��ͼƬ
		.cacheInMemory(false)//�������ص�ͼƬ�Ƿ񻺴����ڴ���  
		.cacheOnDisc()
		.considerExifParams(true)  //�Ƿ���JPEGͼ��EXIF��������ת����ת��
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//����ͼƬ����εı��뷽ʽ��ʾ  
//		.bitmapConfig(Bitmap.Config.RGB_565)//����ͼƬ�Ľ�������//  
//		.decodingOptions(android.graphics.BitmapFactory.Options decodingOptions)//����ͼƬ�Ľ�������  
		//.delayBeforeLoading(int delayInMillis)//int delayInMillisΪ�����õ�����ǰ���ӳ�ʱ��
		//����ͼƬ���뻺��ǰ����bitmap��������  
		//.preProcessor(BitmapProcessor preProcessor)  
		.resetViewBeforeLoading(true)//����ͼƬ������ǰ�Ƿ����ã���λ  
		.displayer(new RoundedBitmapDisplayer(0))//�Ƿ�����ΪԲ�ǣ�����Ϊ����  
		.displayer(new FadeInBitmapDisplayer(100))//�Ƿ�ͼƬ���غú���Ķ���ʱ��  
		.build();//������� 
		return options;
	}
	public DisplayImageOptions getLoinOptions(){
		DisplayImageOptions options;  
		options = new DisplayImageOptions.Builder()  
		 .showImageOnLoading(R.drawable.change_bj) //����ͼƬ�������ڼ���ʾ��ͼƬ  
		 .showImageForEmptyUri(R.drawable.change_bj)//����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ  
		.showImageOnFail(R.drawable.change_bj)  //����ͼƬ����/��������д���ʱ����ʾ��ͼƬ
		.cacheInMemory(false)//�������ص�ͼƬ�Ƿ񻺴����ڴ���  
		.cacheOnDisc()
		.considerExifParams(true)  //�Ƿ���JPEGͼ��EXIF��������ת����ת��
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//����ͼƬ����εı��뷽ʽ��ʾ  
//		.bitmapConfig(Bitmap.Config.RGB_565)//����ͼƬ�Ľ�������//  
//		.decodingOptions(android.graphics.BitmapFactory.Options decodingOptions)//����ͼƬ�Ľ�������  
		//.delayBeforeLoading(int delayInMillis)//int delayInMillisΪ�����õ�����ǰ���ӳ�ʱ��
		//����ͼƬ���뻺��ǰ����bitmap��������  
		//.preProcessor(BitmapProcessor preProcessor)  
		.resetViewBeforeLoading(true)//����ͼƬ������ǰ�Ƿ����ã���λ  
		.displayer(new RoundedBitmapDisplayer(0))//�Ƿ�����ΪԲ�ǣ�����Ϊ����  
		.displayer(new FadeInBitmapDisplayer(100))//�Ƿ�ͼƬ���غú���Ķ���ʱ��  
		.build();//������� 
		return options;
	}

}
