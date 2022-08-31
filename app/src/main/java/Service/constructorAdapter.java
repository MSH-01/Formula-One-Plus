package Service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formulaone.R;

import java.util.ArrayList;
import java.util.HashMap;


public class constructorAdapter extends RecyclerView.Adapter<constructorAdapter.MyViewHolder1> {

    ArrayList<String> constructors;
    ArrayList<String> points;
    Context context;
    HashMap<String, Integer> constructorColours = new HashMap<String,Integer>();

    public constructorAdapter(Context context, ArrayList<String> constructors, ArrayList<String> points) {
        setConstructorColours();
        this.context = context;
        this.constructors = constructors;
        this.points = points;
    }

    @NonNull
    @Override
    public MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.constructor_layout_view, parent, false);
        return new MyViewHolder1(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder1 holder, @SuppressLint("RecyclerView") final int position) {
        String positionOfConstructor = String.valueOf(constructors.indexOf(constructors.get(position))+1);
        holder.constructorPosition.setText(positionOfConstructor);
        holder.constructorName.setText(constructors.get(position));
        holder.constructorPoints.setText(points.get(position));
        String currentConstructor = constructors.get(position);
        try {
            holder.constructorButton.setBackgroundColor(constructorColours.get(currentConstructor));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }

    public void setConstructorColours(){
        constructorColours.put("Alfa Romeo",0xFFE11232);
        constructorColours.put("AlphaTauri",0xFF21394C);
        constructorColours.put("Alpine F1 Team",0xFF016FBA);
        constructorColours.put("Aston Martin",0xFF278759);
        constructorColours.put("Ferrari",0xFFFC0706);
        constructorColours.put("Haas F1 Team",0xFFA37F4D);
        constructorColours.put("McLaren",0xFFFE7F03);
        constructorColours.put("Mercedes",0xFF019A93);
        constructorColours.put("Red Bull",0xFF011121);
        constructorColours.put("Williams",0xFF049BD7);
    }


    @Override
    public int getItemCount() {
        return constructors.size();
    }

    public static class MyViewHolder1 extends RecyclerView.ViewHolder {
        TextView constructorName,constructorPoints,constructorPosition;// init the item view's
        Button constructorButton;

        public MyViewHolder1(View itemView) {
            super(itemView);

            // get the reference of item view's
            constructorButton = itemView.findViewById(R.id.buttonConstructor);
            constructorPosition = itemView.findViewById(R.id.constructorPosition);
            constructorName = itemView.findViewById(R.id.constructorName);
            constructorPoints = itemView.findViewById(R.id.constructorPoints);

        }
    }
}
