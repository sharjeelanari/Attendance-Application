package com.example.attendence;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddAllFieldsActivity extends AppCompatActivity {

    EditText addstudentname;
    EditText addstudentbatch;
    EditText addstudentusn;
    EditText teachername;
    EditText sem;
    EditText subject;
    Button addstuddetailsbtn;

    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;


    Context context;

    com.example.attendence.StudentModel studentModel;
    com.example.attendence.SubjectModel subjectModel;
    com.example.attendence.TeacherModel teacherModel;
    com.example.attendence.BatchModel batchModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addallfieldsactivity);

        addstudentname = (EditText) findViewById(R.id.addstudentname);
        addstudentbatch = (EditText) findViewById(R.id.addstudentbatch);
        addstudentusn = (EditText) findViewById(R.id.addstudentusn);
        teachername = (EditText) findViewById(R.id.addteachername);
        sem = (EditText) findViewById(R.id.addsemester);
        subject = (EditText) findViewById(R.id.addsubject);
        addstuddetailsbtn = (Button) findViewById(R.id.addstuddetailsbtn);

        studentModel = new com.example.attendence.StudentModel();
        subjectModel = new com.example.attendence.SubjectModel();
        teacherModel = new com.example.attendence.TeacherModel();
        batchModel = new com.example.attendence.BatchModel();

        addstuddetailsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String saddname = addstudentname.getText().toString();
                String addbatch = addstudentbatch.getText().toString();
                String saddusn = addstudentusn.getText().toString();
                String teacher = teachername.getText().toString();
                String semester = sem.getText().toString();
                String sub = subject.getText().toString();

                if (saddname.trim().isEmpty() && addbatch.trim().isEmpty() && saddusn.trim().isEmpty() && teacher.trim().isEmpty() &&
                        semester.trim().isEmpty() && sub.trim().isEmpty()) {
                    Toast.makeText(context, "Fill all the fields", Toast.LENGTH_LONG);

                } else {
                    databaseReference = FirebaseDatabase.getInstance().getReference("attendancedetails").child(teacher);
                    databaseReference2 = databaseReference.child(addbatch);
                    databaseReference3 = databaseReference2.child("Names").child(saddname);
                    addtoattendancedatabase(saddname, addbatch, saddusn, teacher, semester, sub);
                    Intent intent = new Intent(com.example.attendence.AddAllFieldsActivity.this, Attendanceactivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            }
        });
    }

    private void addtoattendancedatabase(String saddname, String addbatch, String saddusn, String teacher, String semester, String sub) {
        studentModel.setAddstudentname(saddname);
        studentModel.setAddstudentusn(saddusn);
        batchModel.setAddbatch(addbatch);
        batchModel.setAddsem(semester);
        teacherModel.setTeachername(teacher);
        batchModel.setSubject(sub);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseReference.setValue(teacherModel);

                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference2.setValue(batchModel);
                        databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                databaseReference3.setValue(studentModel);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
