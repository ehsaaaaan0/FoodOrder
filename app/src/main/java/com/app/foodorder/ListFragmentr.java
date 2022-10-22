package com.app.foodorder;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.app.foodorder.ModelCLasses.AddFoodModel;
import com.app.foodorder.aAdapters.ALlListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragmentr#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragmentr extends Fragment {
    RecyclerView show_allthings;
    EditText search;
    ArrayList<AddFoodModel> list;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListFragmentr() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragmentr.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragmentr newInstance(String param1, String param2) {
        ListFragmentr fragment = new ListFragmentr();
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
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_list_fragmentr, container, false);

        show_allthings = view.findViewById(R.id.show_allthings);
        search = view.findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {


                    searchh(editable.toString());
//                    seachA(editable.toString());
                } else {
                    searchh("");
                }
            }
        });

        list = new ArrayList<>();
        ALlListAdapter adapter = new ALlListAdapter(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        show_allthings.setLayoutManager(linearLayoutManager);

        FirebaseDatabase.getInstance().getReference().child("All Dishes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        AddFoodModel model = snapshot1.getValue(AddFoodModel.class);
                        list.add(model);
                    }
                    show_allthings.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }

    private void searchh(String toString) {
        FirebaseDatabase.getInstance().getReference().child("All Dishes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren())
                {
                    list.clear();
                    for (DataSnapshot dss : dataSnapshot.getChildren())
                    {

                        AddFoodModel register = dss.getValue(AddFoodModel.class);
                        if (register.getDishName().toLowerCase().contains(toString.toLowerCase())){
                            list.add(register);
                        }


                    }
                    ALlListAdapter adapter = new ALlListAdapter(list);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    show_allthings.setLayoutManager(linearLayoutManager);
                    show_allthings.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}