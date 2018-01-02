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
import layout.Adapter.CardView_Adapter_Requisito;
import layout.Grabar.Fragment_Grabar_Requisito;
import layout.Login.Conexion;
import principal.android.empresa.isagenmaterial.R;
import principal.android.empresa.isagenmaterial.bean.RequisitoBean;
import principal.android.empresa.isagenmaterial.dao.RequisitoDao;

public class Fragment_Listar_Requisito extends Fragment implements View.OnClickListener {
    ArrayList<RequisitoBean> listado;
    RequisitoDao objdao=null;
    RecyclerView recyclerView;
    RecyclerView.Adapter  adapter;
    FloatingActionButton fab ;
    ImageView imagen;
    Toolbar toolbar;
    Conexion objconexion=new Conexion();
    String conexion="";
    public Fragment_Listar_Requisito() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View obj=inflater.inflate(R.layout.fragment_lista_requisito, container, false);
        recyclerView=(RecyclerView)obj.findViewById(R.id.recycler_view_requisito_fragment);
        imagen=(ImageView)obj.findViewById(R.id.imagen_requisito_mostrar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        objdao=new RequisitoDao();
        toolbar = (Toolbar) obj.findViewById(R.id.toolbar_requisito_mostrar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Requisitos");

        fab = (FloatingActionButton) obj.findViewById(R.id.fab_requisito);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                Fragment_Grabar_Requisito container1Fragment = new Fragment_Grabar_Requisito();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    container1Fragment.setSharedElementEnterTransition(TransitionInflater.from(
                            getActivity()).inflateTransition(R.transition.change_image_trans));
                    container1Fragment.setEnterTransition(TransitionInflater.from(
                            getActivity()).inflateTransition(android.R.transition.fade));
                }

                fragmentManager.beginTransaction()
                        .replace(R.id.contenedor_fragments_requisito, container1Fragment)
                        .addToBackStack(null)
                        .addSharedElement(fab,"transicion_imagen_requisito")
                        .commit();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        CargarDatosProyecto();


        return obj;
    }
    @Override
    public void onClick(View v) {
    }

    public void CargarDatosProyecto(){
        new Async_Listar_Requisitos().execute();
    }

    class Async_Listar_Requisitos extends AsyncTask<Void, Void, String> {
        private ProgressDialog progressDialog;
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Cargando Requisitos", true);
        }
        @Override
        protected String doInBackground(Void... obj) {
            String mensaje="";
            mensaje = String.valueOf(objconexion.isConnected(getActivity().getApplicationContext()));
            conexion = mensaje;
            if(conexion.equals("true")){
            listado=objdao.Listar_Requisito();
            adapter=new CardView_Adapter_Requisito(listado,getActivity());}
            return mensaje;
        }
        protected void onPostExecute(String result) {
            if(conexion.equals("true")){
                recyclerView.setAdapter(adapter);}else{
                Toast.makeText(getActivity(), "No  hay conexion a internet", Toast.LENGTH_LONG).show();

            }
            progressDialog.dismiss();
        }
    }}
