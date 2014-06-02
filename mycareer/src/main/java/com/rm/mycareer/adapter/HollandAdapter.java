package com.rm.mycareer.adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rm.mycareer.R;
import com.rm.mycareer.myCareer;
import com.rm.mycareer.utils.myCareerUtils;

import java.util.List;

public class HollandAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener{

    private Context context;
    private List<Integer> imageIds;
    public ViewHolderItem viewHolder = null;
    private SparseBooleanArray mCheckStates;
    CheckBox cb;

    public HollandAdapter(List<Integer> imageList) {
        context = myCareer.getContext();
        imageIds = imageList;
        mCheckStates = new SparseBooleanArray(imageIds.size());
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

        final View view;

        if(convertView==null){

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.holland_grid_item, parent, false);

            // well set up the ViewHolder
            viewHolder = new ViewHolderItem();
            viewHolder.imageViewItem = (ImageView)view.findViewById(R.id.holland_iv);
            cb = (CheckBox)view.findViewById(R.id.holland_cb);
            viewHolder.textViewItem = (TextView)view.findViewById(R.id.holland_tv);

            // store the holder with the view.
            view.setTag(viewHolder);

        }else{
            view = convertView;
            viewHolder = (ViewHolderItem) view.getTag();
        }

        view.setId(position);
        cb.setTag(position);
        cb.setChecked(mCheckStates.get(position, false));
        cb.setOnCheckedChangeListener(this);
        viewHolder.textViewItem.setText(myCareerUtils.getStringResourceByName("holland" + (position + 1) + "_string"));
        viewHolder.imageViewItem.setImageResource(imageIds.get(position));

        return view;
    }

    public boolean isChecked(int position) {
        return mCheckStates.get(position, false);
    }

    public void setChecked(int position, boolean isChecked) {
        mCheckStates.put(position, isChecked);
        cb.setChecked(isChecked);
    }

    public void toggle(int position) {
        setChecked(position, !isChecked(position));
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView,
                                 boolean isChecked) {
        mCheckStates.put((Integer) buttonView.getTag(), isChecked);

    }

    static class ViewHolderItem {
        ImageView imageViewItem;
        TextView textViewItem;
        CheckBox checkItem;
    }
}