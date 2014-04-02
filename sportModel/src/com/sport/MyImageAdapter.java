package com.sport;

import java.util.List;

import com.bslee.businesses.Businesses;
import com.bslee.imageload.AsyncImageLoader;
import com.bslee.imageload.AsyncImageLoader.ImageCallback;
import com.bslee.net.NetDataManager;

import android.app.Activity;

import android.graphics.Bitmap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.ListView;

public class MyImageAdapter extends BaseAdapter {
	Activity testPicActivity;
	ListView listview;
	AsyncImageLoader Loader;
	List<Businesses> data = NetDataManager.userresult;

	public MyImageAdapter(Activity testPicActivity, ListView listview) {
		this.testPicActivity = testPicActivity;
		this.listview = listview;
		Loader = new AsyncImageLoader(testPicActivity, "xiaohua");
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(testPicActivity).inflate(
					R.layout.list_item, null);
			holder.imageview = (ImageView) convertView
					.findViewById(R.id.listimage);
			holder.imageview2 = (TextView) convertView
					.findViewById(R.id.listimage2);
			holder.imageview3 = (TextView) convertView
					.findViewById(R.id.listimage3);
			holder.imageview4 = (TextView) convertView
					.findViewById(R.id.listimage4);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.imageview2.setText(data.get(position).name);
		holder.imageview3.setText("电话:"+data.get(position).telephone);
		holder.imageview4.setText("地址:"+data.get(position).address);
		
		String url = data.get(position).s_photo_url;
		holder.imageview.setTag(url);
		Bitmap bit = Loader.getBitmap2(url);
		holder.imageview.setImageResource(android.R.color.white);
		if (bit == null) {
			Loader.downloadImage(url, new ImageCallback() {
				@Override
				public void onImageLoaded(Bitmap bitmap, String imageUrl) {
					ImageView imageViewByTag = (ImageView) listview
							.findViewWithTag(imageUrl);
					if (imageViewByTag != null) {
						if (bitmap != null) {
							imageViewByTag.setImageBitmap(bitmap);
						}
					}
				}
			});
		} else {
			holder.imageview.setImageBitmap(bit);
		}
		return convertView;
	}

	class ViewHolder {
		ImageView imageview;
		TextView imageview2;
		TextView imageview3;
		TextView imageview4;
	}
}
