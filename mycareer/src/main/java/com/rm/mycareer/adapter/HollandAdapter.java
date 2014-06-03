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

import java.util.List;

public class HollandAdapter extends BaseAdapter {

    private Context context;
    private List<Integer> imageIds;
    public ViewHolderItem viewHolder = null;

    public HollandAdapter(List<Integer> imageList) {
        context = myCareer.getContext();
        imageIds = imageList;
    }

    public int getCount() {
        return imageIds.size();
    }

    public Object getItem(int position) {
        return imageIds.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderItem holder;

        if (convertView == null) {
            holder = new ViewHolderItem();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.holland_grid_item, parent, false);

            holder.imageViewItem = (ImageView) convertView.findViewById(R.id.holland_iv);
            holder.checkItem = (CheckBox) convertView.findViewById(R.id.holland_cb);
            holder.textViewItem = (TextView) convertView.findViewById(R.id.holland_tv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolderItem) convertView.getTag();
        }
        holder.checkItem.setId(position);
        holder.imageViewItem.setId(position);
        holder.textViewItem.setId(position);
        holder.checkItem.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                int id = cb.getId();
                if (PersonalityView.hollandSelection[id]) {
                    cb.setChecked(false);
                    PersonalityView.hollandSelection[id] = false;
                } else {
                    cb.setChecked(true);
                    PersonalityView.hollandSelection[id] = true;
                }
            }
        });

        holder.imageViewItem.setImageDrawable(myCareerUtils.getDrawable("holland"+(position+1)));
        holder.checkItem.setChecked(PersonalityView.hollandSelection[position]);
        holder.textViewItem.setText(myCareerUtils.getStringResourceByName("holland"+(position+1)+"_string"));
        holder.id = position;

        return convertView;
    }

    public static class ViewHolderItem {
        public ImageView imageViewItem;
        public TextView textViewItem;
        public CheckBox checkItem;
        public int id;
    }
}