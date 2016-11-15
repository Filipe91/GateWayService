package com.gateway.filiperodrigues.gatewayservice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.*;


public class RealTimeDatabase extends AppCompatActivity {

    // Firebase Realtime Database
    TextView mConditionTextView;
    Button mButtonSol;
    Integer int2 = 0;

    // Obterá uma referencia root da árvore JSON no Firebase
    DatabaseReference mRootRef = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();
    // Child Reference, cria condições debaixo do Root
    DatabaseReference mConditionRef = mRootRef.child("condition "+int2);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime_database);

        // Firebase Realtime Database
        // Get UI elements
        mConditionTextView = (TextView) findViewById(R.id.textViewCondition);
        mButtonSol = (Button)findViewById(R.id.buttonSol);
    }

    @Override
    protected void onStart() {
        super.onStart();
        int2 = 0;

        mButtonSol.setOnClickListener(new View.OnClickListener() {
            ValueEventListener listener = null;
            @Override
            public void onClick(View v) {

                // remove a referencia do child anterior
                if(listener != null) {
                    mConditionRef.removeEventListener(listener);
                }

                // criação de um child
                mConditionRef = mRootRef.child("condition "+int2);
                // dar um valor ao child criado
                mConditionRef.setValue("condition "+int2);

                // cria um listener, adicona um evento listener no "mConditionRef"
                listener = mConditionRef.addValueEventListener(new ValueEventListener() {
                    @Override // sempre que os calores do "condition" forem atualizados na base de dados
                    public void onDataChange(DataSnapshot dataSnapshot) { // dados voltaram como DataSnapshot
                        // receber um DataSnapshot e sincroniza-lo com o textview
                        String text = dataSnapshot.getValue(String.class);
                        // TUDO textview SE SINCRONIZA AQUI
                        mConditionTextView.setText(text);
                    }
                    @Override // se acontecer algum erro
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                int2++;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
