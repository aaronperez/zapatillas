package com.aaron.zapatillas;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaron.zapatillas.R;
import com.aaron.zapatillas.Zapatillas;

import java.util.ArrayList;

/**
 * Created by 2dam on 17/10/2014.
 */
public class Adaptador extends ArrayAdapter<Zapatillas> {
    private Context contexto;
    private ArrayList<Zapatillas> zapas;
    private int recurso;
    private LayoutInflater i;

    public Adaptador(Context context, int resource, ArrayList<Zapatillas> objects) {
        super(context, resource, objects);
        this.contexto=context;
        this.zapas=objects;
        this.recurso=resource;
        this.i= (LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder{
        public TextView tvM,tvC,tvP;
        public ImageView ivM,ivF;
        public int posicion;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh= null;
        //El if entra cuando se crea el ViewHolder por primera vez
        if(convertView == null){
            convertView= i.inflate(recurso, null);
            vh = new ViewHolder();
            vh.tvM = (TextView)convertView.findViewById(R.id.tvModelo);
            vh.tvC = (TextView)convertView.findViewById(R.id.tvCaracteristicas);
            vh.tvP = (TextView)convertView.findViewById(R.id.tvPeso);
            vh.ivM = (ImageView)convertView.findViewById(R.id.ivMarca);
            //vh.ivF = (ImageView)convertView.findViewById(R.id.ivFoto);
            convertView.setTag(vh);
        }
        else{
            vh=(ViewHolder)convertView.getTag();
        }
        vh.tvM.setText(zapas.get(zapas.size()-1-position).getModelo());
        vh.tvC.setText(zapas.get(zapas.size()-1-position).getCaract());
        vh.tvP.setText(zapas.get(zapas.size()-1-position).getPeso());
        vh.ivM.setImageBitmap(zapas.get(zapas.size()-1-position).getMarca());
        vh.posicion=position;
        return convertView;
    }


}
