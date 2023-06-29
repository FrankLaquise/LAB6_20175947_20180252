package com.example.lab6_20175947_20180252;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context ;
    ArrayList<ActividadItem> actividadItemArrayList;

    public MyAdapter(Context context, ArrayList<ActividadItem> actividadItemArrayList) {
        this.context = context;
        this.actividadItemArrayList = actividadItemArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(context).inflate(R.layout.actividad_item,parent,false);
        return new MyViewHolder(view);

    }
//bien
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        ActividadItem actividadItem = actividadItemArrayList.get(position);
        holder.textName.setText("Titulo de actividad : " + actividadItem.getTitulo());
        holder.textFecha.setText("Fecha : " + actividadItem.getFecha());
        holder.text_horainicio.setText("Hora Inicio : " + actividadItem.getHora_fin());
        holder.text_horafinal.setText("Hora Final : " + actividadItem.getHora_fin());
    }

    @Override
    public int getItemCount() {
        return actividadItemArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textName;
        TextView textFecha;
        TextView text_horainicio;
        TextView text_horafinal;

        Button buttonDelete;
        Button buttonUpdate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.textName);
            textFecha = itemView.findViewById(R.id.textFecha);
            text_horainicio = itemView.findViewById(R.id.textInicio);
            text_horafinal = itemView.findViewById(R.id.textFinal);

            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonUpdate = itemView.findViewById(R.id.buttonUpdate);
        }
    }
}
