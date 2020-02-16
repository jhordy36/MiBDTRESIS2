package com.example.mibdtresis;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mibdtresis.DetalleProducto;
import com.example.mibdtresis.Producto;
import com.example.mibdtresis.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterProducto extends RecyclerView.Adapter<AdapterProducto.ViewHolder> {
    private LayoutInflater inflador; ArrayList<Producto> datos; Context micontext;
    public AdapterProducto(Context context, ArrayList<Producto> datos) {
        this.datos= datos;
        micontext=context;
        inflador = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        //holder.miid.setText(datos.get(i).getId());
        holder.miid.setText("$" + datos.get(i).getPrecio());
        holder.nombreproducto.setText(datos.get(i).getNombre());
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(micontext, DetalleProducto.class);
                intent.putExtra("miid",datos.get(i).getId());
                intent.putExtra("nombreproducto",datos.get(i).getNombre());
            }
        });
        holder.btnborrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("PRODUCTOS");
                reference.child(datos.get(i).getId()).removeValue();//con el child puedes entrar a las ramas internasde la BD
            }
        });
    }
    @Override
    public int getItemCount() {
        return datos.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView miid, nombreproducto;
        Button btnborrar;
        ViewHolder(View itemView) {
            super(itemView);
            miid = (TextView)itemView.findViewById(R.id.id_text);//el miid es el precio
            nombreproducto = (TextView)itemView.findViewById(R.id.nombre);
            btnborrar=itemView.findViewById(R.id.btnDelete);
        }
    }
}