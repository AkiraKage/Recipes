package com.example.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipes.database.RoomDB;
import com.example.recipes.models.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerHome;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerHome = findViewById(R.id.recycler_home);
        recyclerHome.setLayoutManager(new LinearLayoutManager(this));

        // Load recipes from Room database
        loadRecipes();

        FloatingActionButton fabAddBtn = findViewById(R.id.fab_add);
        fabAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddRecipeActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecipes();
    }

    private void loadRecipes() {
        new Thread(() -> {
            List<Recipe> recipes = RoomDB.getInstance(this).mainDAO().getAll();
            runOnUiThread(() -> {
                myAdapter = new MyAdapter(this, recipes);
                recyclerHome.setAdapter(myAdapter);
            });
        }).start();
    }
}
