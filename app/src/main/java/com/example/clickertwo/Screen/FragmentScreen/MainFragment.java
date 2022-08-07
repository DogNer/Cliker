package com.example.clickertwo.Screen.FragmentScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Dimension;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;
import androidx.viewpager.widget.ViewPager;

import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clickertwo.CardView.AdapterCard;
import com.example.clickertwo.CardView.MeModelCard;
import com.example.clickertwo.R;
import com.example.clickertwo.Screen.GameScreen;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    AppCompatButton btnPlay;

    ArrayList<MeModelCard> modelArrayList;
    AdapterCard myAdapter;
    ViewPager viewPager;

    TextView count_point, countLife;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        loadCard();

        count_point = view.findViewById(R.id.countPoint);
        countLife = view.findViewById(R.id.count_life);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int age = sharedPreferences.getInt("score", 0);
        int life = sharedPreferences.getInt("life", 7);

        countLife.setText(String.valueOf(life));
        count_point.setText(String.valueOf(age));

        return view;
    }

    private void loadCard() {
        modelArrayList = new ArrayList<>();

        modelArrayList.add(new MeModelCard(
                "Get 5\n" + "points", "Up to 50 clicks\n" +
                "10 second", true, "Easy"
        ));

        modelArrayList.add(new MeModelCard(
                "Get 10\n" + "points", "Up to 60 clicks\n" +
                "20 second", false, "Norm"
        ));

        modelArrayList.add(new MeModelCard(
                "Get 15\n" + "points", "Up to 100 clicks\n" +
                "25 second", false, "Difficult"
        ));

        DisplayMetrics dm = getResources().getDisplayMetrics();
        float fwidth = dm.density * dm.widthPixels;

        myAdapter = new AdapterCard(getActivity(), modelArrayList);
        viewPager.setAdapter(myAdapter);
        viewPager.setPadding(0, 50, 0, 0);
    }
}