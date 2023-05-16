package com.example.mrfoodie;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText email, password ;

    Button SignUp, ButtonGoToLoginActivity;

    String EmailHolder, PasswordHolder ;

    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth ;

    Boolean EditTextStatus ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!isConnected())
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Internet Connection Alert")
                    .setMessage("Please check your Internet Connection")
                    .setPositiveButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        })
            .show();




        }
        else
        {
            Toast.makeText(MainActivity.this, "Welcome",Toast.LENGTH_LONG).show();
        }




        email = (EditText)findViewById(R.id.EditText_User_EmailID);
        password = (EditText)findViewById(R.id.EditText_User_Password);

        SignUp = (Button)findViewById(R.id.Button_SignUp);
        ButtonGoToLoginActivity = (Button)findViewById(R.id.Button_LoginActivity);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(MainActivity.this);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if(EditTextStatus){

                    UserRegistrationFunction();

                }
                else {

                    Toast.makeText(MainActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }

            }
        });

        ButtonGoToLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });



    }

    public void UserRegistrationFunction(){

        progressDialog.setMessage("Please Wait, We are Registering Your Data on Server");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(EmailHolder, PasswordHolder).
                addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(MainActivity.this,"User Registration Successfully",Toast.LENGTH_LONG).show();

                            firebaseAuth.signOut();

                        }else{

                            Toast.makeText(MainActivity.this,"Something Went Wrong.",Toast.LENGTH_LONG).show();
                        }

                        progressDialog.dismiss();

                    }
                });


    }

    public void CheckEditTextIsEmptyOrNot(){

        EmailHolder = email.getText().toString().trim();
        PasswordHolder = password.getText().toString().trim();

        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
        {

            EditTextStatus = false;

        }
        else {

            EditTextStatus = true ;
        }

    }
    private boolean isConnected()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}