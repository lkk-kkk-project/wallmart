package com.example.walmart.repo;

import com.example.walmart.model.Product;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ProductRepository {
    private final Map<Long, Product> map = new LinkedHashMap<>();
    private long idGen = 1;
    private final String filePath = "products.json";
    private final Gson gson = new Gson();

    public ProductRepository() {
        load();
    }

    public List<Product> findAll() {
        return new ArrayList<>(map.values());
    }

    public Product save(Product p) {
        if (p.getId() == 0) p.setId(idGen++);
        map.put(p.getId(), p);
        saveToFile();
        return p;
    }

    private void saveToFile() {
        try (Writer w = new FileWriter(filePath)) {
            gson.toJson(map.values(), w);
        } catch (IOException e) {
            System.err.println("Failed to save products: " + e.getMessage());
        }
    }

    private void load() {
        try {
            if (!Files.exists(Paths.get(filePath))) return;
            String json = new String(Files.readAllBytes(Paths.get(filePath)));
            Type listType = new TypeToken<List<Product>>(){}.getType();
            List<Product> list = gson.fromJson(json, listType);
            if (list != null) {
                for (Product p : list) {
                    map.put(p.getId(), p);
                    idGen = Math.max(idGen, p.getId() + 1);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load products: " + e.getMessage());
        }
    }
}