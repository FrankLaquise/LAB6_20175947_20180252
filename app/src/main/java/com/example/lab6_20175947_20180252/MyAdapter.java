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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context ;
    ArrayList<ActividadItem> actividadItemArrayList;
    FirebaseAuth auth;

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

        holder.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAdapter.ViewDialogUpdate viewDialogUpdate = new MyAdapter.ViewDialogUpdate();
                viewDialogUpdate.showDialog(context, actividadItem.getActividad_ID(), actividadItem.getTitulo(), actividadItem.getFecha(), actividadItem.getHora_inicio(), actividadItem.getHora_fin());
            }
        });
/*
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAdapter.ViewDialogConfirmDelete viewDialogConfirmDelete = new MyAdapter.ViewDialogConfirmDelete();
                viewDialogConfirmDelete.showDialog(context, actividadItem.getActividad_ID());
            }
        });*/
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
    public class ViewDialogUpdate {
        public void showDialog(Context context, String id_actividad, String titulo, String fecha, String hora_inicio, String hora_fin) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_add_new_user);

            EditText textTitulo = dialog.findViewById(R.id.textTitulo);
            EditText textFecha = dialog.findViewById(R.id.textFecha);
            TimePicker timePickerHinicio = dialog.findViewById(R.id.timePickerHinicio);
            TimePicker timePickerHfin = dialog.findViewById(R.id.timePickerHfin);


            textTitulo.setText(titulo);
            textFecha.setText(fecha);

            Button buttonUpdate = dialog.findViewById(R.id.buttonAdd);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

            buttonUpdate.setText("UPDATE");

            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String newTitulo = textTitulo.getText().toString();
                    String newFecha = textFecha.getText().toString();
                    String newHinicio = timePickerHinicio.getCurrentHour() + ":" + timePickerHinicio.getCurrentMinute();
                    String newHfin = timePickerHfin.getCurrentHour() + ":" + timePickerHfin.getCurrentMinute();

                    // Obtener las horas y minutos seleccionados en los TimePickers
                    int horaInicio = timePickerHinicio.getCurrentHour();
                    int minutoInicio = timePickerHinicio.getCurrentMinute();
                    int horaFin = timePickerHfin.getCurrentHour();
                    int minutoFin = timePickerHfin.getCurrentMinute();

                    Map<String,Object> map = new HashMap<>();
                    map.put("id",id_actividad);
                    map.put("titulo", newTitulo);
                    map.put("fecha", newFecha);
                    map.put("hora_inicio", newHinicio);
                    map.put("hora_fin", newHfin);

                    if (titulo.isEmpty() || fecha.isEmpty() || hora_inicio.isEmpty() || hora_fin.isEmpty()) {
                        Toast.makeText(context, "Por favor ingrese toda la data...", Toast.LENGTH_SHORT).show();
                    } else {

                        if (newTitulo.equals(titulo) && newFecha.equals(fecha) && newHinicio.equals(hora_inicio) && newHfin.equals(hora_fin)) {
                            Toast.makeText(context, "no ha hecho ningun cambio", Toast.LENGTH_SHORT).show();
                        } else {
                            if (horaInicio > horaFin || (horaInicio == horaFin && minutoInicio >= minutoFin)) {
                                Toast.makeText(context, "La hora de inicio debe ser anterior a la hora de fin", Toast.LENGTH_SHORT).show();

                            } else {
                                String id = auth.getCurrentUser().getUid();
                                FirebaseFirestore.getInstance().collection("users").
                                        document("mQd3U008QaUhk9fvKAKf7a1r5tH2").collection("actividades").document(id_actividad).update(map);
                                //databaseReference.child("USERS").child(id).setValue(new ActividadItem(id, newTitulo, newFecha, newHinicio, newHfin));
                                Toast.makeText(context, "Se ha actualizadp exitosamente!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }


                    }
                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }
    }
    /*
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
    }*/

}
