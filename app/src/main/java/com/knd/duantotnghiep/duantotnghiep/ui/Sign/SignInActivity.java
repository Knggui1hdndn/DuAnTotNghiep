package com.knd.duantotnghiep.duantotnghiep.ui.Sign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.models.User;
import com.knd.duantotnghiep.duantotnghiep.ui.home.HomeActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    private EditText edEmailSignin;
    private EditText edPasswordSignin;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edEmailSignin = findViewById(R.id.edEmailSignin);
        edPasswordSignin = findViewById(R.id.edPasswordSignin);
        btnSignIn = findViewById(R.id.btnSignIn);

        getListUser();

        btnSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
            startActivity(intent);
        });

    }

    private void  getListUser(){
        ApiService.apiservice.getListUsers("","")
                .enqueue(new Callback<List<User>>() {

                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                        Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Toast.makeText(SignInActivity.this, "Call API error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });



    }
}