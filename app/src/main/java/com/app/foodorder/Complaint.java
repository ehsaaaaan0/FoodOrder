package com.app.foodorder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.app.foodorder.ModelCLasses.RecomendationNComplaint;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Complaint#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Complaint extends Fragment {
    EditText getName, getEmail, getMessage;
    AppCompatButton submit;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Complaint() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Complaint.
     */
    // TODO: Rename and change types and number of parameters
    public static Complaint newInstance(String param1, String param2) {
        Complaint fragment = new Complaint();
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
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_complaint, container, false);
        getName = view.findViewById(R.id.getName);
        getEmail = view.findViewById(R.id.getEmail);
        getMessage = view.findViewById(R.id.getMessage);
        submit = view.findViewById(R.id.submitComplaint);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Complaint Notification", "Complaint Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = getName.getText().toString().trim();
                String email = getEmail.getText().toString().trim();
                String message = getMessage.getText().toString().trim();
                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String key = FirebaseDatabase.getInstance().getReference().child("Recomendation/Complaints").push().getKey();

                if (TextUtils.isEmpty(name)){
                    getName.setError("Please Enter Your Name");
                }else if (TextUtils.isEmpty(email)){
                    getEmail.setError("Please Enter Your Email");
                }else if (TextUtils.isEmpty(message)){
                    getMessage.setError("Please Explain What Type of issue you are facing");
                }else {

                    Intent intent = new Intent(getContext(), MainLoginScreen.class);
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);

                    PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0,
                            intent, PendingIntent.FLAG_MUTABLE);



                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "Complaint Notification");
                    builder.setContentTitle("Complaint");
                    builder.setContentText("We have Received Your Complaint, Will get Back To you ASAP");
                    builder.setSmallIcon(R.drawable.burger);
                    builder.setAutoCancel(true);
                    builder.setContentIntent(pendingIntent);

                    NotificationManagerCompat compat = NotificationManagerCompat.from(getContext());
                    compat.notify(3, builder.build());
                    RecomendationNComplaint model = new RecomendationNComplaint(name, email, message, id, key);
                    FirebaseDatabase.getInstance().getReference().child("recommendations and Complaints").child("complaints").child(key).setValue(model);
                    Toast.makeText(getContext(), "Sorry for the Issue!, We Have Received Your Complaint, will get Back To You ASAP.", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;
    }
}