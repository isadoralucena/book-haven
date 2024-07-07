package com.example.bookhaven.services;

public interface ConvertData {
    <T> T  getData(String json, Class<T> className);
}
