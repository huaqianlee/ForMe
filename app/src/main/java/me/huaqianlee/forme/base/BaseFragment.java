package me.huaqianlee.forme.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import me.huaqianlee.forme.MainActivity;

public class BaseFragment extends Fragment {
    public static MainActivity funcMainActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        funcMainActivity = (MainActivity) getActivity();
    }

}
