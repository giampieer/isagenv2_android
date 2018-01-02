package layout.Adapter;
import android.animation.Animator;
import android.content.Context;
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
import java.util.ArrayList;
import principal.android.empresa.isagenmaterial.R;
import principal.android.empresa.isagenmaterial.bean.ProyectoBean;

/**
 * Created by Home on 16/08/2017.
 */

public class Cardview_Adapter_Problema extends RecyclerView.Adapter<Cardview_Adapter_Problema.ViewHolderproblema> implements AdapterView.OnItemClickListener {
    static  int lastPosition=-1;
    ArrayList<ProyectoBean> listado;
    Context context;
    int estado=0;
    public Cardview_Adapter_Problema(ArrayList<ProyectoBean> lista,Context context1){
        listado=lista;
        context=context1;
    }
    @Override
    //RELACIONAMOS CON EL XML
    public Cardview_Adapter_Problema.ViewHolderproblema onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_problema,parent,false);
        Cardview_Adapter_Problema.ViewHolderproblema viewHolder=new Cardview_Adapter_Problema.ViewHolderproblema(view);
        return viewHolder;
    }

    @Override
    //ACTUALIZAR LOS DATOS
    public void onBindViewHolder(final Cardview_Adapter_Problema.ViewHolderproblema holder, final int position) {
        holder.texto.setText(listado.get(position).getTitulo());
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            Animacion(holder.cardView,position);
        }
    }


    @Override
    public void onViewAttachedToWindow(Cardview_Adapter_Problema.ViewHolderproblema holder) {
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
    public static class ViewHolderproblema  extends RecyclerView.ViewHolder{
        public TextView texto;
        public CardView cardView;
        public ImageView imagen;
        public ViewHolderproblema(View itemView){
            super(itemView);
            //texto para actualizar
            texto=(TextView)itemView.findViewById(R.id.texto_problema);
            cardView=(CardView)itemView.findViewById(R.id.cardview_problema);
            imagen=(ImageView)itemView.findViewById(R.id.imagen_problema);
        }}



    public void Animacion(View viewToAnimate,int position){
        if(position>lastPosition){
            Animation animation= AnimationUtils.loadAnimation(context,R.anim.left_cardview);
            viewToAnimate.startAnimation(animation);
            lastPosition=position;
        }

    }
}
