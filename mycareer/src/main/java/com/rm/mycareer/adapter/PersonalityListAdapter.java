package com.rm.mycareer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.rm.mycareer.R;
import com.rm.mycareer.myCareer;
import com.rm.mycareer.utils.myCareerUtils;
import com.rm.mycareer.view.PersonalityView;

import java.util.Map;

/**
 * Created by vntramo on 6/10/2014.
 */
public class PersonalityListAdapter extends BaseAdapter {

    private Context context;
    private Map<String, Integer> personalityMap;

    public PersonalityListAdapter(Map<String, Integer> map) {
        this.personalityMap = map;
        this.context = myCareer.getContext();
    }

    @Override
    public int getCount() {
        return personalityMap.size();
    }

    @Override
    public Object getItem(int i) {
        return personalityMap.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem holder;

        if (convertView == null) {
            holder = new ViewHolderItem();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.personality_item, parent, false);

            holder.textViewItem = (TextView) convertView.findViewById(R.id.tv_personality_result);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderItem) convertView.getTag();
        }

        holder.textViewItem.setId(position);
        holder.textViewItem.setText(personalityMap.keySet().toArray()[position].toString());
        holder.id = position;

        return convertView;
    }

    public static class ViewHolderItem {
        public TextView textViewItem;
        public int id;
    }
}
