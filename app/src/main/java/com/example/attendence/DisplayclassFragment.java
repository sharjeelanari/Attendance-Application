package com.example.attendence;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DisplayclassFragment extends Fragment {

    DatabaseReference databaseReference;
    BatchModel addClassModel;
    com.example.attendence.AddClassAdapter addClassAdapter;

    Button addstudentbutton;

//    Button addclassbtn;

    RecyclerView recyclerView;
    AddAllFieldsAdapter attendanceAdapter;



    public DisplayclassFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_displayclass, container, false);




//        addstudentbutton=view.findViewById(R.id.addstudentbutton);
        recyclerView=view.findViewById(R.id.classdisplayrecview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        //TODO: Get uid from the teacher details from the teacher details db.
        //TODO: then get teacher name of that uid.

//        String teacheruid = FirebaseAuth.
        String teachername = "fetchfromuid";


//TODO: clicked batch name lana h yaha kisi tarah.
        databaseReference = FirebaseDatabase.getInstance().getReference("Teacher").child("ClickedBatch");
        FirebaseRecyclerOptions<StudentModel> options = new FirebaseRecyclerOptions.Builder<StudentModel>().setQuery(databaseReference, StudentModel.class).build();
        attendanceAdapter = new AddAllFieldsAdapter(options);
        recyclerView.setAdapter(attendanceAdapter);


       return view;

    }

    @Override
    public void onStart() {
        attendanceAdapter.startListening();
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        attendanceAdapter.stopListening();
//        addClassAdapter.stopListening();
    }
}