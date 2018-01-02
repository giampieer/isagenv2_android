package layout.Adapter;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import layout.Activity.Proyectos;
import layout.Activity.Requisitos;
import layout.Modificar.Fragment_Modificar_Proyecto;
import layout.Mostrar.Fragment_Listar_Proyecto_Menu;
import principal.android.empresa.isagenmaterial.R;

/**
 * Created by Home on 16/08/2017.
 */

public class Cardview_Adapter_Menu extends RecyclerView.Adapter<Cardview_Adapter_Menu.ViewHolderproyecto_Menu_Principal> {
    static  int lastPosition=-1;
    String[] listado;
    public Context context;
    public Cardview_Adapter_Menu(String[] lista,Context context1){
        listado=lista;
        context=context1;
    }
    @Override
    //RELACIONAMOS CON EL XML
    public Cardview_Adapter_Menu.ViewHolderproyecto_Menu_Principal onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_menu_principal,parent,false);
        Cardview_Adapter_Menu.ViewHolderproyecto_Menu_Principal viewHolder=new Cardview_Adapter_Menu.ViewHolderproyecto_Menu_Principal(view);


        return viewHolder;
    }

    @Override
    //ACTUALIZAR LOS DATOS
    public void onBindViewHolder(Cardview_Adapter_Menu.ViewHolderproyecto_Menu_Principal holder, int position) {

        //imagen array
        TypedArray imagenarray = context.getResources().obtainTypedArray(R.array.array_menu);
        Drawable drawableimagen = imagenarray.getDrawable(position);
        //libreria picasso
        String[] lista=new String[]{"http://www.ntacalabria.it/wp-content/uploads/2016/03/progetto-my-web-writing-770x439_c.jpg",
                "http://ngo-advocat.com/sites/default/files/styles/730x412/public/image_uploaded_from_ios.jpg?itok=-Wlbffk-",
                "http://www.blueway.fr/wp-content/uploads/2016/06/ameliorer-qualite-donnees.jpg",
                "http://www.conservatorioperugia.it/upload/immagineTipo2-Ori-912.jpg"};
        //Picasso.with(context).load(lista[position]).into(holder.imagen);
        holder.imagen.setImageDrawable(drawableimagen);
        //imagen color
        TypedArray colorarray = context.getResources().obtainTypedArray(R.array.array_proyecto_color);
        Drawable drawablecolor = colorarray.getDrawable(position);
        holder.relativo.setBackground(drawablecolor);


        holder.texto.setText(listado[position]);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            Animacion(holder.cardView,position);
        }
    }

    @Override
    public void onViewAttachedToWindow(Cardview_Adapter_Menu.ViewHolderproyecto_Menu_Principal holder) {
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
        return null!=listado?listado.length:0;
    }
    //clase cleada para viewholder
    public  class ViewHolderproyecto_Menu_Principal  extends RecyclerView.ViewHolder{
        public TextView texto;
        public CardView cardView;
        public ImageView imagen;
        public RelativeLayout relativo;
        public ViewHolderproyecto_Menu_Principal(View itemView1){
            super(itemView1);
            //texto para actualizar
            imagen=(ImageView)itemView1.findViewById(R.id.imagen_menu_principal) ;
            texto=(TextView)itemView1.findViewById(R.id.texto_menu_principal);
            cardView=(CardView) itemView1.findViewById(R.id.cardview_menu_principal);
            relativo=(RelativeLayout)itemView1.findViewById(R.id.relative_menu_principal);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int requestCode = getAdapterPosition();
                    if(requestCode==0){
                            Fragment_Listar_Proyecto_Menu fragment = new Fragment_Listar_Proyecto_Menu();
                        FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                                .replace(R.id.contenedor_fragments, fragment)
                                .addToBackStack(null)
                                .commit();
                    }else{
                        if(requestCode==1){
                        }else{
                            if(requestCode==2){

                            }else{
                                if(requestCode==3){

                                }
                            }
                        }
                    }
                }
            });
        }
    }


    public void Animacion(View viewToAnimate,int position){
        if(position>lastPosition){
            Animation animation= AnimationUtils.loadAnimation(context,R.anim.left_cardview);
            viewToAnimate.startAnimation(animation);
            lastPosition=position;
        }

    }
}
