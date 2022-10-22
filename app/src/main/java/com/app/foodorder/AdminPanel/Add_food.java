package com.app.foodorder.AdminPanel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.foodorder.AdminDashboard;
import com.app.foodorder.ModelCLasses.AddFoodModel;
import com.app.foodorder.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Objects;
import java.util.Random;

public class Add_food extends AppCompatActivity {
    Spinner dropdown;
    String category;
    EditText dishtitle,dishDescription_,price,time_prepair;
    ImageView image1,image2,image3;
    LinearLayout uplaod_images;
    Uri uri, uri2, uri3;
    AppCompatButton submit_dish;
    ProgressDialog dialog;
    int reqcode=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        dropdown = findViewById(R.id.spinner);
        uplaod_images = findViewById(R.id.uplaod_images);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        submit_dish = findViewById(R.id.submit_dish);
        time_prepair = findViewById(R.id.time_prepair);
        dishtitle = findViewById(R.id.dishtitle);
        dishDescription_ = findViewById(R.id.dishDescription);
        price = findViewById(R.id.price);
        dialog = new ProgressDialog(Add_food.this);
        dialog.setMessage("Uploading Dish Please Wait...!");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Dexter.withContext(Add_food.this)
                //Dexter.withActivity(MainActivity.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        reqcode=1;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();



        submit_dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadDIsh();
            }
        });


        uplaod_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reqcode == 1) {
                    Intent intent = new Intent(Intent.ACTION_PICK);

                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);

                }else if (reqcode==2){
                    Intent intent = new Intent(Intent.ACTION_PICK);

                    intent.setType("image/*");

//                                startActivityForResult(Intent.createChooser( intent,"Select Image from here..."), PICK_IMAGE_REQUEST);
                    startActivityForResult(Intent.createChooser(intent, "Select Image"), 2);
//                                startActivityForResult(Intent.createChooser(intent, "Select Image", 1));

                }else if (reqcode==3){
                    Intent intent = new Intent(Intent.ACTION_PICK);

                    intent.setType("image/*");

//                                startActivityForResult(Intent.createChooser( intent,"Select Image from here..."), PICK_IMAGE_REQUEST);
                    startActivityForResult(Intent.createChooser(intent, "Select Image"), 3);
//                                startActivityForResult(Intent.createChooser(intent, "Select Image", 1));

                }else if (reqcode==4){
                    Toast.makeText(Add_food.this, "Maximum Images Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });



        String[] items = new String[]{"Please Select Categories","Pizza", "Burger", "Chicken" , "Fries" ,"Ice Cream" , "Drink"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        category = "null";
                        break;
                    case 1:
                        category = "pizza";
                        break;
                    case 2:
                        category = "burger";
                        break;
                    case 3:
                        category = "chicken";
                        break;
                    case 4:
                        category = "fries";
                        break;
                    case 5:
                        category = "icecream";
                        break;
                    case 6:
                        category = "drink";
                        break;
                    default:
                        category  ="null";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (data.getData()!=null){
                uri = data.getData();
                image1.setImageURI(uri);
                image2.setVisibility(View.VISIBLE);
                reqcode++;
            }
        }if (requestCode == 2 && resultCode == RESULT_OK && null !=data){
            super.onActivityResult(requestCode, resultCode, data);
            if (data.getData()!=null){
                uri2 = data.getData();
                image2.setImageURI(uri2);
                image3.setVisibility(View.VISIBLE);
                reqcode++;
            }
        }if (requestCode == 3 && resultCode == RESULT_OK && null !=data){
            super.onActivityResult(requestCode, resultCode, data);
            if (data.getData()!=null){
                uri3 = data.getData();
                image3.setImageURI(uri3);
                reqcode++;
            }
        }

    }

    private void uploadDIsh() {
        dialog.show();
        String dishTitle = dishtitle.getText().toString().trim();
        String dishDescription = dishDescription_.getText().toString().trim();
        String dishPrice = price.getText().toString().trim();
        String cat = category;
        String time = time_prepair.getText().toString().trim();
        if (TextUtils.isEmpty(dishTitle)){
            dialog.dismiss();
            dishtitle.setError("Please Enter Dish Name");
        }else if (TextUtils.isEmpty(dishDescription)){
            dialog.dismiss();
            dishDescription_.setError("Please Enter Some Description of dish");
        }else if (TextUtils.isEmpty(dishPrice)){
            dialog.dismiss();
            price.setError("Please Enter Price of Dish");
        }else if (Objects.equals(cat, "null")){
            dialog.dismiss();
            Toast.makeText(this, "Please Select Category of Dish from dropdown", Toast.LENGTH_SHORT).show();
        }else if (reqcode<3){
            dialog.dismiss();
            Toast.makeText(this, "Please add 3 Images", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(time)){
            dialog.dismiss();
            time_prepair.setError("Please Enter Estimate Time");
        }else{

            final StorageReference reference =  FirebaseStorage.getInstance().getReference().child("Dish Images").child(new Random().nextInt(610000000) + 200000+"");
            final StorageReference reference2 = FirebaseStorage.getInstance().getReference().child("Dish Images").child(new Random().nextInt(610000000) + 200000+"");
            final StorageReference reference3 = FirebaseStorage.getInstance().getReference().child("Dish Images").child(new Random().nextInt(610000000) + 200000+"");
            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String image1 = uri.toString();
                            reference2.putFile(uri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                               reference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                   @Override
                                   public void onSuccess(Uri uri) {
                                       String image2 = uri.toString();
                                       reference3.putFile(uri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                           @Override
                                           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                               reference3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                   @Override
                                                   public void onSuccess(Uri uri) {
                                                       String image3 = uri.toString();
                                                       String key = FirebaseDatabase.getInstance().getReference().child("food").push().getKey();
                                                       AddFoodModel model = new AddFoodModel(image1, image2, image3, dishTitle, dishDescription, dishPrice, category,key,time);
                                                       FirebaseDatabase.getInstance().getReference().child(category).child(key).setValue(model);
                                                       FirebaseDatabase.getInstance().getReference().child("All Dishes").child(key).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                           @Override
                                                           public void onSuccess(Void unused) {
                                                               dialog.dismiss();
                                                               Toast.makeText(Add_food.this, "Dish Added SuccessFully", Toast.LENGTH_SHORT).show();
                                                               startActivity(new Intent(Add_food.this, AdminDashboard.class));
                                                           }
                                                       });
                                                   }
                                               });
                                           }
                                       });
                                   }
                               });
                                }
                            });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(Add_food.this, "Unable to Upload FIle Please Try Again Latter", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

}