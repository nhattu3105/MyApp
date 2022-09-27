package oltest.bai12.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PersonAdapter.MenulistClick {
    private RecyclerView list;
    private Button add,edit,delete;
    private EditText name , age;
    private String Name,Age;

    private PersonAdapter personAdapter;
    private ArrayAdapter arrayAdapter;
    private SQLite sqLite;
    Person person2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLite = new SQLite(MainActivity.this);

        init();  // anh xa
        listA(); // get all data from sql and show it on RCW


        // add an item in database SQL
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person person = new Person(-1,Name = name.getText().toString(),Age = age.getText().toString());
                sqLite = new SQLite(MainActivity.this);
                boolean success = sqLite.addOne(person);
                listA();
                name.setText(null);
                age.setText(null);
                Toast.makeText(MainActivity.this,"success = "+person.toString(),Toast.LENGTH_LONG).show();

            }
        });

        //delete an item in database
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean check1 = name.getText().toString().isEmpty();
                Boolean check2 = age.getText().toString().isEmpty();
                if (((check1 == true) || ( check2 == true)) )
                {
                    Toast.makeText(MainActivity.this,"content cannot be left blank don't fix content",Toast.LENGTH_LONG).show();
                    name.setText(null);
                    age.setText(null);
                }
                else {
                    sqLite = new SQLite(MainActivity.this);
                    boolean success = sqLite.DeleteOne(person2);
                    listA();
                    name.setText(null);
                    age.setText(null);
                    Toast.makeText(MainActivity.this,"success = "+ success,Toast.LENGTH_LONG).show();
                }

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean check1 = name.getText().toString().isEmpty();
                Boolean check2 = age.getText().toString().isEmpty();
                if (((check1 == true) || ( check2 == true)) )
                {
                    Toast.makeText(MainActivity.this,"content cannot be left blank don't fix content",Toast.LENGTH_LONG).show();
                    name.setText(null);
                    age.setText(null);
                }
                else {
                    sqLite = new SQLite(MainActivity.this);
                    person2.setName(Name = name.getText().toString());
                    person2.setAge(Age = age.getText().toString());
                    Boolean a = sqLite.updateOne(person2);
                    listA();
                    name.setText(null);
                    age.setText(null);
                    Toast.makeText(MainActivity.this,"success = "+ a,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public  void init()
    {
        list = findViewById(R.id.list);
        add = findViewById(R.id.add);
        name = findViewById(R.id.nameIP);
        age = findViewById(R.id.ageIP);
        Name = name.getText().toString();
        Age = age.getText().toString();
        edit= findViewById(R.id.edit);
        delete = findViewById(R.id.delete);
    }
    public  void listA()
    {

        personAdapter = new PersonAdapter(this,sqLite.getAll(),this);
        list.setAdapter(personAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(linearLayoutManager);


    }

    @Override
    public void onClick(Person person, int position) {
        name.setText(person.getName());
        age.setText(person.getAge());
        person2 = new Person(person.getId(), person.name,person.age);
    }
}