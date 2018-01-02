package layout.Adapter;
import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import layout.Login.Conexion;
import layout.Modificar.Fragment_Modificar_Proyecto;
import layout.Mostrar.Fragment_Listar_Proyecto;
import principal.android.empresa.isagenmaterial.R;
import principal.android.empresa.isagenmaterial.bean.ProyectoBean;
import principal.android.empresa.isagenmaterial.dao.ProyectoDao;

public class CardView_Adapter_Proyecto extends RecyclerView.Adapter<CardView_Adapter_Proyecto.ViewHolderproyecto> implements AdapterView.OnItemClickListener {
    static  int lastPosition=-1;
     ArrayList<ProyectoBean>listado;
    Context context;
    int estado=0;
    Conexion objconexion=new Conexion();
    String conexion="";
    public CardView_Adapter_Proyecto(ArrayList<ProyectoBean> lista,Context context1){
        listado=lista;
        context=context1;
    }
    @Override
    //RELACIONAMOS CON EL XML
    public CardView_Adapter_Proyecto.ViewHolderproyecto onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_proyecto,parent,false);
        CardView_Adapter_Proyecto.ViewHolderproyecto viewHolder=new CardView_Adapter_Proyecto.ViewHolderproyecto(view);
        return viewHolder;
    }

    @Override
    //ACTUALIZAR LOS DATOS
    public void onBindViewHolder(final CardView_Adapter_Proyecto.ViewHolderproyecto holder, final int position) {
        holder.texto.setText(listado.get(position).getTitulo());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final CharSequence[] items = {"MODIFICAR", "ELIMINAR"};
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                dialogo1.setTitle("Opcion:");
                dialogo1.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if(items[item]=="MODIFICAR"){
                            Fragment_Modificar_Proyecto fragment = new Fragment_Modificar_Proyecto();

                            //enviar datos a solo fragments
                            Bundle parametro = new Bundle();
                            parametro.putString("numero_proyecto",listado.get(position).getNumero());
                            parametro.putString("titulo_proyecto",listado.get(position).getTitulo());
                            parametro.putString("duracion_proyecto",listado.get(position).getDuracion());
                            parametro.putString("descripcion_proyecto",listado.get(position).getDescripcion());
                            parametro.putString("fases_proyecto",listado.get(position).getFases());
                            parametro.putString("inicio_proyecto",listado.get(position).getInicio());
                            parametro.putString("fin_proyecto",listado.get(position).getFin());
                            parametro.putString("gastos_proyecto",listado.get(position).getGastos());
                            parametro.putString("tipo",listado.get(position).getTipo());
                            parametro.putString("jefe_proyecto",listado.get(position).getCODJEFE());

                            fragment.setArguments(parametro);
                            FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                            fragmentManager.beginTransaction()
                                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                                    .replace(R.id.contenedor_fragments_proyecto, fragment)
                                    .addToBackStack(null)
                                    .commit();
                        }else{

                            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                            dialogo1.setTitle("Importante");
                            dialogo1.setMessage("¿Deseas eliminar el proyecto?");
                            dialogo1.setCancelable(false);
                            dialogo1.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                    String numero_proyecto;
                                    numero_proyecto= listado.get(position).getNumero();
                                    String parametros[]=new String[1];
                                    parametros[0]=numero_proyecto;
                                    new Async_Eliminar_Proyecto().execute(parametros);                       }
                            });
                            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {

                                }
                            });
                            dialogo1.show();
                        }
                            }});
                AlertDialog alert = dialogo1.create();
                alert.show();
            }});
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            Animacion(holder.cardView,position);
        }
        }
    class Async_Eliminar_Proyecto extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context, "", "Eliminando", true);
        }
        @Override
        protected String doInBackground(String... params) {
            ProyectoDao objdao=new ProyectoDao();
            String mensaje="";
            mensaje = String.valueOf(objconexion.isConnected(context));
            conexion = mensaje;
            if(conexion.equals("true")){
            String numero_proyecto=params[0];
            estado=objdao.Eliminar_Proyecto(numero_proyecto);}
            return mensaje;
        }
        protected void onPostExecute(String result) {
            if(conexion.equals("true")){
                if (estado==1){
                    Toast.makeText(context, "Registro Eliminado Satisfactoriamente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Registro No Eliminado", Toast.LENGTH_SHORT).show();
                }
            FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
            Fragment_Listar_Proyecto container1Fragment = new Fragment_Listar_Proyecto();
            //animacion adelantar R.anim.enter_from_right, R.anim.exit_to_left
            fragmentManager.beginTransaction().
                    setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.contenedor_fragments_proyecto, container1Fragment)
                    .commit();

            }else{
                Toast.makeText(context, "No  hay conexion a internet", Toast.LENGTH_LONG).show();

            }
            progressDialog.dismiss();
        }

    }

    @Override
    public void onViewAttachedToWindow(ViewHolderproyecto holder) {
        super.onViewAttachedToWindow(holder);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            animateCircularReveal(holder.itemView);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        }

    public void animateCircularReveal(View view) {
        int centerX = 0;
        int centerY = 0;
        int startRadius = 0;
        int endRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animation = null;
            animation = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
        view.setVisibility(View.VISIBLE);
        animation.start();
    }

    @Override
    //REGRESAR EL TAMÑO DEL ARRAY
    public int getItemCount() {
        return null!=listado?listado.size():0;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    //clase cleada para viewholder
    public static class ViewHolderproyecto  extends RecyclerView.ViewHolder{
        public TextView texto;
        public CardView cardView;
        public ImageView imagen;
        public ViewHolderproyecto(View itemView){
            super(itemView);
            //texto para actualizar
            texto=(TextView)itemView.findViewById(R.id.texto_proyecto);
            cardView=(CardView)itemView.findViewById(R.id.cardview_proyecto);
            imagen=(ImageView)itemView.findViewById(R.id.imagen_proyecto);
        }}



    public void Animacion(View viewToAnimate,int position){
        if(position>lastPosition){
            Animation animation= AnimationUtils.loadAnimation(context,R.anim.left_cardview);
            viewToAnimate.startAnimation(animation);
            lastPosition=position;
        }

    }
}
