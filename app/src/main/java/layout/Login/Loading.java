package layout.Login;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import layout.Menu.MainActivity;
import principal.android.empresa.isagenmaterial.R;
import principal.android.empresa.isagenmaterial.bean.JefeBean;
import principal.android.empresa.isagenmaterial.dao.ProyectoDao;

public class Loading extends AppCompatActivity {
    JefeBean obj = null;
    ProyectoDao obj2 = null;
    int estado = 0;
    String conexion = "";
    String nomb, contra;
    ProyectoDao objdao = null;
    JefeBean objbean = null;
    Conexion objconexion=new Conexion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        //session automatico
        conexion_internet();
    }

    public void sesion() {
        objdao = new ProyectoDao();
        objbean = new JefeBean();
        //captura los datos almacenados para iniciar seison automatico
        SharedPreferences prefs = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        //comillas vacia para si hay error en el id o pass
        String usuario = prefs.getString("ID", "");
        String pass = prefs.getString("PASS", "");
        String param[] = new String[2];
        param[0] = usuario;
        param[1] = pass;
        new sesion_automatico().execute(param);
    }

    class sesion_automatico extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Loading.this, "","Cargando ...", true);
        }
        @Override
        protected String doInBackground(String... params) {
            obj=new JefeBean();
            obj2=new ProyectoDao();
            String mensaje="";
            obj.setID(params[0]);
            obj.setPASS(params[1]);
            nomb=params[0];
            contra=params[1];
            estado=obj2.login_jefe(obj);
            return mensaje;
        }
        protected void onPostExecute(String result) {
            if (estado == 1) {
                Intent objIntent = new Intent(Loading.this, MainActivity.class);
                //EVITAR APARESCA TECLADO
                objIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(objIntent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                finish();
                Toast.makeText(getApplicationContext(), "BIENVENIDO :" + nomb.toUpperCase() + "", Toast.LENGTH_LONG).show();
            } else {
                Intent objIntent = new Intent(Loading.this, login.class);
                //EVITAR APARESCA TECLADO
                objIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(objIntent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                finish();
                Toast.makeText(getApplicationContext(), "INICIE SESION", Toast.LENGTH_LONG).show();
            }
            progressDialog.dismiss();
        }
    }


    public void conexion_internet() {
        new sesion_automaticos().execute();

    }

    class sesion_automaticos extends AsyncTask<String, Void, Void> {
         private ProgressDialog progressDialog;
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Loading.this, "", "Conectando ...", true);
        }
        @Override
        protected Void doInBackground(String... params) {
            String mensaje = "";
            mensaje = String.valueOf(objconexion.isConnected(getApplicationContext()));
            conexion = mensaje;
            return null;
        }
        protected void onPostExecute(Void result) {

            if (conexion.equals("false")) {
                Intent objIntent = new Intent(Loading.this, login.class);
                //EVITAR APARESCA TECLADO
                objIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(objIntent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                finish();
                Toast.makeText(getApplicationContext(), "No hay conexión a internet ", Toast.LENGTH_SHORT).show();
            } else {
                if (conexion.equals("true")) {
                    sesion();
                }}
            progressDialog.dismiss();
        }
    }
}
