package layout.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import layout.Mostrar.Fragment_Listar_Requisito;
import principal.android.empresa.isagenmaterial.R;

/**
 * Created by Home on 15/08/2017.
 */

public class Requisitos extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_requisito);
        cargarFragment();
    }
    @Override
    public void onClick(View v) {
    }
    public void cargarFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment_Listar_Requisito container1Fragment = new Fragment_Listar_Requisito();
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.contenedor_fragments_requisito, container1Fragment).commit();

    }
}
