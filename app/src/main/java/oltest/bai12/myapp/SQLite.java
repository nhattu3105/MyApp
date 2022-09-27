package oltest.bai12.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLite extends SQLiteOpenHelper {
    public static final String PERSON_TABLE = "PERSON_TABLE";
    public static final String PERSON_NAME = "PERSON_NAME";
    public static final String PERSON_AGE = "PERSON_AGE";
    public static final String ID = "ID";

    // name = person.db
    //factory(con tro) = null
    //version = 1
    public SQLite(@Nullable Context context) {
        super(context,"person.db",null,1);
    }

    // this is called the first time database is accessd. there should be code in here to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + PERSON_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PERSON_NAME + " TEXT, " + PERSON_AGE + " TEXT)";
        db.execSQL(createTable);
    }

    // this is called if  database version number changes. It prenvent previous users app from breaking when you change data
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(Person person)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        //ContentValues is similar top PutExtras in an Intent
        ContentValues cv = new ContentValues();

        cv.put(PERSON_NAME,person.getName());
        cv.put(PERSON_AGE,person.getAge());

        long insert = db.insert(PERSON_TABLE, null, cv);

        if (insert == -1)
        {
            return false;
        }else return true;


    }

    public List<Person> getAll()
    {
        List<Person> peopleList = new ArrayList<>();

        String queryString = "SELECT * FROM " + PERSON_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if (cursor.moveToFirst())
        {
            do {
                int personID = cursor.getInt(0);
                String personName = cursor.getString(1);
                String personAge = cursor.getString(2);

                Person person = new Person(personID,personName,personAge);
                peopleList.add(person);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return peopleList;
    }
    
    public boolean DeleteOne(Person person)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "DELETE FROM " + PERSON_TABLE + " WHERE " + ID + " = " + person.getId();
        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst())
        {
            return true;
        }else  return  false;
    }

    public boolean updateOne(Person person)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        //ContentValues updatedValues = new ContentValues();
        //updatedValues.put(PERSON_TABLE,person.getName() );
        //updatedValues.put(PERSON_AGE, person.getAge());
        //db.update(PERSON_TABLE,updatedValues,ID + " = ? ", new String[]{String.valueOf((person.getId()))});
        db.execSQL("UPDATE " + PERSON_TABLE +
                " SET PERSON_NAME = " + "'" +person.getName()+ "' "+ ", "
                + "PERSON_AGE = " + "'" + person.getAge() + "' "+
                "WHERE ID = " + "'" + person.getId() + "'");
        return true;


    }
}
