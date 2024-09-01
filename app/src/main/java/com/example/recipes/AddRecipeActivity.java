package com.example.recipes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.recipes.database.RoomDB;
import com.example.recipes.models.Recipe;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class AddRecipeActivity extends AppCompatActivity {

    private Uri imageUri;
    private EditText titleinput;
    private EditText ingredientinput;
    private EditText recipeinput;
    private ImageButton imageButton;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        titleinput = findViewById(R.id.titleinput);
        ingredientinput = findViewById(R.id.ingredientinput);
        recipeinput = findViewById(R.id.recipeinput);
        imageButton = findViewById(R.id.imageButton);
        Button savebtn = findViewById(R.id.savebtn);

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Intent data = result.getData();
                imageUri = data.getData();
                imageButton.setImageURI(imageUri);
            }
        });

        imageButton.setOnClickListener(v -> openGallery());
        savebtn.setOnClickListener(v -> saveRecipe());
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private void saveRecipe() {
        String title = titleinput.getText().toString();
        String ingredients = ingredientinput.getText().toString();
        String recipeText = recipeinput.getText().toString();

        if (title.isEmpty() || ingredients.isEmpty() || recipeText.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Per favore, compila tutti i campi e seleziona un'immagine.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> ingredientList = Arrays.asList(ingredients.split(","));

        Recipe recipe = new Recipe();
        recipe.setFoodName(title);
        recipe.setIngredients(ingredientList);
        recipe.setRecipe(recipeText);
        recipe.setImgURL(imageUri.toString());
        RoomDB.getInstance(this).mainDAO().insert(recipe);

        Toast.makeText(this, "Ricetta salvata con successo!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
