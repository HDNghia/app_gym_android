package com.example.thanh.activity;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.Sampler;
import android.util.Log;
import android.widget.Toast;

import com.example.thanh.model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user_db";

    private static final String TABLE_USER = "User";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_HEIGHT = "height";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_AVT = "avt";
    private static final String COLUMN_WORKINGLEVEL = "workingLevel";
    private static final String COLUMN_COVERID = "coverId";
    private static final String COLUMN_WALLET = "wallet";
    private static final String COLUMN_ISBAN = "isBan";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_LASTACTIVE = "lastActive";
    private static final String COLUMN_BACKACCOUNT = "bankAccount";
    private static final String COLUMN_BANKNAME = "bankName";
    private static final String COLUMN_DESCRIPTION1 = "description1";
    private static final String COLUMN_DESCRIPTION2 = "description2";
    private static final String COLUMN_SPECIALIZE = "specialize";
    private static final String COLUMN_FIRSTNAME = "firstname";
    private static final String COLUMN_LASTNAME = "lastname";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONENUMBER = "phonenumber";
    private static final String COLUMN_ROLE = "role";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN___V = "__v";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_HEIGHT + " INTEGER, " +
                COLUMN_WEIGHT + " INTEGER, " +
                COLUMN_AVT + " TEXT, " +
                COLUMN_WORKINGLEVEL + " INTEGER, " +
                COLUMN_COVERID + " TEXT, " +
                COLUMN_WALLET + " INTEGER, " +
                COLUMN_ISBAN + " BOOLEAN, " +
                COLUMN_STATUS + " INTEGER, " +
                COLUMN_LASTACTIVE + " INTEGER, " +
                COLUMN_BACKACCOUNT + " TEXT, " +
                COLUMN_BANKNAME + " TEXT, " +
                COLUMN_DESCRIPTION1 + " TEXT, " +
                COLUMN_DESCRIPTION2 + " TEXT, " +
                COLUMN_SPECIALIZE + " TEXT, " +
                COLUMN_FIRSTNAME + " TEXT, " +
                COLUMN_LASTNAME + " TEXT, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_ROLE + " INTEGER, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN___V + " INTEGER " +
                ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        onCreate(db);
    }

    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, user.get_id());
        values.put(COLUMN_HEIGHT, user.getHeight());
        values.put(COLUMN_WEIGHT, user.getWeight());
        values.put(COLUMN_AVT, user.getAvt());
        values.put(COLUMN_WORKINGLEVEL, user.getWorkingLevel());
        values.put(COLUMN_COVERID, user.getCoverId());
        values.put(COLUMN_WALLET, user.getWallet());
        values.put(COLUMN_ISBAN, user.isBan());
        values.put(COLUMN_STATUS, user.getStatus());
        values.put(COLUMN_LASTACTIVE, user.getLastActive());
        values.put(COLUMN_BACKACCOUNT, user.getBankAccount());
        values.put(COLUMN_BANKNAME, user.getBankName());
        values.put(COLUMN_DESCRIPTION1, user.getDescription1());
        values.put(COLUMN_DESCRIPTION2, user.getDescription2());
        values.put(COLUMN_SPECIALIZE, user.getSpecialize());
        values.put(COLUMN_FIRSTNAME, user.getFirstname());
        values.put(COLUMN_LASTNAME, user.getLastname());
        values.put(COLUMN_GENDER, user.getGender());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_ROLE, user.getRole());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_ADDRESS, user.getAddress());
        values.put(COLUMN___V, user.get__v());
        Log.d("Bug", String.valueOf(values));
        db.insert(TABLE_USER, null, values);
        db.close();
        return 0;
    }

    @SuppressLint("Range")
    public List<User> getAllUser() {
        List<User> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);

        if (cursor.moveToFirst()) {
            do {
                User student = new User();
                student.set_id(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                student.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                student.setFirstname(cursor.getString(cursor.getColumnIndex(COLUMN_FIRSTNAME)));
                student.setAvt(cursor.getString(cursor.getColumnIndex(COLUMN_AVT)));
                studentList.add(student);
                Log.d("Tuan",  student.get_id() + "");
                Log.d("Tuan",  student.getEmail() + "");
                Log.d("Tuan",  student.getFirstname() + "");
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return studentList;
    }
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null
                ,null);
        db.close();
    }
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, COLUMN_ID + " = ?",
                new String[]{String.valueOf(user.get_id())});
        db.close();
    }
    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        // Các truy vấn để lấy thông tin của sinh viên với id tương ứng
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int userid = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));

            user = new User();
            user.setEmail(email);
            user.set_id(userid);
        }

        cursor.close();
        db.close();

        return user;
    }
}
