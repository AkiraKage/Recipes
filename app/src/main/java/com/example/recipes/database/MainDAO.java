package com.example.recipes.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.recipes.models.Recipe;

import java.util.List;

@Dao
public interface MainDAO {
    @Insert(onConflict = REPLACE)
    void insert(Recipe recipe);

    @Query("SELECT * FROM recipes ORDER BY foodName ASC")
    List<Recipe> getAll();

    @Query("UPDATE recipes SET foodName = :foodName, recipe = :recipe, imgURL = :imgURL WHERE id = :id ")
    void update(int id, String foodName, String recipe, String imgURL);

    @Delete
    void delete(Recipe recipe);
}