package com.app.foodorder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.app.foodorder.ModelCLasses.Register;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupTabFragment extends Fragment {
    EditText name_,email_,address_,pass_;
    AppCompatButton button_signuo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_signup_tab, container, false);

        name_ = root.findViewById(R.id.email);
        email_ = root.findViewById(R.id.mobile_num);
        address_ = root.findViewById(R.id.pass);
        pass_ = root.findViewById(R.id.conf_pass);
        button_signuo = root.findViewById(R.id.button_signuo);


        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Creating Your Account...!");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        button_signuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                String name = name_.getText().toString().trim();
                String email = email_.getText().toString().trim();
                String address = address_.getText().toString().trim();
                String password = pass_.getText().toString().trim();

                if (TextUtils.isEmpty(name)){
                    dialog.dismiss();
                    name_.setError("Please Enter Your Full name");
                }else if (TextUtils.isEmpty(email)){
                    dialog.dismiss();
                    email_.setError("Please Enter Your Email");
                }else if (TextUtils.isEmpty(address)){
                    dialog.dismiss();
                    address_.setError("Please Enter Your Address");
                }else if (TextUtils.isEmpty(password)){
                    dialog.dismiss();
                    pass_.setError("Please ENter Your Password");
                }else{
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            Register model = new Register(name, email, address, password, id);
                            FirebaseDatabase.getInstance().getReference().child("Users").child(id).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    dialog.dismiss();
                                    Toast.makeText(getContext(), "Registered SuccessFully", Toast.LENGTH_SHORT).show();
                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "My Notification");
                                    builder.setContentTitle("Your Account is Registered SuccessFully");
                                    builder.setContentText("Let's Order Some Delicious Food ;)");
                                    builder.setSmallIcon(R.drawable.burger);
                                    builder.setAutoCancel(true);

                                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
                                    managerCompat.notify(1, builder.build() );

                                    startActivity(new Intent(getContext(), MainActivity.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialog.dismiss();
                                    Toast.makeText(getContext(), "Unable to Save Your Record", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Unable to Create Your Account!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }



            }
        });


        return root;
    }
}