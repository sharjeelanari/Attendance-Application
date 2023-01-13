package com.example.attendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class AttendenceReport extends AppCompatActivity {
    RecyclerView studentreport, attendencereport, recyclerView;
    EditText from, to;

    private int day, year, month;
    Calendar calendar;

    DatabaseReference studentrepref, databaseReference;

    StudentRepAdap studentRepAdap;
    AttndnceRepAdap attndnceRepAdap;
    AttndnceAdap attndnceAdap;

    AddClassAdapter addClassAdapter;

//TODO: (20/07) Make teacher login Page, fetch data from firebase to recycler views below, create method to export xl report file
//TODO: Make a display message at the end of every attendance in which give two buttons (submit and review), make for loop to get seven days
//TODO: attendance in recycler view below and this should also have a custom seven days button.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_report);


        studentreport = findViewById(R.id.studentreport);
        attendencereport = findViewById(R.id.studattendencerep);
        recyclerView = findViewById(R.id.recview);
        from = findViewById(R.id.attendrepfrom);
        to = findViewById(R.id.attendrepto);


        String batch = getIntent().getStringExtra("batch");
        String uid = "get uid of teacher and then get name";
        studentrepref = FirebaseDatabase.getInstance().getReference(uid).child(batch);

        databaseReference = FirebaseDatabase.getInstance().getReference();


        recyclerView.setLayoutManager(new LinearLayoutManager(AttendenceReport.this));

        FirebaseRecyclerOptions<BatchModel>options3=new FirebaseRecyclerOptions.Builder<BatchModel>().setQuery(databaseReference, BatchModel.class).build();
        addClassAdapter = new com.example.attendence.AddClassAdapter(options3, AttendenceReport.this);
        recyclerView.setAdapter(addClassAdapter);

//        DisplayclassFragment displayclassFragment = new DisplayclassFragment();


//        TODO: Showing attendence in the app in recycler view (Refer notes).

        StudentModel studentModel = new StudentModel();
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<StudentModel>().setQuery(studentrepref, StudentModel.class)
                .build();
        studentreport.setLayoutManager(new LinearLayoutManager(AttendenceReport.this));
        studentRepAdap = new StudentRepAdap(options);
        studentreport.setAdapter(studentRepAdap);

        FirebaseRecyclerOptions options1 = new FirebaseRecyclerOptions.Builder<AttndnceRepModel>().setQuery(studentrepref, AttndnceRepModel.class)
                .build();
        attendencereport.setLayoutManager(new LinearLayoutManager(AttendenceReport.this));
        attndnceRepAdap = new AttndnceRepAdap(options1, AttendenceReport.this);
        attendencereport.setAdapter(attndnceRepAdap);


        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(2);
            }
        });

//        if (from!=null && to != null) {
//            String To = to.getText().toString();
//            String From = from.getText().toString();
//            for (String frm = From; frm == To)
//                attndnceRepAdap.databaseReference.child(frm);
//        }

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("Attendence Report");

        HSSFRow hssfRow = hssfSheet.createRow(0);
        HSSFCell hssfCell = hssfRow.createCell(0);


        File filePath = new File(Environment.getExternalStorageDirectory() + "/Name.xls");
        try {
            if (!filePath.exists()){
                filePath.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            hssfWorkbook.write(fileOutputStream);

            if (fileOutputStream!=null){
                fileOutputStream.flush();
                fileOutputStream.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    protected Dialog onCreateDialog(int id) {
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        if (id == 1) {
            return new DatePickerDialog(this, dateSetListener, year, month, day);
        }else if (id == 2){
            return new DatePickerDialog(this, dateSetListener1, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dateSetListener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            ShowfromDate(dayOfMonth, month,year);
        }
    };

    private void ShowfromDate(int day, int month, int year){
        String date = day + "/" + month + "/" + year;
        from.setText(date);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            ShowtoDate(dayOfMonth, month, year);
        }
    };

    private void ShowtoDate(int day, int month, int year){
        String date = day + "/" + month + "/" + year;
        to.setText(date);
    }


    private class studentmodel{
        String name, usn, totalclasses, attended, percentage;

        private studentmodel(){

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsn() {
            return usn;
        }

        public void setUsn(String usn) {
            this.usn = usn;
        }

        public String getTotalclasses() {
            return totalclasses;
        }

        public void setTotalclasses(String totalclasses) {
            this.totalclasses = totalclasses;
        }

        public String getAttended() {
            return attended;
        }

        public void setAttended(String attended) {
            this.attended = attended;
        }

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }
    }

    private class AttndnceRepModel{
        String name, usn;

        private AttndnceRepModel(){

        }

        private AttndnceRepModel(String name, String usn){
            this.name = name;
            this.usn = usn;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsn() {
            return usn;
        }

        public void setUsn(String usn) {
            this.usn = usn;
        }
    }

    private class AttndnceModel{
        Long Date;
        String Present;

        private AttndnceModel(){

        }
        private AttndnceModel(Long Date, String Present){
            this.Date = Date;
            this.Present = Present;
        }

        public Long getDate() {
            return Date;
        }

        public void setDate(Long date) {
            Date = date;
        }

        public String getPresent() {
            return Present;
        }

        public void setPresent(String present) {
            Present = present;
        }
    }

    private class StudentRepAdap extends FirebaseRecyclerAdapter<studentmodel, StudentRepAdap.viewHolder> {

        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public StudentRepAdap(@NonNull FirebaseRecyclerOptions<studentmodel> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull studentmodel model) {
            holder.name.setText(model.getName());
            holder.usn.setText(model.getUsn());
            holder.totalclasses.setText(model.getTotalclasses());
            holder.attended.setText(model.getAttended());
            holder.percentage.setText(model.getPercentage());
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_stud_attndc_rep,parent,false);
            return new viewHolder(v);
        }

        public class viewHolder extends RecyclerView.ViewHolder {

            TextView name, usn, totalclasses, attended, percentage;
            public viewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.studnm);
                usn = itemView.findViewById(R.id.usnstud);
                totalclasses = itemView.findViewById(R.id.totalclasses);
                attended = itemView.findViewById(R.id.attended);
                percentage = itemView.findViewById(R.id.percentage);
            }
        }
    }

    private class AttndnceAdap extends FirebaseRecyclerAdapter<AttndnceModel, AttndnceAdap.viewHolder>{

        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public AttndnceAdap(@NonNull FirebaseRecyclerOptions<AttndnceModel> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull AttndnceAdap.viewHolder holder, int position, @NonNull AttndnceModel model) {
            holder.AttendenceDate.setText(model.getDate().toString());
            holder.DayAttendence.setText(model.getPresent());
        }

        @NonNull
        @Override
        public AttndnceAdap.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_attndnce_rec, parent, false);
            return  new viewHolder(view);
        }

        public class viewHolder extends RecyclerView.ViewHolder {

            TextView AttendenceDate, DayAttendence;
            public viewHolder(@NonNull View itemView) {
                super(itemView);

                AttendenceDate = itemView.findViewById(R.id.attndncedate);
                DayAttendence = itemView.findViewById(R.id.dayattndnce);
            }
        }
    }

    private class AttndnceRepAdap extends  FirebaseRecyclerAdapter<AttndnceRepModel, AttndnceRepAdap.viewHolder> {
        DatabaseReference databaseReference;
        Context context;
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public AttndnceRepAdap(@NonNull FirebaseRecyclerOptions<AttndnceRepModel> options, Context context) {
            super(options);
            this.context = context;
        }

        @Override
        protected void onBindViewHolder(@NonNull AttndnceRepAdap.viewHolder holder, int position, @NonNull AttndnceRepModel model) {
            String userid = "Get the uid of the teacher and then get name for db reference";
            String batch = getIntent().getStringExtra("batch");

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference(userid).child(batch).child("Date");

            holder.name.setText(model.getName());
            holder.usn.setText(model.getUsn());
            //TODO:  Try adding a for loop for showing the aattendance of specific time int here by getting it from the listner here in the adapter only.

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setStackFromEnd(true);

            holder.attndnce.setLayoutManager(layoutManager);
            holder.attndnce.setHasFixedSize(true);

            FirebaseRecyclerOptions recyclerOptions = new FirebaseRecyclerOptions.Builder<AttndnceModel>().setQuery(reference, AttndnceModel.class)
                    .build();
            AttndnceAdap attndnceAdap = new AttndnceAdap(recyclerOptions);
            holder.attndnce.setAdapter(attndnceAdap);

        }

        @NonNull
        @Override
        public AttndnceRepAdap.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_stud_attndc_rep,parent,false);
            return new viewHolder(view);
        }

        public class viewHolder extends RecyclerView.ViewHolder {

            TextView name, usn, P_A;
            RecyclerView attndnce;
            public viewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.studentname);
                usn = itemView.findViewById(R.id.studentusn);
                P_A = itemView.findViewById(R.id.attndnce);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        addClassAdapter.startListening();
        attndnceRepAdap.startListening();
        studentRepAdap.startListening();
        attndnceAdap.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        addClassAdapter.startListening();
        attndnceRepAdap.startListening();
        studentRepAdap.startListening();
        attndnceAdap.startListening();
    }
}
