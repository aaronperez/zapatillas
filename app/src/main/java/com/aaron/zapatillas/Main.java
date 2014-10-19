package com.aaron.zapatillas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Main extends Activity {

    private ArrayList<Zapatillas> zapas = new ArrayList<Zapatillas>();
    private ListView lv;
    private Adaptador ad;
    private Spinner spinnerM;
    private EditText et1, et2, et3;
    private Bitmap b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_agregar) {
            agregar();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* Desplegar menú contextual*/
    @Override
    public void onCreateContextMenu(ContextMenu main, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(main, v, menuInfo);
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.contextual, main);
    }

    /* Al seleccionar elemento del menú contextual */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id=item.getItemId();
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int index= info.position;
        Object o= info.targetView.getTag();
        Adaptador.ViewHolder vh;
        vh = (Adaptador.ViewHolder)o;
        if (id == R.id.action_eliminar) {
            tostada(index + " " + vh.tvM.getText().toString());
            zapas.remove(index);
            ad.notifyDataSetChanged();
            return true;
        }else if (id == R.id.action_editar) {
            editar(index);
            ad.notifyDataSetChanged();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    /*         Auxiliares        */
    /*****************************/

    private void initComponents() {
        //Log.v("URI", getString(@drawable/adidas));
        nuevas();
        ad = new Adaptador(this, R.layout.elemento, zapas);
        lv = (ListView) findViewById(R.id.lvLista);
        lv.setAdapter(ad);
        //String[] Marca=new String[]{"adidas","asics","brooks","mizuno","new balance","nike","saucony"};
        //ArrayAdapter<String> movieAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Marca);
        spinnerM=(Spinner)findViewById(R.id.spinnerM);
        //spinnerM.setAdapter(movieAdapter);
        registerForContextMenu(lv);
     }

    /* Método que convierte la posición en
    * el spinner en la imagen que le corresponde*/
    private Bitmap spinnerBitmap(int n){
        int opcion=spinnerM.getSelectedItemPosition();
        Log.v("Posicion",getString(opcion));
        switch(opcion){
            case 0:
                b1=BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.adidas);
                break;
            case 1:
                b1=BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.asics);
                break;
            case 2:
                b1=BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.brooks);
                break;
            case 3:
                b1=BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.mizuno);
                break;
            case 4:
                b1=BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.newbalance);
                break;
            case 5:
                b1=BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.nike);
                break;
            case 6:
                b1=BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.saucony);
                break;
            }
        b1=BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.adidas);
        return b1;
    }

    //rellenamos algunos elementos del ArrayList para que se vea algo
    private void nuevas(){
        zapas.add(new Zapatillas("Adidas Riot 5","Montaña, pronador","70-90",BitmapFactory.decodeResource(this.getResources(),R.drawable.adidas)));
        zapas.add(new Zapatillas("Nike Pegasus","Asfalto, pronador","60-80",BitmapFactory.decodeResource(this.getResources(),R.drawable.nike)));
        zapas.add(new Zapatillas("Asics Gel Pulse 6","Asfalto, pronador","65-75", BitmapFactory.decodeResource(this.getResources(),R.drawable.asics)));
        zapas.add(new Zapatillas("New Balance 770 v4","Asfalto, pronador/neutro","70-75",BitmapFactory.decodeResource(this.getResources(),R.drawable.newbalance)));
        zapas.add(new Zapatillas("Mizuno Wave Ultima 6","Asfalto, neutro","70-80",BitmapFactory.decodeResource(this.getResources(),R.drawable.mizuno)));
        zapas.add(new Zapatillas("Saucony Virrata 2","Asfalto, neutro, drop 0","60-80", BitmapFactory.decodeResource(this.getResources(),R.drawable.saucony)));
    }

    private void tostada(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    /*        Menús          */
    /*************************/
    public void agregar(){
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle("Editar zapatilla");
        LayoutInflater inflater= LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.edicion, null);
        alert.setView(vista);
        alert.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                et1 = (EditText) vista.findViewById(R.id.etModelo);
                et2 = (EditText) vista.findViewById(R.id.etUsos);
                et3 = (EditText) vista.findViewById(R.id.etPeso);
                Log.v("POSICION", spinnerM.getSelectedItem().toString());
                Log.v("POSICION",getString(spinnerM.getSelectedItemPosition()));
                //b1=spinnerBitmap(spinnerM.getSelectedItemPosition());
                String c= spinnerM.getSelectedItem().toString()+" "+et1.getText().toString();
                zapas.add(new Zapatillas(c,et2.getText().toString(),et3.getText().toString(),b1));
                ad.notifyDataSetChanged();
                tostada("Elemento añadido");
            }
        });
        alert.setNegativeButton(R.string.cancelar,null);
        alert.show();
    }

    public void editar(int x){
        final int x2=x;
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle("Alta");
        LayoutInflater inflater= LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.edicion, null);
        alert.setView(vista);
        et1.setText(zapas.get(x2).getModelo().toString());
        et2.setText(zapas.get(x2).getCaract().toString());
        et3.setText(zapas.get(x2).getPeso().toString());
        spinnerM.setSelection(x2);
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                zapas.get(x2).setModelo(et1.getText().toString());
                zapas.get(x2).setMarca(spinnerBitmap(spinnerM.getSelectedItemPosition()));
                zapas.get(x2).setCaract(et2.getText().toString());
                zapas.get(x2).setPeso(et3.getText().toString());
                ad.notifyDataSetChanged();
                tostada("Elemento editado");
            }
        });
        alert.setNegativeButton("cancelar",null);
        alert.show();
    }
}
