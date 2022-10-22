package com.app.foodorder;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.app.foodorder.ModelCLasses.AddFoodModel;
import com.app.foodorder.aAdapters.PopularFoodAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragmant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragmant extends Fragment {
    TextView address;
    LottieAnimationView open_profile;
    RecyclerView showpopular;
    LinearLayout pizza_show,burger_show,chicken_show,fries_show,ice_show,drink_show;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragmant() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragmant.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragmant newInstance(String param1, String param2) {
        HomeFragmant fragment = new HomeFragmant();
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
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_home_fragmant, container, false);

        address = view.findViewById(R.id.textView);
        open_profile = view.findViewById(R.id.open_profile);
        showpopular = view.findViewById(R.id.showpopular_);
        pizza_show = view.findViewById(R.id.pizza_show);
        burger_show = view.findViewById(R.id.burger_show);
        chicken_show = view.findViewById(R.id.chicken_show);
        ice_show = view.findViewById(R.id.ice_show);
        fries_show = view.findViewById(R.id.fries_show);
        drink_show = view.findViewById(R.id.drink_show);



        open_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Profile.class));
            }
        });
        ArrayList<AddFoodModel> list = new ArrayList<>();
        PopularFoodAdapter adapter = new PopularFoodAdapter(list, getContext());

        LinearLayoutManager liner  = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        showpopular.setLayoutManager(liner);
        FirebaseDatabase.getInstance().getReference().child("All Dishes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        AddFoodModel model = snapshot1.getValue(AddFoodModel.class);
                        list.add(model);
                    }
                    showpopular.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                address.setText(snapshot.child("address").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        pizza_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), single_dish_show.class);
                i.putExtra("dish", "pizza");
                startActivity(i);
            }
        });
        burger_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), single_dish_show.class);
                i.putExtra("dish", "burger");
                startActivity(i);
            }
        });
        chicken_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), single_dish_show.class);
                i.putExtra("dish", "chicken");
                startActivity(i);
            }
        });
        fries_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), single_dish_show.class);
                i.putExtra("dish", "fries");
                startActivity(i);
            }
        });
        ice_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), single_dish_show.class);
                i.putExtra("dish", "ice");
                startActivity(i);
            }
        });
        drink_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), single_dish_show.class);
                i.putExtra("dish", "drink");
                startActivity(i);
            }
        });







        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogPlus dialogPlus = DialogPlus.newDialog(getContext())
                        .setContentHolder(new ViewHolder(R.layout.activity_pop_up_address)).setExpanded(true, 1300)
                        .create();
                View v = dialogPlus.getHolderView();
                EditText new_address = v.findViewById(R.id.new_address);
                AppCompatButton save = v.findViewById(R.id.savesave);
                RadioButton address = v.findViewById(R.id.address_old);
                TextView edit_address = v.findViewById(R.id.textView15);

                FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                address.setText(snapshot.child("address").getValue(String.class));
                                new_address.setText(snapshot.child("address").getValue(String.class));
                                edit_address.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        save.setText("Save");
                                        address.setVisibility(View.GONE);
                                        new_address.setVisibility(View.VISIBLE);
                                    }
                                });
                                save.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child("address").setValue(new_address.getText().toString());
                                        dialogPlus.dismiss();
                                    }
                                });


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                dialogPlus.show();

            }
        });



        return view;
    }
}