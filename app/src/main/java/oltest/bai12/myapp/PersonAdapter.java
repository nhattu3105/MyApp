package oltest.bai12.myapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.TableViewHolder> {

    private Context context;
    private List<Person> people;
    private MenulistClick menulistClick;

    public PersonAdapter(Context context, List<Person> people,MenulistClick menulistClick) {
        this.context = context;
        this.people = people;
        this.menulistClick = menulistClick;

    }

    @NonNull
    @Override
    public PersonAdapter.TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.personlayout,parent,false);
        return new TableViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Person person = people.get(position);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menulistClick.onClick(people.get(position),position);
            }
        });
        holder.name.setText(person.getName());
        holder.age.setText(person.getAge());

    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public int getItemViewType(int position) {
        return position;
    }

    static class TableViewHolder extends RecyclerView.ViewHolder
    {
        TextView name ,age;
        ConstraintLayout constraintLayout;
        public TableViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.nameapp);
            age = itemView.findViewById(R.id.ageapp);
            constraintLayout = itemView.findViewById(R.id.personlayout);
        }

    }

    public interface MenulistClick
    {
        public void onClick(Person person, int positon);

    }
}
