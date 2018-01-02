package layout.Grabar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import layout.Login.Conexion;
import principal.android.empresa.isagenmaterial.R;
import principal.android.empresa.isagenmaterial.bean.JefeBean;
import principal.android.empresa.isagenmaterial.bean.ProyectoBean;
import principal.android.empresa.isagenmaterial.bean.RequisitoBean;
import principal.android.empresa.isagenmaterial.dao.RequisitoDao;

public class Fragment_Grabar_Requisito extends Fragment implements View.OnClickListener {
    ArrayList<ProyectoBean> listado;
    int estado=0;
    String estado2;
    Spinner combo;
    RequisitoBean obj2=null;
    RequisitoDao obj=null;
    EditText TXTNUMERO,TXTALCANCE,TXTPERSONAL,TXTREUNION,TXTDESCRIPCION;
    FloatingActionButton BTNGRABAR;
    ImageView imagen;
    Toolbar toolbar;
    Conexion objconexion=new Conexion();
    String conexion="";
    String conexion2="";
    ArrayList<ProyectoBean> sinconexion=new ArrayList<ProyectoBean>();

    public Fragment_Grabar_Requisito() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View obj=inflater.inflate(R.layout.fragment_grabar_requisito, container, false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            obj.findViewById(R.id.btngrabar_requisito).setTransitionName("transicion_imagen_requisito");
        }
        imagen=(ImageView)obj.findViewById(R.id.imagen_requisito_grabar);
        toolbar = (Toolbar) obj.findViewById(R.id.toolbar_requisito_grabar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Grabar Requisitos");


        TXTNUMERO=(EditText)obj.findViewById(R.id.numero_requisito);
        TXTALCANCE=(EditText)obj.findViewById(R.id.alcance_requisito);
        TXTDESCRIPCION=(EditText)obj.findViewById(R.id.descripcion_requisito);
        TXTPERSONAL=(EditText)obj.findViewById(R.id.cantpersona_requisito);
        TXTREUNION=(EditText)obj.findViewById(R.id.cantreunion_requisito);
        combo=(Spinner)obj.findViewById(R.id.comboproyecto_requisito);
        cargar_datos();
        BTNGRABAR=(FloatingActionButton) obj.findViewById(R.id.btngrabar_requisito);
        BTNGRABAR.setOnClickListener(this);
        sinconexion.add(new ProyectoBean("0","SELECCIONAR PROYECTO"));

        return obj;
    }


    public void grabar(){
        String TXTNUM, TXTALC, TXTPER, TXTDES, TXTREU, TXTCOMBOPROY;
        TXTNUM = TXTNUMERO.getText().toString();
        TXTALC = TXTALCANCE.getText().toString();
        TXTPER = TXTPERSONAL.getText().toString();
        TXTREU= TXTREUNION.getText().toString();
        TXTDES = TXTDESCRIPCION.getText().toString();
        ProyectoBean obj=(ProyectoBean) combo.getSelectedItem();
        TXTCOMBOPROY=obj.getNumero();
        String parametros[]=new String[6];
        parametros[0]=TXTNUM;
        parametros[1]=TXTALC;
        parametros[2]=TXTPER;
        parametros[3]=TXTREU;
        parametros[4]=TXTDES;
        parametros[5]=TXTCOMBOPROY;
        if(TXTNUMERO.length()==0){
            TXTNUMERO.setError("INGRESE EL NUMERO");
            TXTNUMERO.requestFocus();
        }else if(TXTALCANCE.length()==0){
            TXTALCANCE.setError("INGRESE EL TITULO DEL PROYECTO");
            TXTALCANCE.requestFocus();

        }else if(TXTPERSONAL.length()==0){
            TXTPERSONAL.setError("INGRESE LA DURACION");
            TXTPERSONAL.requestFocus();
        }else if(TXTDESCRIPCION.length()==0){
            TXTDESCRIPCION.setError("INGRESE LA DESCRIPCION");
            TXTDESCRIPCION.requestFocus();
        }else if(TXTREUNION.length()==0){
            TXTREUNION.setError("INGRESE LA CANT DE FASES");
            TXTREUNION.requestFocus();
        }else if(combo.getSelectedItemPosition()==0){
            ((TextView)combo.getSelectedView()).setError("");
        }else{
            new Async_Grabar_Requisito().execute(parametros);
        }



    }

    class Async_Grabar_Requisito extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Grabando", true);
        }

        @Override
        protected String doInBackground(String... params) {
            obj=new RequisitoDao();
            obj2=new RequisitoBean();
            String mensaje="";
            mensaje = String.valueOf(objconexion.isConnected(getActivity().getApplicationContext()));
            conexion = mensaje;
            if(conexion.equals("true")){
            obj2.setNumero(params[0]);
            obj2.setAlcance(params[1]);
            obj2.setPersonal(params[2]);
            obj2.setReunion(params[3]);
            obj2.setDescripcion(params[4]);
            obj2.setNUMPROY(params[5]);
            estado=obj.Grabar_Requisito(obj2);}
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


    class Async_Cargar_DatosRequisito extends AsyncTask<Void, Void, String> {
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
            obj=new RequisitoDao();
            estado2=obj.generar_cod();
            //cargar combo de jefes
            listado=obj.combo();}

            return mensaje;
        }
        protected void onPostExecute(String result) {
            if(conexion2.equals("true")){
            //siempre se guarda los datos en editetxt en string
            TXTNUMERO.setText(estado2);
            TXTNUMERO.setEnabled(false);
            //guardar datos en combo
            combo.setAdapter(new ArrayAdapter<ProyectoBean>(getActivity(), android.R.layout.simple_spinner_item, listado));
            progressDialog.dismiss();
        }else{
                combo.setAdapter(new ArrayAdapter<ProyectoBean>(getActivity(), android.R.layout.simple_spinner_item, sinconexion));

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
            dialogo1.setMessage("¿Deseas grabar el nuevo requisito ?");
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
    }
    public void cargar_datos(){
        new Async_Cargar_DatosRequisito().execute();
    }
    public void retroceder(){
        getActivity().onBackPressed();


    }
}
