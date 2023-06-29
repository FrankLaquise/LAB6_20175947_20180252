package com.example.lab6_20175947_20180252;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ActividadesRecyclerAdapter extends RecyclerView.Adapter<ActividadesRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<ActividadItem> actividadItemArrayList;
    DatabaseReference databaseReference;

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
        ActividadItem actividades = actividadItemArrayList.get(position);

        holder.textName.setText("Titulo de actividad : " + actividades.getTitulo());
        holder.textFecha.setText("Fecha : " + actividades.getFecha());
        holder.text_horainicio.setText("Hora Inicio : " + actividades.getHora_fin());
        holder.text_horafinal.setText("Hora Final : " + actividades.getHora_fin());

        holder.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogUpdate viewDialogUpdate = new ViewDialogUpdate();
                viewDialogUpdate.showDialog(context, actividades.getActividad_ID(), actividades.getTitulo(), actividades.getFecha(), actividades.getHora_inicio(), actividades.getHora_fin());
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogConfirmDelete viewDialogConfirmDelete = new ViewDialogConfirmDelete();
                viewDialogConfirmDelete.showDialog(context, actividades.getActividad_ID());
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

    public class ViewDialogUpdate {
        public void showDialog(Context context, String id, String titulo, String fecha, String hora_inicio, String hora_fin) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_add_new_user);

            EditText textTitulo = dialog.findViewById(R.id.textTitulo);
            EditText textFecha = dialog.findViewById(R.id.textFecha);
            //TimePicker timePickerHinicio = dialog.findViewById(R.id.timePickerHinicio);
            //TimePicker timePickerHfin = dialog.findViewById(R.id.timePickerHfin);


            textTitulo.setText(titulo);
            textFecha.setText(fecha);

            Button buttonUpdate = dialog.findViewById(R.id.buttonAdd);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

            buttonUpdate.setText("UPDATE");



        }
    }


    public class ViewDialogConfirmDelete {
        public void showDialog(Context context, String id) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.view_dialog_confirm_delete);

            Button buttonDelete = dialog.findViewById(R.id.buttonDelete);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    databaseReference.child("USERS").child(id).removeValue();
                    Toast.makeText(context, "Actividad Borrada exitosamente!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }
    }

}
