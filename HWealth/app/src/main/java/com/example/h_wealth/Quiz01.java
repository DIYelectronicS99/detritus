package com.example.h_wealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.h_wealth.ui.home.HomeFragment;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class Quiz01 extends AppCompatActivity{
    public  StringBuilder s;
    public  String qno;
    int flag = 0;
    public  static int count = (int) HomeFragment.getData();



    public FirebaseFirestore userdb = FirebaseFirestore.getInstance();
    public DocumentReference countref = FirebaseFirestore.getInstance().collection("quiz").document("count");
        Button b1,b2,b3,bnext, bhome;
        TextView question;
        int click1 = 0;
        //Boolean click2 = false; so this is final copy , right???
    //yes
    //okay, don't type now and let me do some changes to main java files

        //Boolean click3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz01);


        question = findViewById(R.id.tvq);

        bnext = findViewById(R.id.btnq01);
        bhome = findViewById(R.id.btnhome);

        bnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Quiz01.this, Quiz02.class));
            }
        });

        bhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Quiz01.this, navDrawer.class));
            }
        });

        b1 = findViewById(R.id.an1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click1 = 1;
                String answer = b1.getText().toString();
                checkanswer(answer);

            }
        });

        b2 = findViewById(R.id.an2);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // click2 = true;
                String answer = b3.getText().toString();
                checkanswer(answer);
                Toast.makeText(Quiz01.this, "wrong answer !!", Toast.LENGTH_SHORT).show();
            }
        });

        b3 = findViewById(R.id.an3);

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // click3 = true;
                String answer = b3.getText().toString();
                checkanswer(answer);
                Toast.makeText(Quiz01.this, "wrong answer !!", Toast.LENGTH_SHORT).show();
            }
        });





    }

    /*public  void updatecounter(){

        DocumentReference documentReference = userdb.collection("quiz").document("count");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
             qno = (long) documentSnapshot.get("count");

            }
        });
        qno = qno + 1;

        countref.update("count", qno);
    }*/

    public void  updatequestion(){

        DocumentReference documentReference = userdb.collection("quiz").document("count");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                long  qno = (long) documentSnapshot.get("count");

                 s = new StringBuilder(100);
                s.append("q");
                s.append(qno);

            }
        });

        DocumentReference documentReference2 = userdb.collection("quiz").document(String.valueOf(s));
        documentReference2.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String opt1= documentSnapshot.getString("ans1");
                String opt2= documentSnapshot.getString("ans2");
                String opt3= documentSnapshot.getString("ans3");

                String newquestion  = documentSnapshot.getString("question");

                b1.setText(opt1);
                b2.setText(opt2);
                b3.setText(opt3);
                question.setText(newquestion);


            }
        });


    }

    void checkanswer(final String ans){

      //  updatequestion();

        DocumentReference documentReference = userdb.collection("quiz").document(String.valueOf("q1"));
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String trueans = documentSnapshot.getString("correct");
                if (ans.equals(trueans)){
                    flag++;
                    updatecounter();
                    Toast.makeText(Quiz01.this, "correct answer !!", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(Quiz01.this, "you stupid ?", Toast.LENGTH_SHORT).show();

                }
            }
        });
        b1.setEnabled(false);
        b2.setEnabled(false);
        b3.setEnabled(false);


    }


    public static  int getData(){
        return count;
    }
    public  void updatecounter(){
        DocumentReference documentReference = userdb.collection("quiz").document("count");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                qno =  documentSnapshot.get("count").toString();

            }
        });
        if(flag ==1)
        qno = qno + 1;

        countref.update("count", qno);
    }

}
