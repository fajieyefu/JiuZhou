package com.fajieyefu.jiuzhou.Bean;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fajieyefu.jiuzhou.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;


/**
 * Created by qiancheng on 2016/10/14.
 */
public class AddFilesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private List<FilesInfo> mData;
	private Context mContext;
	private String username, psw_md5;
	private OnItemClickListener clickListener;

	public void setClickListener(OnItemClickListener clickListener) {
		this.clickListener = clickListener;
	}

	public static interface OnItemClickListener {
		void onClick(View view, int position);
	}

	public AddFilesAdapter(List<FilesInfo> list, Context context) {
		this.mData = list;
		mContext = context;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		//创建一个view
		View view = View.inflate(viewGroup.getContext(), R.layout.add_file_item, null);
		//创建一个ViewHolder
		FileViewHolder fileViewHolder = new FileViewHolder(view);


		return fileViewHolder;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		FilesInfo fileInfo = mData.get(position);
		if (position==0){
			Picasso.with(mContext).load(R.mipmap.camera).into(((FileViewHolder) holder).file_img);
		}else{
			Picasso.with(mContext).load(new File(fileInfo.getAdd_file_path())).resize(BeanUtil.dp2px(mContext,80),BeanUtil.dp2px(mContext,80)).into(((FileViewHolder) holder).file_img);

		}
	}


	@Override
	public int getItemCount() {
		return mData.size();
	}

	public class FileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		private ImageView file_img;
		private ImageView amplification;

		public FileViewHolder(View itemView) {
			super(itemView);
			file_img = (ImageView) itemView.findViewById(R.id.file_img);
			amplification = (ImageView) itemView.findViewById(R.id.amplification);
			RelativeLayout rootView = (RelativeLayout) itemView.findViewById(R.id.root_view);
			rootView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			if (clickListener != null) {
				clickListener.onClick(itemView, getPosition());
			}
		}
	}


}
