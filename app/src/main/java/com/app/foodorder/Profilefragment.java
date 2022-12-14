package com.app.foodorder;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.foodorder.ModelCLasses.PlaceOrderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profilefragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profilefragment extends Fragment {
    RecyclerView rv_past;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Profilefragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profilefragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Profilefragment newInstance(String param1, String param2) {
        Profilefragment fragment = new Profilefragment();
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
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_profilefragment, container, false);
        rv_past = view.findViewById(R.id.past_rc);

        ArrayList<PlaceOrderModel>list = new ArrayList<>();
        PastOrderAdapter adapter = new PastOrderAdapter(list, "user");
        LinearLayoutManager liner = new LinearLayoutManager(getContext());
        rv_past.setLayoutManager(liner);
        rv_past.setNestedScrollingEnabled(false);
        rv_past.setHasFixedSize(true);

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Previous Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        PlaceOrderModel model = snapshot1.getValue(PlaceOrderModel.class);
                        list.add(model);
                    }
                    rv_past.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
//                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
//                        AddFoodModel model = snapshot1.getValue(AddFoodModel.class);
//                        list.add(model);
//                    }
//                    rv_past.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}