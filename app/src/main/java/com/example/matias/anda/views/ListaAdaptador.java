package com.example.matias.anda.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.matias.anda.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ListaAdaptador extends BaseAdapter {
    //private int[] colors = new int[] { 0xFF3a3aff , 0xFF6868FF };

    android.view.View.OnClickListener ocl;

    Context context;
    String[] titulos, contenidos;
    String[] imagenes;
    LayoutInflater inflater;

    public ListaAdaptador(Context context, String[] titulos, String[] contenidos, String[] imagenes) {
        this.context = context;
        this.titulos = titulos;
        this.contenidos = contenidos;
        this.imagenes = imagenes;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView universidad, contenido;
        ImageView imgImg;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.formato_lista, parent, false);

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
        itemView.setOnClickListener(ocl);
        //itemView.setOnLongClickListener(new OnItemLongClickListener(position));

        //int colorPos = position % colors.length;
        //itemView.setBackgroundColor(colors[colorPos]);

        return itemView;
    }

}