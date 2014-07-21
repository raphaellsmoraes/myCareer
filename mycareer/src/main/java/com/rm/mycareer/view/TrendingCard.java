package com.rm.mycareer.view;

import android.view.View;
import android.widget.TextView;

import com.fima.cardsui.objects.RecyclableCard;
import com.rm.mycareer.R;

/**
 * Created by vntramo on 7/14/2014.
 */
public class TrendingCard extends RecyclableCard {
    public TrendingCard(String title){
        super(title);
    }

    @Override
    protected int getCardLayoutId() {
        return R.layout.card_base;
    }

    @Override
    protected void applyTo(View convertView) {
        ((TextView)convertView.findViewById(R.id.title)).setText(title);
    }
}
