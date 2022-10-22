package com.app.foodorder.AdminPanel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.foodorder.ModelCLasses.RecomendationNComplaint;
import com.app.foodorder.R;
import com.app.foodorder.aAdapters.Admin_S_C;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link COmplaint_Admin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class COmplaint_Admin extends Fragment {
    RecyclerView rv;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public COmplaint_Admin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment COmplaint_Admin.
     */
    // TODO: Rename and change types and number of parameters
    public static COmplaint_Admin newInstance(String param1, String param2) {
        COmplaint_Admin fragment = new COmplaint_Admin();
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
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_c_omplaint__admin, container, false);
        rv = view.findViewById(R.id.admin_complaint_rv);
        ArrayList<RecomendationNComplaint> list = new ArrayList<>();
        Admin_S_C adapter = new Admin_S_C(list, "com");
        LinearLayoutManager liner = new LinearLayoutManager(getContext());
        rv.setLayoutManager(liner);

        FirebaseDatabase.getInstance().getReference().child("recommendations and Complaints").child("complaints").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        RecomendationNComplaint model = snapshot1.getValue(RecomendationNComplaint.class);
                        list.add(model);
                    }
                    rv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}