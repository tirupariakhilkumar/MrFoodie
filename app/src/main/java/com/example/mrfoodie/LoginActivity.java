package com.example.mrfoodie;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    EditText email, password ;

    String EmailHolder, PasswordHolder;

    Button Login,SignUP ;

    Boolean EditTextEmptyCheck;

    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.editText_email);
        password = (EditText)findViewById(R.id.editText_password);

        Login = (Button)findViewById(R.id.button_login);
        SignUP = (Button)findViewById(R.id.button_SignUP);

        progressDialog =  new ProgressDialog(LoginActivity.this);

        firebaseAuth = FirebaseAuth.getInstance();


        if(firebaseAuth.getCurrentUser() != null){

            finish();

            Intent intent = new Intent(LoginActivity.this, Welcome.class);
            startActivity(intent);
        }


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if(EditTextEmptyCheck)
                {

                    LoginFunction();

                }
                else {

                    Toast.makeText(LoginActivity.this, "Please Fill All the Fields", Toast.LENGTH_LONG).show();
                }


            }
        });

        SignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }

    public void CheckEditTextIsEmptyOrNot(){

        EmailHolder = email.getText().toString().trim();

        PasswordHolder = password.getText().toString().trim();

        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
        {

            EditTextEmptyCheck = false;

        }
        else {

            EditTextEmptyCheck = true ;

        }

    }

    public void LoginFunction(){

        progressDialog.setMessage("Please Wait");

        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(EmailHolder, PasswordHolder)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            progressDialog.dismiss();

                            finish();


                            Intent intent = new Intent(LoginActivity.this, Welcome.class);
                            startActivity(intent);
                        }
                        else {

                            progressDialog.dismiss();

                            Toast.makeText(LoginActivity.this, "Email or Password Not found, Please Try Again", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

}