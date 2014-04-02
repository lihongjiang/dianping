package com.sport;

import java.util.HashMap;
import java.util.Map;

import com.bslee.businesses.BusinessesRequestParam;
import com.bslee.businesses.BusinessesResponseBean;
import com.bslee.net.AsyncTaskManager;
import com.bslee.net.NetDataManager;
import com.bslee.net.RequestListener;
import com.bslee.util.AbAppUtil;
import com.bslee.xutils.view.ViewUtils;
import com.bslee.xutils.view.annotation.ViewInject;
import com.bslee.xutils.view.annotation.event.OnClick;
import com.bslee.xutils.view.annotation.event.OnItemClick;

import com.sport.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.View;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SportModelActivity extends Activity {
	@ViewInject(R.id.myimagelist)
	public ListView myimagelist;
	@ViewInject(R.id.download)
	Button download;
	public int page = 1;
	public MyImageAdapter adapter;
	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			
			if(msg.what==0x0001){
				pb.setMessage("获取数据失败！");
				
			}else  if(msg.what==0x0002){
				pb.setMessage("获取数据成功！");
				adapter.notifyDataSetChanged();
			}
			pb.dismiss();
			
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		if(AbAppUtil.isNetworkAvailable(this)){
			Toast.makeText(this, "请检查网络，网络连接失败！",Toast.LENGTH_LONG).show();
		}
		ViewUtils.inject(this);
		pb = new ProgressDialog(this);
		adapter = new MyImageAdapter(this, myimagelist);
		myimagelist.setAdapter(adapter);
		pb.setMessage("正在加载中。。。");
		pb.show();
		download(page);
	}

	ProgressDialog pb;

	@OnClick(R.id.download)
	public void downloaddata(View v) {
		page++;
		pb.setMessage("正在加载中。。。");
		pb.show();
		download(page);
	}

	private void download(int p) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("category", "运动健身");
		paramMap.put("city", "北京");
		paramMap.put("format", "json");
		paramMap.put("has_coupon", "1");
		paramMap.put("limit", "40");
		paramMap.put("platform", "2");
		AsyncTaskManager.getInstance().getVisitor(
				new BusinessesRequestParam(paramMap),
				new RequestListener<BusinessesResponseBean>() {

					@Override
					public void onStart() {

					}

					@Override
					public void onComplete(BusinessesResponseBean bean) {
						bean.resolve(true);
						if (bean.success) {
							Message msg = new Message();
							msg.what = 0x0002;
							handler.sendMessage(msg);
						} else {
							Message msg = new Message();
							msg.what = 0x0001;
							handler.sendMessage(msg);
						}

					}
				});
	}

}