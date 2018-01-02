package layout.Adapter;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import layout.Activity.Problemas;
import layout.Activity.Proyectos;
import layout.Activity.Requisitos;
import principal.android.empresa.isagenmaterial.R;
public class CardView_Adapter_Proyecto_Menu extends RecyclerView.Adapter<CardView_Adapter_Proyecto_Menu.ViewHolderproyecto_Menu> {
    static  int lastPosition=-1;
   String[] listado;
    public Context context;
    public CardView_Adapter_Proyecto_Menu(String[] lista,Context context1){
        listado=lista;
        context=context1;
    }
    @Override
    //RELACIONAMOS CON EL XML
    public CardView_Adapter_Proyecto_Menu.ViewHolderproyecto_Menu onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_proyecto_menu,parent,false);
        CardView_Adapter_Proyecto_Menu.ViewHolderproyecto_Menu viewHolder=new CardView_Adapter_Proyecto_Menu.ViewHolderproyecto_Menu(view);


        return viewHolder;
    }

    @Override
    //ACTUALIZAR LOS DATOS
    public void onBindViewHolder(CardView_Adapter_Proyecto_Menu.ViewHolderproyecto_Menu holder, int position) {
        //imagen array
        TypedArray imagenarray = context.getResources().obtainTypedArray(R.array.array_proyecto);
        Drawable drawableimagen = imagenarray.getDrawable(position);
        //imagen color
        TypedArray colorarray = context.getResources().obtainTypedArray(R.array.array_proyecto_color);
        Drawable drawablecolor = colorarray.getDrawable(position);
        holder.relativo.setBackground(drawablecolor);
        holder.imagen.setImageDrawable(drawableimagen);
        holder.texto.setText(listado[position]);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            Animacion(holder.cardView,position);
        }
    }

    @Override
    public void onViewAttachedToWindow(CardView_Adapter_Proyecto_Menu.ViewHolderproyecto_Menu holder) {
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
    public  class ViewHolderproyecto_Menu  extends RecyclerView.ViewHolder{
        public TextView texto;
        public CardView cardView;
        public ImageView imagen;
        public RelativeLayout relativo;
        public ViewHolderproyecto_Menu(View itemView1){
            super(itemView1);
            //texto para actualizar
            imagen=(ImageView)itemView1.findViewById(R.id.imagen_proyecto_fragment) ;
            texto=(TextView)itemView1.findViewById(R.id.texto_proyecto_fragment);
            cardView=(CardView) itemView1.findViewById(R.id.cardview_proyecto_fragment);
            relativo=(RelativeLayout)itemView1.findViewById(R.id.relative_proyectos);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int requestCode = getAdapterPosition();
                 if(requestCode==0){
                     if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                         /*Intent obj1= new Intent(context, Proyectos.class);
                         ActivityOptions options0 = ActivityOptions
                                 .makeSceneTransitionAnimation((MainActivity) context, v, v.getTransitionName());
                         context.startActivity(obj1, options0.toBundle());*/
                         Intent obj1= new Intent(context, Proyectos.class);
                         context.startActivity(obj1);
                     }else{
                         Intent obj1= new Intent(context, Proyectos.class);
                         context.startActivity(obj1);
                     }

                 }else{
                     if(requestCode==1){
                         Intent obj1= new Intent(context, Requisitos.class);
                         context.startActivity(obj1);

                     }else{
                         if(requestCode==2){
                             Intent obj1= new Intent(context, Problemas.class);
                             context.startActivity(obj1);
                         }else{
                             if(requestCode==3){
                                 Intent obj1= new Intent(context, Proyectos.class);
                                 context.startActivity(obj1);
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
