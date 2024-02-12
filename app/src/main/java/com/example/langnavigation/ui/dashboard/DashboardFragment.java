package com.example.langnavigation.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.langnavigation.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DashboardFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn_check = view.findViewById(R.id.btnCheck);
        String check = getResources().getString(R.string.btn_check);
        btn_check.setOnClickListener(v -> {
            Log.d("LANG_TEST", "check: " + check);
            Toast.makeText(getContext(), getResources().getString(R.string.btn_check), Toast.LENGTH_SHORT).show();

        });
    }
}