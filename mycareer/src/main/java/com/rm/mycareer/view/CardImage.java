package com.rm.mycareer.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fima.cardsui.objects.RecyclableCard;
import com.rm.mycareer.R;

/**
 * Created by vntramo on 7/14/2014.
 */
public class CardImage extends RecyclableCard {

    public CardImage(String title, int image){
        super(title, image);
    }

    @Override
    protected int getCardLayoutId() {
        return R.layout.card_image;
    }

    @Override
    protected void applyTo(View convertView) {
        ((TextView) convertView.findViewById(R.id.title)).setText(title);
        ((ImageView) convertView.findViewById(R.id.imageView1)).setImageResource(image);
    }
}
