package com.example.notepadapp.search;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.notepadapp.R;
import com.example.notepadapp.database.DatabaseHelper;

import java.util.List;
import java.util.Map;

public class SearchAdapter extends BaseAdapter {

    private Context context;

    private List<Map<String, String>> data;

    SearchAdapter(Context context, List<Map<String, String>> data) {
        this.context = context;
        this.data = data;
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
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_note,parent,false);
            holder = new ViewHolder();
            holder.tvTitle = convertView.findViewById(R.id.tv_title);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            holder.ivIcon = convertView.findViewById(R.id.iv_icon);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        Map<String, String> map = data.get(position);
        holder.tvTitle.setText(map.get(DatabaseHelper.CONTENT));
        holder.tv_desc.setText(map.get(DatabaseHelper.DATE));
//        String path = map.get(DatabaseHelper.PHOTO);
//        holder.ivIcon.setImageBitmap(BitmapFactory.decodeFile(path));
        return convertView;
    }

    static class ViewHolder {
        TextView tvTitle;
        TextView tv_desc;
        ImageView ivIcon;
    }
}