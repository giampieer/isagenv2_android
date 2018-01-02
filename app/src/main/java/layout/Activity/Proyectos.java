package layout.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import layout.Mostrar.Fragment_Listar_Proyecto;
import principal.android.empresa.isagenmaterial.R;

public class Proyectos extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_proyecto);
        cargarFragment();
    }
    @Override
    public void onClick(View v) {
    }
    public void cargarFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment_Listar_Proyecto container1Fragment = new Fragment_Listar_Proyecto();
        //animacion adelantar R.anim.enter_from_right, R.anim.exit_to_left
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.contenedor_fragments_proyecto, container1Fragment).commit();

    }
}
