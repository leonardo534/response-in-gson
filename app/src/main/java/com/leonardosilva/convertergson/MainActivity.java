package com.leonardosilva.convertergson;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.leonardosilva.convertergson.api.DataService;
import com.leonardosilva.convertergson.model.Posts;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnRecuperar;
    private TextView textoResultado;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRecuperar = findViewById(R.id.btnRecuperarDados);
        textoResultado = findViewById(R.id.textoResultado);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPosts();
            }
        });

    }

    private void getPosts() {
        DataService dataService = retrofit.create(DataService.class);
        Call<List<Posts>> call = dataService.getPosts();

        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                if (!response.isSuccessful()) {
                    textoResultado.setText(response.message());
                }
                List<Posts> postsList = response.body();
                textoResultado.setText("");
                for (Posts posts : postsList) {
                    String content = "";

                    content += "\nUser id: " + posts.getUserId() + "\n";
                    content += "Id post: " + posts.getId() + "\n";
                    content += "Title: " + posts.getTitle() + "\n";
                    content += "Post: " + posts.getBody() + "\n";

                    textoResultado.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                textoResultado.setText(t.getMessage());
            }
        });
    }

}
