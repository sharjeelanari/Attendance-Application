package com.example.attendence;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Attendanceactivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    DatabaseReference databaseReference;
    AddAllFieldsAdapter attendanceAdapter;
    Button report;
    RecyclerView recyclerView;

    AddClassAdapter addClassAdapter ;


    public  static FragmentManager fragmentManager;

    Button addstudentbtn;
    DisplayAdapter displayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendanceactivity);

        recyclerView = findViewById(R.id.batchesofteachers);

        report = findViewById(R.id.report);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Attendanceactivity.this, AttendenceReport.class);
                startActivity(intent);
            }
        });

//        fragmentManager = getSupportFragmentManager();

//        if (findViewById(R.id.fragment_container)!=null){
//            if (savedInstanceState!=null){
//                return;
//            }
//        }

        databaseReference = FirebaseDatabase.getInstance().getReference("TeacherBatches");

        recyclerView.setLayoutManager(new LinearLayoutManager(Attendanceactivity.this));

        FirebaseRecyclerOptions<BatchModel>options=new FirebaseRecyclerOptions.Builder<BatchModel>().setQuery(databaseReference, BatchModel.class).build();
        addClassAdapter = new com.example.attendence.AddClassAdapter(options, Attendanceactivity.this);
        recyclerView.setAdapter(addClassAdapter);


//        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
//
//        DisplayclassFragment displayclassFragment=new DisplayclassFragment();
//        fragmentTransaction.add(R.id.fragment_container,displayclassFragment,null);
//        fragmentTransaction.commit();

        addstudentbtn=findViewById(R.id.addstudentbutton);
        addstudentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Attendanceactivity.this, AddAllFieldsActivity.class);
                startActivity(intent);

            }
        });



        String clickedbatch = getIntent().getStringExtra("batch");
        String clickedsubject = getIntent().getStringExtra("subject");

//        String teacheruid = FirebaseAuth
        String teachername = "getfromuid";

        viewPager2=(ViewPager2)findViewById(R.id.viewpager2);


        if (clickedbatch!= null) {

            DisplayclassFragment displayclassFragment = new DisplayclassFragment(clickedbatch);
            FragmentManager fm= getSupportFragmentManager();

            displayAdapter = new DisplayAdapter(fm, getLifecycle());
// TODO: Correct this recyclerview it should be in ViewPager Adapter(display class fragment)
            viewPager2.setAdapter(displayAdapter);
            DisplayclassFragment d = new DisplayclassFragment();

//            d.attendanceAdapter.startListening();

        }else{
//            attendanceAdapter.stopListening();
        }

    }

    @Override
    protected void onStart() {
//        attendanceAdapter.startListening();
        addClassAdapter.startListening();
        super.onStart();
    }

    @Override
    protected void onStop() {
//        attendanceAdapter.stopListening();
        addClassAdapter.stopListening();
        super.onStop();
    }
}