package com.example.instagramclon.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.instagramclon.R;
import com.example.instagramclon.adapter.PostAdapter;
import com.example.instagramclon.databinding.ActivityFeedBinding;
import com.example.instagramclon.model.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Post> postArrayList;
    private ActivityFeedBinding feedBinding;
    PostAdapter postAdapter;
    private FirebaseUser user;

    Map<String,Object> hashMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedBinding=ActivityFeedBinding.inflate(getLayoutInflater());
        View view=feedBinding.getRoot();
        setContentView(view);

        getSupportActionBar().setTitle("Ana Sayfa");
        hashMap=new HashMap<>();

        postArrayList=new ArrayList<>();


        auth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        user=auth.getCurrentUser();




        getData();
        postAdapter=new PostAdapter(postArrayList);
        feedBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedBinding.recyclerView.setAdapter(postAdapter);



    }

    

    private void getData(){
        firebaseFirestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error!=null){
                    Toast.makeText(FeedActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                }
                if (value != null) {
                    for (DocumentSnapshot snapshot:value.getDocuments()) {
                        Map<String,Object> data=snapshot.getData();
                        //Casting
                        String userEmail=(String) data.get("useremail");
                        String command=(String) data.get("comment");
                        String downloadUrl=(String) data.get("downloadurl");
                        String fullName=(String) data.get("post_fullname");
                        String userName=(String) data.get("post_username");
                        String pphoto = (String) data.get("post_pphoto");

                        Date dataDate=snapshot.getDate("date",DocumentSnapshot.ServerTimestampBehavior.ESTIMATE);
                        SimpleDateFormat dateFormat=new SimpleDateFormat("dd.MM..yyyy HH:mm", Locale.getDefault());
                        String date=dateFormat.format(dataDate);

                        Post post=new Post(userEmail,command,downloadUrl,date,userName,fullName,pphoto);
                        postArrayList.add(post);
                    }
                    postAdapter.notifyDataSetChanged();


                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.option_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.add_post){

            Intent intent=new Intent(FeedActivity.this,UploadActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.settings_page) {
            Intent intentToSettings = new Intent(FeedActivity.this,SettingsActivity.class);
            startActivity(intentToSettings);
        }
        else if (item.getItemId()==R.id.signout){

            auth.signOut();

            Intent intentToMain=new Intent(FeedActivity.this,MainActivity.class);
            startActivity(intentToMain);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}