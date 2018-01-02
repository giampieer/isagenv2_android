package layout.Modificar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
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
import android.widget.Toast;

import org.json.JSONObject;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import layout.Login.Conexion;
import principal.android.empresa.isagenmaterial.R;
import principal.android.empresa.isagenmaterial.bean.JefeBean;
import principal.android.empresa.isagenmaterial.dao.AdminDao;


public class perfil_modificar extends Fragment implements View.OnClickListener {
    String cod,nomb,correo,tel,area,id,pass;
    JefeBean objjefebean=null;
    JefeBean obj=null;
    AdminDao objjefedao=null;
    EditText txtnombre,txtcorreo,txttel;
    int estado=0;
    private FloatingActionButton fab,fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    private Boolean isFabOpen = false;
    Session session;
    String correos,contraseña,email,mensaje;
    Toolbar toolbar;
    Conexion objconexion=new Conexion();
    String conexion="";
    String conexion2="";
    String conexion3="";
    public perfil_modificar() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View obj=inflater.inflate(R.layout.fragment_modificar_perfil, container, false);
        toolbar = (Toolbar) obj.findViewById(R.id.toolbar_perfil_modificar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Modificar Perfil");
        fab = (FloatingActionButton)obj.findViewById(R.id.fab_modificar_perfil);
        fab1 = (FloatingActionButton)obj.findViewById(R.id.fab1_modificar_perfil);
        fab2 = (FloatingActionButton)obj.findViewById(R.id.fab1_correo_perfil);
        fab_open = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

        txtnombre=(EditText)obj.findViewById(R.id.txtnombre);
        txtcorreo=(EditText)obj.findViewById(R.id.txtcorreo);
        txttel=(EditText)obj.findViewById(R.id.txttelefono);
        cargardatos();
        return obj;
    }

    class async_actualizar_admin extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Cargando", true);
        }
        @Override
        protected String doInBackground(String... params) {
            String mensaje="";
            mensaje = String.valueOf(objconexion.isConnected(getActivity().getApplicationContext()));
            conexion = mensaje;
            if(conexion.equals("true")){
            objjefebean=new JefeBean();
            objjefedao=new AdminDao();
            obj=new JefeBean();
            objjefebean.setID(params[0]);
            objjefebean.setPASS(params[1]);
            obj=objjefedao.cargar_admin(objjefebean);
            cod = String.valueOf(obj.getCODJEFE());
            nomb = obj.getNOMBJEFE();
            correo= obj.getEMAJEFE();
            tel= obj.getTELFJEFE();
            area= obj.getAREAJEFE();
            id= obj.getID();
            pass= obj.getPASS();}
            return mensaje;
        }
        protected void onPostExecute(String result) {

            if(conexion.equals("true")){
                txtnombre.setText(nomb);
                txtcorreo.setText(correo);
                txttel.setText(tel);
            } else{
                Toast.makeText(getActivity(), "No  hay conexion a internet", Toast.LENGTH_LONG).show();

            }

            progressDialog.dismiss();

        }

    }

    public void cargardatos(){

        SharedPreferences prefs = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        //comillas vacia para si hay error en el id o pass
        String usuario = prefs.getString("ID", "");
        String pass = prefs.getString("PASS", "");
        String param[]=new String[2];
        param[0]=usuario;
        param[1]=pass;
        new async_actualizar_admin().execute(param);
    }


    public void modificar() {
        String TXTNOMBRE, TXTCORREO, TXTTEL,TXTCOD,TXTAREA,TXTID,TXTPASS;

        TXTNOMBRE = txtnombre.getText().toString();
        TXTCORREO= txtcorreo.getText().toString();
        TXTTEL = txttel.getText().toString();
        TXTCOD= cod;
        TXTAREA = area;
        TXTID=id;
        TXTPASS=pass;
        String parametros[]=new String[7];
        parametros[0]=TXTNOMBRE;
        parametros[1]=TXTCORREO;
        parametros[2]=TXTTEL;
        parametros[3]=TXTCOD;
        parametros[4]=TXTAREA;
        parametros[5]=TXTID;
        parametros[6]=TXTPASS;
        if(txtnombre.length()==0){
            txtnombre.setError("INGRESE EL NOMBRE DEL ADMINISTRADOR");
            txtnombre.requestFocus();
        }else if(txtcorreo.length()==0){
            txtcorreo.setError("INGRESE EL CORREO DEL ADMINISTRADOR");
            txtcorreo.requestFocus();
        }else if(txttel.length()==0){
            txttel.setError("INGRESE EL TELEFONO DEL ADMINISTRADOR");
            txttel.requestFocus();
        }else {
            new async_modificar_admin().execute(parametros);
            cargardatos();
        }
    }
    class async_modificar_admin extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Modificando", true);
        }
        @Override
        protected String doInBackground(String... params) {
            obj=new JefeBean();
            objjefedao=new AdminDao();
            String mensaje="";
            mensaje = String.valueOf(objconexion.isConnected(getActivity().getApplicationContext()));
            conexion2 = mensaje;
            if(conexion2.equals("true")){
            obj.setNOMBJEFE(params[0]);
            obj.setEMAJEFE(params[1]);
            obj.setTELFJEFE(params[2]);
            obj.setCODJEFE(Integer.parseInt(params[3]));
            obj.setAREAJEFE(params[4]);
            obj.setID(params[5]);
            obj.setPASS(params[6]);
            estado=objjefedao.Modificar_Admin(obj);}
            return mensaje;
        }
        protected void onPostExecute(String result) {
            if(conexion2.equals("true")){
                if (estado==1){
                    Toast.makeText(getActivity(), "Registro Modificado Satisfactoriamente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Registro No Modificado", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            } else{
                Toast.makeText(getActivity(), "No  hay conexion a internet", Toast.LENGTH_LONG).show();

            }
            progressDialog.dismiss();
        }
    }


    public void enviar_email(){
        email=txtcorreo.getText().toString();//correo del quien va recibir el correo
        correos="isagen24@gmail.com";
        contraseña="isagen2424";
        mensaje="RECORDAR QUE SU CONTRASEÑA DE ADMINISTRADOR ES :"+obj.getPASS();
        String param[]=new String[4];
        param[0]=email;
        param[1]=correos;
        param[2]=contraseña;
        param[3]=mensaje;
        new async_enviar_correo().execute(param);
        cargardatos();
    }

    class async_enviar_correo extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Enviando Correo", true);
        }

        @Override
        protected String doInBackground(String... params) {
            String mensaje="";
            mensaje = String.valueOf(objconexion.isConnected(getActivity().getApplicationContext()));
            conexion3 = mensaje;
            if(conexion3.equals("true")){
            final String ema,con,cor,men;
            ema=params[0];
            cor=params[1];
            con=params[2];
            men=params[3];

            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            try {
                session = Session.getDefaultInstance(props, new javax.mail.Authenticator(){
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(cor,con);
                    }
                });

                if(session!=null){
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(cor));
                    //Adding receiver
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(ema));
                    //Adding subject
                    message.setSubject("Gestion de Proyectos");
                    //Adding message
                    message.setText(men);

                    //Sending email
                    Transport.send(message);
                    Snackbar.make(getView(), "Correo Enviado Satisfactoriamente", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Snackbar.make(getView(), "Correo No Enviado", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }catch (Exception e){
                e.printStackTrace();
            }}

            return mensaje;
        }
        protected void onPostExecute(String result) {

            if(conexion3.equals("true")){
                progressDialog.dismiss();
            } else{
                Toast.makeText(getActivity(), "No  hay conexion a internet", Toast.LENGTH_LONG).show();

            }
        }

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            //fab es el id del floating button
            case R.id.fab_modificar_perfil:
                animateFAB();
                break;
            case R.id.fab1_modificar_perfil:
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿Deseas modificar datos del Administrador?");
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
            case R.id.fab1_correo_perfil:
                AlertDialog.Builder dialogo2 = new AlertDialog.Builder(getActivity());
                dialogo2.setTitle("Importante");
                dialogo2.setMessage("¿Deseas enviar correo con tu contraseña actual?");
                dialogo2.setCancelable(false);
                dialogo2.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        enviar_email();                }
                });
                dialogo2.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo2.show();
        }
    }

    public void animateFAB(){
        if(isFabOpen){
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
        } else {
            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }


}
