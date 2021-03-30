package cl.duoc.ejemplolistviewclase;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnAgregar = findViewById(R.id.btnAgregar);
        ListView lvLista = findViewById(R.id.lvLista);
        TextView lblTotal = findViewById(R.id.lblTotal);
        lblTotal.setText(getString(R.string.total)+"0");
        ArrayList<Persona> personas = new ArrayList();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtNombre = findViewById(R.id.txtNombre);
                EditText txtApellido = findViewById(R.id.txtApellido);

                String nombre = txtNombre.getText().toString();
                String apellido = txtApellido.getText().toString();

                if(!nombre.isEmpty())
                {
                    if(!apellido.isEmpty())
                    {
                        try
                        {
                            Persona p = new Persona(nombre,apellido);
                            personas.add(p);

                            txtNombre.getText().clear();
                            txtApellido.getText().clear();

                            CustomArrayAdapter adaptador = new CustomArrayAdapter(MainActivity.this,
                                    R.layout.item_layout,personas,lblTotal);

                            lvLista.setAdapter(adaptador);
                            int total = MainActivity.calcultarTotal(personas);
                            lblTotal.setText(getString(R.string.total)+total);

                        }
                        catch(Exception ex)
                        {
                            AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                            alerta.setTitle(R.string.error);
                            alerta.setMessage(Integer.parseInt(ex.getMessage()));
                            alerta.setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            alerta.show();
                            //txtNombre.setError(getString(Integer.parseInt(ex.getMessage()))); //1900025
                        }

                    }
                    else
                    {
                        txtApellido.setError(getString(R.string.error_vacio));
                    }
                }
                else
                {
                    txtNombre.setError(getString(R.string.error_vacio));
                }
            }
        });

        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Persona p = (Persona) lvLista.getItemAtPosition(position);
                Toast.makeText(MainActivity.this,p.getNombre(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public static int calcultarTotal(ArrayList<Persona> lista)
    {
        int total= 0;
        for(Persona p : lista)
        {
            total += p.getNombre().length();
        }
        return total;
    }

}

class CustomArrayAdapter extends ArrayAdapter<Persona>
{
    private Context miContexto;
    private int miRecurso;
    private ArrayList<Persona> miLista;
    private TextView lblTotal;

    public CustomArrayAdapter(Context miContexto, int miRecurso, ArrayList<Persona> miLista,TextView lblTotal)
    {
        super(miContexto,miRecurso,miLista);
        this.miContexto = miContexto;
        this.miRecurso = miRecurso;
        this.miLista = miLista;
        this.lblTotal = lblTotal;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View view = LayoutInflater.from(miContexto).inflate(miRecurso,null);
        TextView lblNombre = view.findViewById(R.id.lblNombre);
        TextView lblApellido = view.findViewById(R.id.lblApellido);
        Button btnEliminar = view.findViewById(R.id.btnEliminar);

        Persona p = miLista.get(position);
        lblNombre.setText(p.getNombre());
        lblApellido.setText(p.getApellido());
        

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomArrayAdapter.this.remove(p); // eliminar del adapter
                miLista.remove(p); // eliminar del arraylist
                int total = MainActivity.calcultarTotal(miLista);
                //EXTRA
                //String[] t = lblTotal.getText().toString().split("$");
                //Log.i("total",t[0]);
                lblTotal.setText("Total $"+total);
            }
        });

        return view;


    }
}