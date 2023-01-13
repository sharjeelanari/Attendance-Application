package com.example.attendence;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddAllFieldsAdapter extends FirebaseRecyclerAdapter<com.example.attendence.StudentModel, com.example.attendence.AddAllFieldsAdapter.viewHolder> {

    Context context;
    DatabaseReference db;


    public AddAllFieldsAdapter(@NonNull FirebaseRecyclerOptions<com.example.attendence.StudentModel> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull com.example.attendence.StudentModel model) {

        holder.showstudentname.setText(model.getAddstudentname());
        holder.showstudentusn.setText(model.getAddstudentusn());
        holder.batch.setText(model.getBatch());

        String batch = holder.batch.getText().toString();
        String studname = holder.showstudentname.getText().toString();
        db= FirebaseDatabase.getInstance().getReference("TeacherName").child(batch).child(studname);

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.samplestudattendancedisplay,parent,false);
        return  new viewHolder(view);
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView showstudentname;
        TextView showstudentusn, batch;
        RadioGroup presabs;
        RadioButton radioButton;

        public viewHolder(@NonNull View itemView) {
            super(itemView);


            showstudentname = itemView.findViewById(R.id.showstudentname);
            showstudentusn = itemView.findViewById(R.id.showstudentusn);
            presabs = itemView.findViewById(R.id.presabs);

            int id = presabs.getCheckedRadioButtonId();

            radioButton = itemView.findViewById(id);

            presabs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    radioButton = itemView.findViewById(checkedId);
                    String PA = radioButton.getText().toString();

                    sendtodb(PA);
                }
            });

        }

    }

    private void sendtodb(String PA){

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat s = new SimpleDateFormat("YYYYMMdd");


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, String> hashMap = new HashMap<>();
                String date = s.format(calendar.getTime());

                hashMap.put(date,PA);

                db.setValue(hashMap);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
