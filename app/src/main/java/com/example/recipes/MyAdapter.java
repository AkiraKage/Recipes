package com.example.recipes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipes.database.RoomDB;
import com.example.recipes.models.Recipe;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Recipe> recipeList;
    private Context context;

    public MyAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.titleoutput.setText(recipe.getFoodName());
        holder.descriptionoutput.setText(recipe.getRecipe());

        Glide.with(context)
                .load(recipe.getImgURL())
                .into(holder.previewImage);

        holder.itemView.setOnLongClickListener(v -> {
            showDeleteConfirmationDialog(recipe, position);
            return true;
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeViewActivity.class);
            intent.putExtra("selected_recipe", recipe);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    private void showDeleteConfirmationDialog(Recipe recipe, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Conferma Eliminazione")
                .setMessage("Sei sicuro di voler eliminare questa ricetta?")
                .setPositiveButton("Elimina", (dialog, which) -> deleteRecipe(recipe, position))
                .setNegativeButton("Annulla", null)
                .show();
    }

    private void deleteRecipe(Recipe recipe, int position) {
        new Thread(() -> {
            RoomDB.getInstance(context).mainDAO().delete(recipe);
            ((AppCompatActivity) context).runOnUiThread(() -> {
                recipeList.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(context, "Ricetta eliminata con successo!", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleoutput;
        TextView descriptionoutput;
        ImageView previewImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleoutput = itemView.findViewById(R.id.titleoutput);
            descriptionoutput = itemView.findViewById(R.id.descriptionoutput);
            previewImage = itemView.findViewById(R.id.previewImage);
        }
    }
}
