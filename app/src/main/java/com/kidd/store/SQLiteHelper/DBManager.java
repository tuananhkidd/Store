package com.kidd.store.SQLiteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.kidd.store.models.Category;
import com.kidd.store.models.User;
import com.kidd.store.models.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TuanAnhKid on 3/30/2018.
 */

public class DBManager extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "store";
    private static final String TABLE_USER = "user";
    private static final String ID = "id";
    private static final String USER_NAME = "username";
    private static final String PASSWORD = "password";

    private static final String TABLE_BOOK = "book";
    private static final String TABLE_CATEGORY = "category";
    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private static final String PUBLISHER = "publisher";
    private static final String PRICE = "price";
    private static final String CATEGORY_ID = "categoryID";

    public final String TAG = getClass().getSimpleName();
    private Context context;

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
        Log.i(TAG, "DBManager: ");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table " + TABLE_USER + " ( "
                + ID + " NCHAR(36), "
                + USER_NAME + " TEXT, "
                + PASSWORD + " TEXT )";
        String sql1 = "create table " + TABLE_BOOK + " ( "
                + ID + " NCHAR(36), "
                + TITLE + " TEXT, "
                + AUTHOR + " TEXT, "
                + PUBLISHER + " TEXT, "
                + PRICE + " INT)";
        String sql2 = "create table " + TABLE_CATEGORY + " ( "
                + ID + " NCHAR(36), "
                + TITLE + " TEXT) ";

        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql2);

        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void register(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, user.getId());
        values.put(USER_NAME, user.getUsername());
        values.put(PASSWORD, user.getPassword());
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public boolean login(String username, String password) {
        String query = "select * from " + TABLE_USER + " where username like ? and password like ?";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        if (cursor.moveToFirst()) {
            return true;
        }
        return false;
    }

    public boolean check_user(String username) {
        String query = "select * from " + TABLE_USER + " where username like ?";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{username});
        if (cursor.moveToFirst()) {
            return false;
        }
        return true;
    }

    public void insertBook(Book book,String categoryID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, book.getId());
        values.put(TITLE, book.getTitle());
        values.put(AUTHOR, book.getAuthor());
        values.put(PUBLISHER, book.getPublisher());
        values.put(PRICE, book.getPrice());
        values.put(CATEGORY_ID, categoryID);

        db.insert(TABLE_BOOK, null, values);
        Log.i(TAG, "insertBook: success");
        db.close();
    }

    public void insertCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, category.getId());
        values.put(TITLE, category.getTitle());

        db.insert(TABLE_CATEGORY, null, values);
        Log.i(TAG, "insertCategory: success");
        db.close();
    }

    public List<Category> getAllCategory(){
        List<Category> lsCategory = new ArrayList<>();
        String query = "select * from " + TABLE_CATEGORY;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
               lsCategory.add(new Category(cursor.getString(0),cursor.getString(1)));
            } while (cursor.moveToNext());

        }
        return lsCategory;
    }

    public List<Book> getAllBook() {
        List<Book> lsBooks = new ArrayList<>();
        String query = "select * from " + TABLE_BOOK;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                lsBooks.add(new Book(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getInt(4)));
            } while (cursor.moveToNext());

        }
        return lsBooks;

    }

    public List<Book> getAllBook(String categoryID) {
        List<Book> lsBooks = new ArrayList<>();
        String query = "select * from " + TABLE_BOOK +" where categoryID = ?";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{categoryID});
        if (cursor.moveToFirst()) {
            do {
                lsBooks.add(new Book(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getInt(4)));
            } while (cursor.moveToNext());

        }
        return lsBooks;

    }
}
