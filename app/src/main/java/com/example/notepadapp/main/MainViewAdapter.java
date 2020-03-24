package com.example.notepadapp.main;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.notepadapp.R;
import static com.example.notepadapp.database.DatabaseHelper.CONTENT;
import static com.example.notepadapp.database.DatabaseHelper.DATE;

public class MainViewAdapter extends BaseAdapter {
    private Context mContext;
    private Cursor mCursor;
    private LinearLayout mLayout;

    public MainViewAdapter(Context mContext, Cursor mCursor) {
        this.mContext = mContext;
        this.mCursor = mCursor;
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public Object getItem(int i) {
        return mCursor.getPosition();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        mLayout=(LinearLayout) inflater.inflate(R.layout.cell_note_layout,null);
        TextView tv_content=(TextView)mLayout.findViewById(R.id.tv_content);
        TextView tv_date=(TextView)mLayout.findViewById(R.id.tv_date);
        mCursor.moveToPosition(i);
        String content=mCursor.getString(mCursor.getColumnIndex(CONTENT));
        String date=mCursor.getString(mCursor.getColumnIndex(DATE));
        tv_content.setText(content);
        tv_date.setText(date);
        return mLayout;
    }
}
