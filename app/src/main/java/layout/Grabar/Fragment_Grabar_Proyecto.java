package layout.Grabar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import layout.Fechas.FechaFin;
import layout.Fechas.FechaInicio;
import layout.Login.Conexion;
import principal.android.empresa.isagenmaterial.R;
import principal.android.empresa.isagenmaterial.bean.JefeBean;
import principal.android.empresa.isagenmaterial.bean.ProyectoBean;
import principal.android.empresa.isagenmaterial.dao.ProyectoDao;

public class Fragment_Grabar_Proyecto extends Fragment implements View.OnClickListener {
    ArrayList<JefeBean> listado1;
    int estado;
    String estado2;
    Spinner tipo,combo;
    ProyectoBean obj2=null;
    ProyectoDao obj=null;
    TextView TXTINICIO,TXTFIN;
    EditText TXTNUMERO,TXTTITULO,TXTDURACION,TXTDESCRIPCION,TXTFASES,TXTGASTOS;
    FloatingActionButton BTNGRABAR;
    ImageView imagen;
    Toolbar toolbar;
    Conexion objconexion=new Conexion();
    String conexion="";
    String conexion2="";
    ArrayList<JefeBean> sinconexion=new ArrayList<JefeBean>();

    public Fragment_Grabar_Proyecto() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View obj=inflater.inflate(R.layout.fragment_grabar_proyecto, container, false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            obj.findViewById(R.id.btngrabar_proyecto).setTransitionName("transicion_imagen_proyecto");
        }
        imagen=(ImageView)obj.findViewById(R.id.imagen_proyecto_grabar);
        toolbar = (Toolbar) obj.findViewById(R.id.toolbar_proyecto_grabar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Grabar Proyectos");

        TXTNUMERO=(EditText)obj.findViewById(R.id.numero_proyecto);
        TXTTITULO=(EditText)obj.findViewById(R.id.titulo_proyecto);
        TXTDURACION=(EditText)obj.findViewById(R.id.duracion_proyecto);
        TXTDESCRIPCION=(EditText)obj.findViewById(R.id.descripcion_proyecto);
        TXTFASES=(EditText)obj.findViewById(R.id.fases_proyecto);
        TXTINICIO=(TextView) obj.findViewById(R.id.fechainicio_proyecto);
        TXTINICIO.setOnClickListener(this);
        TXTFIN=(TextView) obj.findViewById(R.id.fechafin_proyecto);
        TXTFIN.setOnClickListener(this);
        TXTGASTOS=(EditText)obj.findViewById(R.id.gastos_proyecto);
        combo=(Spinner)obj.findViewById(R.id.combojefe_proyecto);
        BTNGRABAR=(FloatingActionButton) obj.findViewById(R.id.btngrabar_proyecto);
        BTNGRABAR.setOnClickListener(this);
        tipo = (Spinner) obj.findViewById(R.id.tipo_proyecto);
        String[] valores = {"Publicos","Privados","Experimentales","Normalizados","Productivos","Sociales", "Investigacion"};
        tipo.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, valores));
        cargar_datos();
        sinconexion.add(new JefeBean(0,"SELECCIONAR ADMINISTRADOR"));

        return obj;
    }


    public void FechaInicio(View v) {
        DialogFragment newFragment = new FechaInicio();
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    public void FechaFin(View v) {
        DialogFragment newFragment = new FechaFin();
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }


    public void grabar(){
        String TXTNUM, TXTTIT, TXTTIP, TXTDUR, TXTDES, TXTFAS, TXTINI, TXTFI, TXTGAS, TXTCON;
        TXTNUM = TXTNUMERO.getText().toString();
        TXTTIT = TXTTITULO.getText().toString();
        TXTTIP =tipo.getItemAtPosition(tipo.getSelectedItemPosition()).toString();
        JefeBean obj=(JefeBean)combo.getSelectedItem();
        TXTCON= String.valueOf(obj.getCODJEFE());
        TXTDUR = TXTDURACION.getText().toString();
        TXTDES = TXTDESCRIPCION.getText().toString();
        TXTFAS = TXTFASES.getText().toString();
        TXTINI = TXTINICIO.getText().toString();
        TXTFI = TXTFIN.getText().toString();
        TXTGAS = TXTGASTOS.getText().toString();
        String parametros[]=new String[10];
        parametros[0]=TXTNUM;
        parametros[1]=TXTTIT;
        parametros[2]=TXTTIP;
        parametros[3]=TXTDUR;
        parametros[4]=TXTDES;
        parametros[5]=TXTFAS;
        parametros[6]=TXTINI;
        parametros[7]=TXTFI;
        parametros[8]=TXTGAS;
        parametros[9]=TXTCON;

        if(TXTNUMERO.length()==0){
            TXTNUMERO.setError("INGRESE EL NUMERO");
            TXTNUMERO.requestFocus();
        }else if(TXTTITULO.length()==0){
            TXTTITULO.setError("INGRESE EL TITULO DEL PROYECTO");
            TXTTITULO.requestFocus();

        }else if(TXTDURACION.length()==0){
            TXTDURACION.setError("INGRESE LA DURACION");
            TXTDURACION.requestFocus();
        }else if(TXTDESCRIPCION.length()==0){
            TXTDESCRIPCION.setError("INGRESE LA DESCRIPCION");
            TXTDESCRIPCION.requestFocus();
        }else if(TXTFASES.length()==0){
            TXTFASES.setError("INGRESE LA CANT DE FASES");
            TXTFASES.requestFocus();
        }else if(TXTINICIO.length()==0){
            TXTINICIO.setError("INGRESE LA FECHA DE INICIO");
            TXTINICIO.requestFocus();
        }else if(TXTFIN.length()==0){
            TXTFIN.setError("INGRESE LA FECHA DE ENTREGA");
            TXTFIN.requestFocus();
        }else if(TXTGASTOS.length()==0){
            TXTGASTOS.setError("INGRESE EL PRESUPUESTO");
            TXTGASTOS.requestFocus();
        }else if(combo.getSelectedItemPosition()==0){
            ((TextView)combo.getSelectedView()).setError("");
        }else{
            new Async_Grabar_Proyecto().execute(parametros);
        }
    }

    class Async_Grabar_Proyecto extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Grabando", true);
        }
        @Override
        protected String doInBackground(String... params) {
            obj=new ProyectoDao();
            obj2=new ProyectoBean();
            String mensaje="";
            mensaje = String.valueOf(objconexion.isConnected(getActivity().getApplicationContext()));
            conexion = mensaje;
            if(conexion.equals("true")){
            obj2.setNumero(params[0]);
            obj2.setTitulo(params[1]);
            obj2.setTipo(params[2]);
            obj2.setDuracion(params[3]);
            obj2.setDescripcion(params[4]);
            obj2.setFases(params[5]);
            obj2.setInicio(params[6]);
            obj2.setFin(params[7]);
            obj2.setGastos(params[8]);
            obj2.setCODJEFE(params[9]);
            estado=obj.Grabar_Proyecto(obj2);}
            return mensaje;
        }
        protected void onPostExecute(String result) {
            if(conexion.equals("true")){
                if (estado==1){
                    Toast.makeText(getActivity(), "Registro Grabado Satisfactoriamente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Registro No Grabado", Toast.LENGTH_SHORT).show();
                }
                retroceder();
            } else{
                Toast.makeText(getActivity(), "No  hay conexion a internet", Toast.LENGTH_LONG).show();

            }
            progressDialog.dismiss();


        }
    }


    class Async_Cargar_DatosProyecto extends AsyncTask<Void, Void, String> {
        private ProgressDialog progressDialog;
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Cargando", true);
        }
        @Override
        protected String doInBackground(Void... objt) {
            String mensaje="";
            mensaje = String.valueOf(objconexion.isConnected(getActivity().getApplicationContext()));
            conexion2 = mensaje;
            if(conexion2.equals("true")){
            //generar codigo
            obj=new ProyectoDao();
            estado2=obj.generar_cod();
            //cargar combo de jefes
            listado1=obj.combo();}

            return mensaje;
        }
        protected void onPostExecute(String result) {
            if(conexion2.equals("true")){
            //siempre se guarda los datos en editetxt en string
            TXTNUMERO.setText(estado2);
            TXTNUMERO.setEnabled(false);
            //guardar datos en combo
            combo.setAdapter(new ArrayAdapter<JefeBean>(getActivity(), android.R.layout.simple_spinner_item, listado1));
        }else{
                combo.setAdapter(new ArrayAdapter<JefeBean>(getActivity(), android.R.layout.simple_spinner_item, sinconexion));

                Toast.makeText(getActivity(), "No  hay conexion a internet", Toast.LENGTH_LONG).show();
            }
            progressDialog.dismiss();
        }

    }
    @Override
    public void onClick(View v) {
        if (v == BTNGRABAR) {
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
            dialogo1.setTitle("Importante");
            dialogo1.setMessage("¿Deseas grabar el nuevo proyecto?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Grabar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    grabar();
                }
            });
            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                }
            });
            dialogo1.show();
        }
        if (v == TXTINICIO) {
            FechaInicio(getView());
        }
        if (v == TXTFIN) {
            FechaFin(getView());
        }
    }
    public void cargar_datos(){
        new Async_Cargar_DatosProyecto().execute();
    }
    public void retroceder(){
        getActivity().onBackPressed();
    }
}
