package com.example.matias.anda.views;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.matias.anda.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ListaAdaptador extends BaseAdapter {
    //private int[] colors = new int[] { 0xFF3a3aff , 0xFF6868FF };

    android.view.View.OnClickListener ocl;

    Context context;
    String[] titulos, contenidos;
    String[] imagenes;
    LayoutInflater inflater;
    Handler handler;

    public ListaAdaptador(Context context, String[] titulos, String[] contenidos, String[] imagenes, Handler handler) {
        this.context = context;
        this.titulos = titulos;
        this.contenidos = contenidos;
        this.imagenes = imagenes;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return this.contenidos.length;
    }

    @Override
    public Object getItem(int position) {
        ArrayList<String> arr = new ArrayList<>();
        arr.add(titulos[position]);
        arr.add(contenidos[position]);
        arr.add(imagenes[position]);

        return arr;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        TextView universidad, contenido;
        ImageView imgImg;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.formato_lista, parent, false);
        itemView.setFocusable(false);


        universidad = (TextView) itemView.findViewById(R.id.universidadLista);
        contenido = (TextView) itemView.findViewById(R.id.contenidoLista);
        imgImg = (ImageView) itemView.findViewById(R.id.imagenLista);

        universidad.setText(titulos[position]);
        String str = contenidos[position];
        if (str.length()>40){
            str=str.substring(0,40);
            str+="...";
        }
        contenido.setText(str);
        Glide.with(this.context).load(imagenes[position]).centerCrop().into(imgImg);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "You Clicked " + contenidos[position], Toast.LENGTH_LONG).show();
                handler.sendEmptyMessage(position);
            }
        });
        //itemView.setOnLongClickListener(new OnItemLongClickListener(position));

        //int colorPos = position % colors.length;
        //itemView.setBackgroundColor(colors[colorPos]);

        return itemView;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

}