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

public class ActividadesRecyclerAdapter extends RecyclerView.Adapter<ActividadesRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<ActividadItem> actividadItemArrayList;

    public ActividadesRecyclerAdapter(Context context, ArrayList<ActividadItem> actividadItemArrayList) {
        this.context = context;
        this.actividadItemArrayList = actividadItemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =     LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.actividad_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ActividadItem users = actividadItemArrayList.get(position);

        holder.textName.setText("Titulo de actividad : " + users.getTitulo());
        holder.textFecha.setText("Fecha : " + users.getFecha());
        holder.text_horainicio.setText("Hora Inicio : " + users.getHora_fin());
        holder.text_horafinal.setText("Hora Final : " + users.getHora_fin());

        holder.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ViewDialogUpdate viewDialogUpdate = new ViewDialogUpdate();
                //viewDialogUpdate.showDialog(context, users.getUserID(), users.getUserName(), users.getUserEmail(), users.getUserCountry());
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ViewDialogConfirmDelete viewDialogConfirmDelete = new ViewDialogConfirmDelete();
                //viewDialogConfirmDelete.showDialog(context, users.getUserID());
            }
        });

    }

    @Override
    public int getItemCount() {
        return actividadItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        TextView textName;
        TextView textFecha;
        TextView text_horainicio;
        TextView text_horafinal;

        Button buttonDelete;
        Button buttonUpdate;

        public ViewHolder(@NonNull View itemView) {
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
