package com.example.clickertwo.CardView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.clickertwo.GameScreen.GameHard;
import com.example.clickertwo.GameScreen.GameNorm;
import com.example.clickertwo.R;
import com.example.clickertwo.Screen.FragmentScreen.MainFragment;
import com.example.clickertwo.Screen.GameScreen;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class AdapterCard extends PagerAdapter {

    String profileid;
    FirebaseUser firebaseUser;

    TextView howManyClicks, howManyPoints, special_fich_text, diff_level;

    Boolean special;

    private Context context;
    private ArrayList<MeModelCard> modelArrayList;

    public AdapterCard(Context context, ArrayList<MeModelCard> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item, container, false);

        MeModelCard model = modelArrayList.get(position);

        howManyClicks = view.findViewById(R.id.how_many_click);
        howManyPoints = view.findViewById(R.id.manyPoint);
        special_fich_text = view.findViewById(R.id.special_fich);
        diff_level = view.findViewById(R.id.difflevel);

        howManyPoints.setText(model.getPoint());
        howManyClicks.setText(model.getManyClick());
        diff_level.setText(model.getDiffLevel());

        if (model.getSpecial_fich()){
            special_fich_text.setVisibility(view.INVISIBLE);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0){
                    Intent intent = new Intent(view.getContext(), GameScreen.class);
                    view.getContext().startActivity(intent);
                }
                else if (position == 1){
                    Intent intent = new Intent(view.getContext(), GameNorm.class);
                    view.getContext().startActivity(intent);
                }
                else if (position == 2){
                    Intent intent = new Intent(view.getContext(), GameHard.class);
                    view.getContext().startActivity(intent);
                }
            }
        });
        container.addView(view, position);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
