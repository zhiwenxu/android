package com.example.view.commonview;

import cn.app9010.supermarket.R;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Administrator on 2016/12/7.
 */

public class HdDialog extends Dialog implements android.view.View.OnClickListener{

	private ImageView hdIcon;
	private TextView hdTitle;
	private TextView hdContetn;
	private TextView hdTimeFrom;
	private TextView hdTimeTo;
	private Button hdBtn;
    public HdDialog(Context context) {
        super(context, R.style.customprogressdialog);
        setContentView(R.layout.hd_dialog);
        init();
    }
    
    public void init(){
    	hdIcon = (ImageView)findViewById(R.id.hd_icon);
    	hdTitle = (TextView)findViewById(R.id.hd_title);
    	hdContetn = (TextView)findViewById(R.id.hd_content);
    	hdTimeFrom = (TextView)findViewById(R.id.hd_time_from);
    	hdTimeTo = (TextView)findViewById(R.id.hd_time_to);
    	hdBtn = (Button)findViewById(R.id.hd_btn);
    	hdBtn.setOnClickListener(this);
    }
    
    public void initdata(int type,String title,String content,String from,String to){
    	hdTitle.setText(title);
    	hdContetn.setText(content);
    	hdTimeFrom.setText(from);
    	hdTimeTo.setText(to);
    	
    	switch(type){
    	
    	case 1:
    		hdIcon.setBackgroundResource(R.drawable.xfmj);
			break;
    	case 2:
    		hdIcon.setBackgroundResource(R.drawable.xfzk);
			break;
    	case 3:
    		hdIcon.setBackgroundResource(R.drawable.sjlj);
			break;
    	case 4:
    		hdIcon.setBackgroundResource(R.drawable.sjhb);
			break;
    	case 5:
    		hdIcon.setBackgroundResource(R.drawable.jfdx);
			break;
    	}
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == hdBtn){
			dismiss();
		}
	}
}
