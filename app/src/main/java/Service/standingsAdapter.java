package Service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formulaone.R;

import java.util.ArrayList;
import java.util.HashMap;


public class standingsAdapter extends RecyclerView.Adapter<standingsAdapter.MyViewHolder> {

    ArrayList<String> drivers;
    ArrayList<String> constructors;
    ArrayList<String> points;
    Context context;
    HashMap<String, Integer> constructorColours = new HashMap<String,Integer>();

    public standingsAdapter(Context context, ArrayList<String> drivers, ArrayList<String> constructors, ArrayList<String> points) {
        setConstructorColours();
        this.context = context;
        this.drivers = drivers;
        this.constructors = constructors;
        this.points = points;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String positionOfDriver = String.valueOf(drivers.indexOf(drivers.get(position))+1);
        holder.driverPosition.setText(positionOfDriver);
        holder.driverName.setText(drivers.get(position));
        holder.driverConstructor.setText(constructors.get(position));
        holder.driverPoints.setText(points.get(position));
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
        return drivers.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView driverName, driverConstructor, driverPoints,driverPosition;// init the item view's
        Button constructorButton;

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            constructorButton = itemView.findViewById(R.id.buttonConstructor);
            driverPosition = itemView.findViewById(R.id.driverPosition);
            driverName = itemView.findViewById(R.id.driverName);
            driverConstructor = itemView.findViewById(R.id.driverConstructor);
            driverPoints = itemView.findViewById(R.id.driverPoints);

        }
    }
}
