package com.example.attendence;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddClassAdapter extends FirebaseRecyclerAdapter<BatchModel, com.example.attendence.AddClassAdapter.viewHolder> {

    Context context;
    BatchModel addClassModel;



    public AddClassAdapter(@NonNull FirebaseRecyclerOptions<BatchModel> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull BatchModel model) {


        holder.showbatch.setText(model.getAddbatch());
//        holder.showsubject.setText(model.getSubject());

        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String batch = holder.showbatch.getText().toString();
//                String subject = holder.showsubject.getText().toString();

                Intent intent=new Intent(context,Attendanceactivity.class);
                intent.putExtra("batch", batch);
//                intent.putExtra("subject", subject);
                context.startActivity(intent);
//                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Attendence").child("Get teachers name").child(batch);

//                DisplayclassFragment displayclassFragment = new DisplayclassFragment();
//                displayclassFragment.databaseReference.setValue(db);
            }
        });


        holder.showrep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String batch = holder.showbatch.getText().toString();
                String subject = holder.showsubject.getText().toString();

                Intent intent1 = new Intent(context, AttendenceReport.class);
                intent1.putExtra("batch", batch);
                intent1.putExtra("subject", subject);
                context.startActivity(intent1);
            }
        });


    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sampleaddclassdisplay,parent,false);
        return new viewHolder(view);
    }


    public class viewHolder extends RecyclerView.ViewHolder {

        TextView showbatch;
        TextView showsubject;
        FrameLayout frameLayout;
        Button showrep;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            showbatch=itemView.findViewById(R.id.showbatch);
            showsubject=itemView.findViewById(R.id.showsubject);
            showrep = itemView.findViewById(R.id.showrep);

            frameLayout=itemView.findViewById(R.id.classfmlayout);

        }
    }
}
