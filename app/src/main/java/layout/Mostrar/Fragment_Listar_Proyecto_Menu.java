package layout.Mostrar;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import layout.Adapter.CardView_Adapter_Proyecto_Menu;
import principal.android.empresa.isagenmaterial.R;



public class Fragment_Listar_Proyecto_Menu extends Fragment {
    String[] listado=new String[]{"NUEVO","REQUISITOS","PROBLEMAS","OBJETIVOS"};
    RecyclerView recyclerView;
    RecyclerView.Adapter  adapter;
    public Fragment_Listar_Proyecto_Menu() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View obj=inflater.inflate(R.layout.fragment_lista_proyecto_menu, container, false);
        recyclerView=(RecyclerView)obj.findViewById(R.id.recycler_view_proyecto_fragment_menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        CargarDatosProyecto();
        return obj;
    }
    public void CargarDatosProyecto(){
        new Async_Listar_Proyectos_Framents().execute();
    }
    class Async_Listar_Proyectos_Framents extends AsyncTask<Void, Void, String> {

        private ProgressDialog progressDialog;
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Cargando ", true);
        }
        @Override
        protected String doInBackground(Void... obj) {
            String mensaje="";

            adapter=new CardView_Adapter_Proyecto_Menu(listado,getActivity());
            return mensaje;
        }
        protected void onPostExecute(String result) {
            recyclerView.setAdapter(adapter);
            progressDialog.dismiss();
        }
    }

}
