package layout.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import layout.Menu.MainActivity;
import principal.android.empresa.isagenmaterial.R;
import principal.android.empresa.isagenmaterial.bean.JefeBean;
import principal.android.empresa.isagenmaterial.dao.AdminDao;
import principal.android.empresa.isagenmaterial.dao.ProyectoDao;

public class login extends AppCompatActivity implements View.OnClickListener {
    JefeBean obj=null;
    AdminDao objjefedao=null;
    ProyectoDao obj2=null;
    Spinner cbotipousu;
    ArrayAdapter<CharSequence> adapter;
    EditText txtusuario,txtclave;
    Button btnentrar;
    String conexion = "";
    int estado=0;
    String nomb,contra;
    String correo;
    private VideoView mVideoView;
    Conexion objconexion=new Conexion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        cbotipousu=(Spinner)findViewById(R.id.cbotipousu);
        btnentrar=(Button)findViewById(R.id.btnentrar);
        txtusuario=(EditText)findViewById(R.id.txtusuario);
        txtclave=(EditText)findViewById(R.id.txtclave);
        btnentrar.setOnClickListener(this);
        adapter= ArrayAdapter.createFromResource(this, R.array.tipousu_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbotipousu.setAdapter(adapter);
        //video  fondo
        mVideoView = (VideoView) findViewById(R.id.bgVideoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.loginvideo);
        mVideoView.setVideoURI(uri);
        mVideoView.start();
        //repeticion video
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
    }

    public void Entrar()
    {
        String usuario,clave;
        usuario = txtusuario.getText().toString();
        clave = txtclave.getText().toString();
        String parametros[]=new String[2];
        parametros[0]=usuario;
        parametros[1]=clave;
        try
        {
            String estado2="6";
            if(cbotipousu.getSelectedItemId()==0)
            {
                Toast.makeText(getApplicationContext(),"No selecciono ninguna opcion", Toast.LENGTH_LONG).show();
                cbotipousu.requestFocus();
            }else if (txtusuario.length() == 0) {
                txtusuario.setError("INGRESE POR FAVOR EL USUARIO!");
                txtusuario.requestFocus();
            } else if (txtclave.length() == 0) {
                txtclave.setError("INGRESE POR FAVOR LA CONTRASEÑA!");
                txtclave.requestFocus();
            }else
            if(cbotipousu.getSelectedItemId()==1)
            {
                    new async_login_jefe().execute(parametros);
            }
            else
            if(cbotipousu.getSelectedItemId()==2) {
                if (estado2.equalsIgnoreCase("1")) {
                    Intent objIntent = new Intent(login.this, MainActivity.class);
                    Bundle objBundle = new Bundle();
                    objBundle.putString("usuario", usuario);
                    objIntent.putExtras(objBundle);
                    startActivity(objIntent);
                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                    Toast.makeText(getApplicationContext(), "BIENVENIDO PERSONAL A  ISAGEN", Toast.LENGTH_LONG).show();
                } else if (estado2 != "1") {
                    Toast.makeText(getApplicationContext(), "Usuario y clave Incorrectos, por favor Ingrese Nuevamente", Toast.LENGTH_LONG).show();
                    txtusuario.setText("");
                    cbotipousu.requestFocus();
                    txtclave.setText("");
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }




    @Override
    public void onClick(View v) {
        if(v==btnentrar)
        {
            Entrar();
        }
    }
    class async_login_jefe extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(login.this, "","Login...." , true);
        }
        @Override
        protected String doInBackground(String... params) {
            String mensaje="";
            mensaje = String.valueOf(objconexion.isConnected(getApplicationContext()));
            conexion = mensaje;
            if(conexion.equals("true")){
                obj=new JefeBean();
                obj2=new ProyectoDao();
                obj.setID(params[0]);
                obj.setPASS(params[1]);
                nomb=params[0];
                contra=params[1];
                estado=obj2.login_jefe(obj);
//capturar gmail para navigation drawer
                if(estado==1){
                    objjefedao=new AdminDao();
                    obj=objjefedao.cargar_admin(obj);
                    correo=obj.getEMAJEFE();
                }
            }


            return mensaje;
        }
        protected void onPostExecute(String result) {
//post
            if(conexion.equals("true")){
                if (estado == 1) {
                    //cambikarde activity
                    Intent objIntent = new Intent(login.this, MainActivity.class);
                    startActivity(objIntent);
                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                    //toast simepre tiene que estar en postexecute
                    //Toast.makeText(getApplicationContext(), "BIENVENIDO :"+nomb.toUpperCase()+"", Toast.LENGTH_LONG).show();
                    //almacena datos del login para inicar sesion automaticamnete
                    SharedPreferences prefs = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("ID", nomb);
                    editor.putString("PASS", contra);
                    editor.putString("EMAIL",correo);
                    editor.commit();
                    //no se pueda retroceder
                    finish();
                    //toast siempres en postexecute no en doinbackground
                    Toast.makeText(getApplicationContext(), "BIENVENIDO :" + nomb.toUpperCase() + "", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Usuario y clave Incorrectos, por favor Ingrese Nuevamente", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "No  hay conexion a internet", Toast.LENGTH_LONG).show();

            }


            progressDialog.dismiss();
        }
    }

}
