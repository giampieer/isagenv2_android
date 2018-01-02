package layout.Modificar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import layout.Login.Conexion;
import principal.android.empresa.isagenmaterial.R;
import principal.android.empresa.isagenmaterial.bean.RequisitoBean;
import principal.android.empresa.isagenmaterial.dao.RequisitoDao;

public class Fragment_Modificar_Requisito extends Fragment implements View.OnClickListener {
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    String cod,alcance,numeroproy,descrip,cantperso,cantreu;
    int estado=0;
    ImageView imagen;
    Toolbar toolbar;
    Conexion objconexion=new Conexion();
    String conexion="";
    String conexion2="";
    EditText TXTNUMERO,TXTALCANCE,TXTPERSONAL,TXTREUNION,TXTDESCRIPCION,TXTNUMPROYECTO;
    RequisitoDao obj=null;
    RequisitoBean obj2=null;

    public Fragment_Modificar_Requisito() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View obj=inflater.inflate(R.layout.fragment_modificar_requisito, container, false);
        imagen=(ImageView)obj.findViewById(R.id.imagen_requisito_modificar);
        toolbar = (Toolbar) obj.findViewById(R.id.toolbar_requisito_modificar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Modificar Requisitos");

        fab = (FloatingActionButton)obj.findViewById(R.id.fab_modificar_requisito);
        fab1 = (FloatingActionButton)obj.findViewById(R.id.fab1_modificar_requisito);
        fab_open = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);


        TXTNUMERO=(EditText)obj.findViewById(R.id.numero_requisito);
        TXTALCANCE=(EditText)obj.findViewById(R.id.alcance_requisito);
        TXTDESCRIPCION=(EditText)obj.findViewById(R.id.descripcion_requisito);
        TXTPERSONAL=(EditText)obj.findViewById(R.id.cantpersona_requisito);
        TXTREUNION=(EditText)obj.findViewById(R.id.cantreunion_requisito);
        TXTNUMPROYECTO=(EditText)obj.findViewById(R.id.numproyecto_requisito);
        cargardatos();
        return obj;
    }


    public void modificar() {
        String TXTNUM, TXTALC, TXTPER, TXTDES, TXTREU, TXTNUMPROY;
        TXTNUM = TXTNUMERO.getText().toString();
        TXTALC = TXTALCANCE.getText().toString();
        TXTPER = TXTPERSONAL.getText().toString();
        TXTREU= TXTREUNION.getText().toString();
        TXTDES = TXTDESCRIPCION.getText().toString();
        TXTNUMPROY = TXTNUMPROYECTO.getText().toString();
        String parametros[]=new String[6];
        parametros[0]=TXTNUM;
        parametros[1]=TXTALC;
        parametros[2]=TXTPER;
        parametros[3]=TXTREU;
        parametros[4]=TXTDES;
        parametros[5]=TXTNUMPROY;
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
        }else if(TXTNUMPROYECTO.length()==0){
            TXTNUMPROYECTO.setError("INGRESE LA CANT DE FASES");
            TXTNUMPROYECTO.requestFocus();
        }else{
            new Async_Modificar_Requisito().execute(parametros);

        }
    }

    //ASYNCTASK

    class Async_Modificar_Requisito extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Modificando", true);
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
            estado=obj.Modificar_Requisito(obj2);}

            return mensaje;
        }
        protected void onPostExecute(String result) {
            if(conexion.equals("true")){
                if (estado==1){
                    Toast.makeText(getActivity(), "Registro Modificado Satisfactoriamente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Registro No Modificado", Toast.LENGTH_SHORT).show();
                }
                retroceder();
            } else{
                Toast.makeText(getActivity(), "No  hay conexion a internet", Toast.LENGTH_LONG).show();

            }
            progressDialog.dismiss();

        }
    }

    class Async_Cargar_Requisito extends AsyncTask<Void, Void, String> {

        private ProgressDialog progressDialog;
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Cargando", true);
        }

        @Override
        protected String doInBackground(Void... obj) {
            String mensaje="";
            mensaje = String.valueOf(objconexion.isConnected(getActivity().getApplicationContext()));
            conexion2 = mensaje;
            if(conexion2.equals("true")){
            cod = getArguments().getString("numero_requisito");
            alcance = getArguments().getString("alcance_requisito");
            cantperso= getArguments().getString("persona_requisito");
            descrip = getArguments().getString("descripcion_requisito");
            cantreu= getArguments().getString("reunion_requisito");
            numeroproy= getArguments().getString("numeroproy_requisito");}
            return mensaje;
        }
        protected void onPostExecute(String result) {
            if(conexion2.equals("true")){
            TXTNUMERO.setText(cod);
            TXTNUMERO.setEnabled(false);
            TXTALCANCE.setText(alcance);
            TXTNUMPROYECTO.setText(numeroproy);
            TXTDESCRIPCION.setText(descrip);
            TXTREUNION.setText(cantreu);
            TXTPERSONAL.setText(cantperso);
        } else{
            Toast.makeText(getActivity(), "No  hay conexion a internet", Toast.LENGTH_LONG).show();

        }
            progressDialog.dismiss();
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            //fab es el id del floating button
            case R.id.fab_modificar_requisito:
                animateFAB();
                break;
            case R.id.fab1_modificar_requisito:
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿Deseas modificar el requisito?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        modificar();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                dialogo1.show();
                break;

        }

    }


    public void retroceder(){
        getActivity().onBackPressed();
    }
    public void cargardatos(){
        new Async_Cargar_Requisito().execute();
    }

    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab1.setClickable(false);
            isFabOpen = false;

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab1.setClickable(true);
            isFabOpen = true;
        }
    }

}
