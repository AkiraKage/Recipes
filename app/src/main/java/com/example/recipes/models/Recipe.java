package com.example.recipes.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.example.recipes.database.Converters;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "recipes")
public class Recipe implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "foodName")
    String foodName;

    @ColumnInfo(name = "imgURL")
    String imgURL;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "ingredients")
    List<String> ingredients;

    @ColumnInfo(name = "recipe")
    String recipe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
}
