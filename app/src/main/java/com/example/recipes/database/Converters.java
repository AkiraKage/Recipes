package com.example.recipes.database;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Converters {

    @TypeConverter
    public String fromList(List<String> ingredients) {
        return String.join(",", ingredients);
    }

    @TypeConverter
    public List<String> toList(String data) {
        return Arrays.stream(data.split(",")).collect(Collectors.toList());
    }
}