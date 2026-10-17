package com.library.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.library.model.Book;
import com.library.model.BorrowRecord;
import com.library.model.Member;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles reading and writing data to JSON files using Gson.
 * Data is stored in a local "data/" folder.
 */
public class DataStorage {

    private static final String DATA_DIR      = "data/";
    private static final String BOOKS_FILE    = DATA_DIR + "books.json";
    private static final String MEMBERS_FILE  = DATA_DIR + "members.json";
    private static final String RECORDS_FILE  = DATA_DIR + "borrow_records.json";

    private final Gson gson;

    public DataStorage() {
        // Register LocalDate adapter so Gson handles Java 8+ dates
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class,
                        (com.google.gson.JsonSerializer<LocalDate>) (src, type, ctx) ->
                                new com.google.gson.JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class,
                        (com.google.gson.JsonDeserializer<LocalDate>) (json, type, ctx) ->
                                LocalDate.parse(json.getAsString()))
                .setPrettyPrinting()
                .create();

        // Ensure data directory exists
        new File(DATA_DIR).mkdirs();
    }

    // â”€â”€ Books â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    public List<Book> loadBooks() {
        return loadList(BOOKS_FILE, new TypeToken<List<Book>>(){}.getType());
    }

    public void saveBooks(List<Book> books) {
        saveList(BOOKS_FILE, books);
    }

    // â”€â”€ Members â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    public List<Member> loadMembers() {
        return loadList(MEMBERS_FILE, new TypeToken<List<Member>>(){}.getType());
    }

    public void saveMembers(List<Member> members) {
        saveList(MEMBERS_FILE, members);
    }

    // â”€â”€ Borrow Records â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    public List<BorrowRecord> loadRecords() {
        return loadList(RECORDS_FILE, new TypeToken<List<BorrowRecord>>(){}.getType());
    }

    public void saveRecords(List<BorrowRecord> records) {
        saveList(RECORDS_FILE, records);
    }

    // â”€â”€ Helpers â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    private <T> List<T> loadList(String filePath, Type type) {
        File file = new File(filePath);
        if (!file.exists()) return new ArrayList<>();
        try (Reader reader = new FileReader(file)) {
            List<T> list = gson.fromJson(reader, type);
            return list != null ? list : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("âš ï¸  Error reading " + filePath + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private <T> void saveList(String filePath, List<T> list) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(list, writer);
        } catch (IOException e) {
            System.err.println("âš ï¸  Error saving " + filePath + ": " + e.getMessage());
        }
    }
}
