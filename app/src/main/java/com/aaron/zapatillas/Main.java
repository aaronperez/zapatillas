package com.aaron.zapatillas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;

public class Main extends Activity {

    private ArrayList<Zapatillas> zapas = new ArrayList<Zapatillas>();
    private ListView lv;
    private Adaptador ad;
    private Spinner spinner;
    private EditText et1, et2, et3;
    private Bitmap b1;
    private TextView tvM, tvC, tvP;
    private ImageView ivM;

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
        if (id == R.id.action_editar) {
            editar(index);
            ad.notifyDataSetChanged();
            return true;
        }else if (id == R.id.action_eliminar) {
            eliminar(index);
            ad.notifyDataSetChanged();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    /*         Auxiliares        */
    /*****************************/

    private void initComponents() {
        nuevas();
        ad = new Adaptador(this, R.layout.elemento, zapas);
        lv = (ListView) findViewById(R.id.lvLista);
        lv.setAdapter(ad);
        registerForContextMenu(lv);
     }

    /*  Método para iniciar Spinner y escucharlo  */
    /**********************************************/
    private void iniciarSpinner(View v){
        AdapterView.OnItemSelectedListener onSpinner =new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,int position,long id) {}
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
         };
        ArrayAdapter<CharSequence> stringArrayAdapter=ArrayAdapter.createFromResource(this,R.array.Marca,android.R.layout.simple_spinner_dropdown_item);
        spinner =(Spinner)v.findViewById(R.id.spinnerM);
        spinner.setAdapter(stringArrayAdapter);
        spinner.setOnItemSelectedListener(onSpinner);
}

    /* Método que convierte la posición en
    * el spinner en la imagen que le corresponde*/
    private Bitmap spinnerBitmap(int n){
        switch(n){
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
        return b1;
    }

    //rellenamos algunos elementos del ArrayList para que se vea algo
    private void nuevas(){
        zapas.add(new Zapatillas("Adidas Riot 5","Montaña, pronador","70-90",BitmapFactory.decodeResource(this.getResources(),R.drawable.adidas)));
        zapas.add(new Zapatillas("Asics Gel Pulse 6","Asfalto, pronador","65-75", BitmapFactory.decodeResource(this.getResources(),R.drawable.asics)));
        zapas.add(new Zapatillas("Nike Pegasus","Asfalto, pronador","60-80",BitmapFactory.decodeResource(this.getResources(),R.drawable.nike)));
        zapas.add(new Zapatillas("New Balance 770 v4","Asfalto, pronador/neutro","70-75",BitmapFactory.decodeResource(this.getResources(),R.drawable.newbalance)));
        zapas.add(new Zapatillas("Mizuno Wave Ultima 6","Asfalto, neutro","70-80",BitmapFactory.decodeResource(this.getResources(),R.drawable.mizuno)));
        zapas.add(new Zapatillas("Saucony Virrata 2","Asfalto, neutro, drop 0","60-80", BitmapFactory.decodeResource(this.getResources(),R.drawable.saucony)));
    }

    private void tostada(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    /*     Menú  opciones    */
    /*************************/
    public void agregar(){
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle("Agregar zapatilla");
        LayoutInflater inflater= LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.edicion, null);
        alert.setView(vista);
        iniciarSpinner(vista);
        alert.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                et1 = (EditText) vista.findViewById(R.id.etModelo);
                et2 = (EditText) vista.findViewById(R.id.etUsos);
                et3 = (EditText) vista.findViewById(R.id.etPeso);
                b1=spinnerBitmap(spinner.getSelectedItemPosition());
                String c= spinner.getSelectedItem().toString()+" "+et1.getText().toString();
                zapas.add(new Zapatillas(c,et2.getText().toString(),et3.getText().toString(),b1));
                ordenar();
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
        alert.setTitle("Editar zapatilla");
        LayoutInflater inflater= LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.edicion, null);
        alert.setView(vista);
        iniciarSpinner(vista);
        final EditText et1=(EditText)vista.findViewById(R.id.etModelo);
        final EditText et2=(EditText)vista.findViewById(R.id.etUsos);
        final EditText et3=(EditText)vista.findViewById(R.id.etPeso);
        et1.setText(zapas.get(x2).getModelo());
        et2.setText(zapas.get(x2).getCaract());
        et3.setText(zapas.get(x2).getPeso());
        for(int i=0;i<7;i++){
            if(spinner.getItemAtPosition(i).toString().substring(0,3).equals(zapas.get(x2).getModelo().substring(0,3))){
                spinner.setSelection(i);
                break;
            }
        }
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                zapas.get(x2).setModelo(et1.getText().toString());
                zapas.get(x2).setMarca(spinnerBitmap(spinner.getSelectedItemPosition()));
                zapas.get(x2).setCaract(et2.getText().toString());
                zapas.get(x2).setPeso(et3.getText().toString());
                b1=spinnerBitmap(spinner.getSelectedItemPosition());
                zapas.get(x2).setMarca(b1);
                ordenar();
                ad.notifyDataSetChanged();
                tostada("Elemento editado");
            }
        });
        alert.setNegativeButton("cancelar",null);
        alert.show();
    }

    public void eliminar(int n){
        final int x=n;
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle("Eliminar zapatilla?");
        LayoutInflater inflater= LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.elemento, null);
        tvM=(TextView)findViewById(R.id.tvModelo);
        tvC=(TextView)findViewById(R.id.tvCaracteristicas);
        tvP=(TextView)findViewById(R.id.tvPeso);
        ivM=(ImageView)findViewById(R.id.ivMarca);
        tvM.setText(zapas.get(x).getModelo());
        tvC.setText(zapas.get(x).getCaract());
        tvP.setText(zapas.get(x).getPeso());
        ivM.setImageBitmap(zapas.get(x).getMarca());
        alert.setView(vista);
        alert.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                zapas.remove(x);
                ad.notifyDataSetChanged();
                tostada("Elemento eliminado");
            }
        });
        alert.setNegativeButton(R.string.cancelar,null);
        alert.show();
    }

    public void ordenar(){
        Collections.sort(zapas);
    }
}

