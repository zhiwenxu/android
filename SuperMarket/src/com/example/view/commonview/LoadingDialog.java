package com.example.view.commonview;
 
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import cn.app9010.supermarket.R;
 
public class LoadingDialog extends Dialog{
	
    public LoadingDialog(Context context) {
    	super(context, R.style.customprogressdialog);
        setContentView(R.layout.loading_dialog);
    }
    /**
     * �����ڽ���ı�ʱ����
     */
    @Override
	public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
        // ��ȡImageView�ϵĶ�������
        AnimationDrawable spinner = (AnimationDrawable) imageView.getDrawable();
        // ��ʼ����
        spinner.start();
    }
 
    /**
     * ��Dialog������ʾ��Ϣ
     * @param message
     */
    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
        	findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }
 
    /**
     * �����Զ���ProgressDialog
     * 
     * @param context
     *            ������
     * @param message
     *            ��ʾ
     * @param cancelable
     *            �Ƿ񰴷��ؼ�ȡ��
     * @param cancelListener
     *            ���·��ؼ�����
     * @return
     */
    public void show(CharSequence message, boolean cancelable, OnCancelListener cancelListener) {
        if (message == null || message.length() == 0) {
        	findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
        }
        // �����ؼ��Ƿ�ȡ��
        setCancelable(cancelable);
        // �������ؼ�����
        setOnCancelListener(cancelListener);
        // ���þ���
        getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        // ���ñ�����͸����
        lp.dimAmount = 0.2f;
        getWindow().setAttributes(lp);
        // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        show();
    }
}