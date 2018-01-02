package layout.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import layout.Modificar.perfil_modificar;
import principal.android.empresa.isagenmaterial.R;

public class perfil extends AppCompatActivity implements View.OnClickListener {
    public perfil() {
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        cargardatos();
    }

    public void cargardatos(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        perfil_modificar container1Fragment = new perfil_modificar();
        //animacion adelantar R.anim.enter_from_right, R.anim.exit_to_left
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.contenedor_fragments_perfil, container1Fragment)
                .commit();

    }
    @Override
    public void onClick(View v) {

    }
}
