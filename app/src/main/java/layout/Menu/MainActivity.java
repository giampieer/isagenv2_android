package layout.Menu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import layout.Activity.perfil;
import layout.Login.Loading;
import layout.Mostrar.Fragment_Listar_Proyecto_Menu;
import layout.Mostrar.Fragment_Listar_Proyecto;
import layout.Mostrar.inicio;
import principal.android.empresa.isagenmaterial.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    TextView admin,correo;
    String nombadmin,nombcorreo;
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inicio();

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //CAPTURAR DATOS DEL LOGIN
        SharedPreferences prefs = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        //comillas vacia para si hay error en el id o pass
        nombadmin = prefs.getString("ID", "");
        nombcorreo= prefs.getString("EMAIL", "@gmail.com");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //INGRESAR DATOS EN NAVIGATION DRAWER
        View hView = navigationView.getHeaderView(0);
        admin= (TextView) hView.findViewById(R.id.nombre_usuario);
        correo = (TextView) hView.findViewById(R.id.correo_usuario);
        admin.setText("ADMINISTRADOR: "+nombadmin.toUpperCase());
        correo.setText(nombcorreo);

        navigationView.setNavigationItemSelectedListener(this);
    }
    public void inicio(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        inicio container1Fragment = new inicio();
        fragmentManager.beginTransaction().replace(R.id.contenedor_fragments, container1Fragment).commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cerrar_sesion) {
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MainActivity.this);
            dialogo1.setTitle("Importante");
            dialogo1.setMessage("¿Deseas cerrar sesion ?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    cerrar_sesion();                }
            });
            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                }
            });
            dialogo1.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.proyecto) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment_Listar_Proyecto_Menu container1Fragment = new Fragment_Listar_Proyecto_Menu();
            //animacion adelantar R.anim.enter_from_right, R.anim.exit_to_left
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.contenedor_fragments, container1Fragment).addToBackStack(null).commit();
        } else if (id == R.id.personal) {
            Intent objIntent=new Intent(MainActivity.this,Fragment_Listar_Proyecto.class);
            startActivity(objIntent);
            //finish();
        } else if (id == R.id.seguimiento) {

        } else if (id == R.id.cronograma) {

        }else if(id==R.id.modificar_usuario){
            Intent objIntent=new Intent(MainActivity.this,perfil.class);
            startActivity(objIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

    }


    public void cerrar_sesion(){
        //limpiar los datos almacenados para inicar sesion
        SharedPreferences prefs = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
        Intent intent=new Intent(MainActivity.this,Loading.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

}
