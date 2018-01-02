package layout.Mostrar;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import layout.Adapter.CardView_Adapter_Proyecto;
import layout.Adapter.Cardview_Adapter_Problema;
import layout.Grabar.Fragment_Grabar_Proyecto;
import layout.Login.Conexion;
import principal.android.empresa.isagenmaterial.R;
import principal.android.empresa.isagenmaterial.bean.ProyectoBean;
import principal.android.empresa.isagenmaterial.dao.ProyectoDao;

/**
 * Created by Home on 16/08/2017.
 */

public class Fragment_Listar_Problema extends Fragment implements View.OnClickListener{

    ArrayList<ProyectoBean> listado;
    ProyectoDao objdao=null;
    RecyclerView recyclerView;
    RecyclerView.Adapter  adapter;
    FloatingActionButton fab ;
    ImageView imagen;
    Toolbar toolbar;
    Conexion objconexion=new Conexion();
    String conexion="";
    public Fragment_Listar_Problema() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View obj=inflater.inflate(R.layout.fragment_lista_problema, container, false);
        recyclerView=(RecyclerView)obj.findViewById(R.id.recycler_view_problema_fragment);
        imagen=(ImageView)obj.findViewById(R.id.imagen_problema_mostrar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        objdao=new ProyectoDao();
        toolbar = (Toolbar) obj.findViewById(R.id.toolbar_problema_mostrar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Problemas");
        fab = (FloatingActionButton) obj.findViewById(R.id.fab_problema);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        CargarDatosProblemas();


        return obj;
    }
    @Override
    public void onClick(View v) {
    }

    public void CargarDatosProblemas(){
        new Async_Listar_Problemas().execute();
    }

    class Async_Listar_Problemas extends AsyncTask<Void, Void, String> {

        private ProgressDialog progressDialog;
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Cargando Problemas", true);
        }
        @Override
        protected String doInBackground(Void... obj) {
            String mensaje="";
            mensaje = String.valueOf(objconexion.isConnected(getActivity().getApplicationContext()));
            conexion = mensaje;
            if(conexion.equals("true")){
            objdao=new ProyectoDao();
            listado=objdao.Listar_Proyecto();
            adapter=new Cardview_Adapter_Problema(listado,getActivity());}
            return mensaje;
        }
        protected void onPostExecute(String result) {
            if(conexion.equals("true")){
                recyclerView.setAdapter(adapter);}else{
                Toast.makeText(getActivity(), "No  hay conexion a internet", Toast.LENGTH_LONG).show();

            }            progressDialog.dismiss();
        }
    }
}
