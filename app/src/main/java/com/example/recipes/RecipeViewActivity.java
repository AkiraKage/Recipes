package com.example.recipes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.recipes.models.Recipe;

public class RecipeViewActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;

    private TextView titleTextView;
    private ImageView recipeImageView;
    private TextView ingredientsTextView;
    private TextView recipeTextView;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_view);

        titleTextView = findViewById(R.id.titletextview);
        recipeImageView = findViewById(R.id.recipeImageView);
        ingredientsTextView = findViewById(R.id.ingredientstextview);
        recipeTextView = findViewById(R.id.recipetextview);
        backButton = findViewById(R.id.backButton);

        // Imposta il listener per il pulsante "Indietro"
        backButton.setOnClickListener(v -> onBackPressed());

        // Verifica e richiedi permessi
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        } else {
            loadRecipeData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadRecipeData();
            } else {
                Toast.makeText(this, "Permission denied to read external storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadRecipeData() {
        Intent intent = getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra("selected_recipe");

        if (recipe != null) {
            titleTextView.setText(recipe.getFoodName());
            ingredientsTextView.setText("Ingredienti: " + String.join(", ", recipe.getIngredients()));
            recipeTextView.setText(recipe.getRecipe());

            String imageUrl = recipe.getImgURL();
            Log.d("RecipeViewActivity", "Image URL: " + imageUrl);  // Aggiungi un log per il debug

            if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(this)
                        .load(imageUrl)
                        .into(recipeImageView);
            } else {
                recipeImageView.setImageDrawable(null); // Nessuna immagine se URL è nullo o vuoto
            }
        }
    }
}
