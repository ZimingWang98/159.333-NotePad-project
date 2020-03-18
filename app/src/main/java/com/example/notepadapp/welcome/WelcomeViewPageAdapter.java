package com.example.notepadapp.welcome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.notepadapp.R;

import java.util.List;

public class WelcomeViewPageAdapter extends PagerAdapter {
    Context mContext;
    List<ScreenItem> mListScreen;

    public WelcomeViewPageAdapter(Context mContext, List<ScreenItem> mListScreen) {
        this.mContext = mContext;
        this.mListScreen = mListScreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.layout_screen,null);

        ImageView imgSlide=layoutScreen.findViewById(R.id.welcome_image);
        TextView title=layoutScreen.findViewById(R.id.welcome_title);
        TextView description=layoutScreen.findViewById(R.id.welcome_description);
        title.setText(mListScreen.get(position).getTitle());
        description.setText(mListScreen.get(position).getDescription());
        imgSlide.setImageResource(mListScreen.get(position).getScreenImage());
        container.addView(layoutScreen);
        return layoutScreen;
    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return view==obj;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object obj) {
        container.removeView((View) obj);
    }
}
