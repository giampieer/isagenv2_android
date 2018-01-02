package layout.Modificar;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import layout.Fechas.FechaFin;
import layout.Fechas.FechaInicio;
import layout.Login.Conexion;
import layout.Login.Loading;
import principal.android.empresa.isagenmaterial.R;
import principal.android.empresa.isagenmaterial.bean.ProyectoBean;
import principal.android.empresa.isagenmaterial.dao.ProyectoDao;


public class Fragment_Modificar_Proyecto extends Fragment implements View.OnClickListener {
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    int estado=0;
    ImageView imagen;
    Toolbar toolbar;
    Conexion objconexion=new Conexion();
    String conexion="";
    String conexion2="";
        String cod,nomb,dura,descrip,fases,inicio,fin,gastos,coord;
        Spinner tipo;
        TextView TXTINICIO,TXTFIN;
        EditText TXTNUMERO,TXTTITULO,TXTDURACION,TXTDESCRIPCION,TXTFASES,TXTGASTOS,TXTCOD;
        ProyectoDao obj=null;
        ProyectoBean obj2=null;

//PDF
    private final static String NOMBRE_DIRECTORIO = "PDF Gestion de Proyectos";
    private final static String NOMBRE_DOCUMENTO = "Proyecto.pdf";
    private final static String ETIQUETA_ERROR = "ERROR";
    private static final int NOTIF_ALERTA_ID = 1;
    ProyectoBean objbean=null;
    ProyectoDao objdao=null;
        public Fragment_Modificar_Proyecto() {
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View obj=inflater.inflate(R.layout.fragment_modificar_proyecto, container, false);
            imagen=(ImageView)obj.findViewById(R.id.imagen_proyecto_modificar);
            toolbar = (Toolbar) obj.findViewById(R.id.toolbar_proyecto_modificar);
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Modificar Proyectos");

            fab = (FloatingActionButton)obj.findViewById(R.id.fab_modificar_proyecto);
            fab1 = (FloatingActionButton)obj.findViewById(R.id.fab1_modificar_proyecto);
            fab2 = (FloatingActionButton)obj.findViewById(R.id.fab1_pdf_proyecto);
            fab_open = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
            fab_close = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);
            rotate_forward = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_forward);
            rotate_backward = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_backward);
            fab.setOnClickListener(this);
            fab1.setOnClickListener(this);
            fab2.setOnClickListener(this);

            TXTNUMERO=(EditText)obj.findViewById(R.id.numero_proyecto);
            TXTTITULO=(EditText)obj.findViewById(R.id.titulo_proyecto);
            TXTDURACION=(EditText)obj.findViewById(R.id.duracion_proyecto);
            TXTDESCRIPCION=(EditText)obj.findViewById(R.id.descripcion_proyecto);
            TXTFASES=(EditText)obj.findViewById(R.id.fases_proyecto);
            TXTINICIO=(TextView) obj.findViewById(R.id.fechainicio_proyecto);
            TXTINICIO.setOnClickListener(this);
            TXTFIN=(TextView) obj.findViewById(R.id.fechafin_proyecto);
            TXTFIN.setOnClickListener(this);
            TXTGASTOS=(EditText)obj.findViewById(R.id.gastos_proyecto);
            TXTCOD=(EditText)obj.findViewById(R.id.jefe_proyecto);

            tipo = (Spinner) obj.findViewById(R.id.tipo_proyecto);
            String[] valores = {"Publicos","Privados","Experimentales","Normalizados","Productivos","Sociales", "Investigacion"};
            tipo.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, valores));

            cargardatos();
            return obj;
        }


        public void modificar() {
            String TXTNUM, TXTTIT, TXTTIP, TXTDUR, TXTDES, TXTFAS, TXTINI, TXTFI, TXTGAS, TXTCON;

            TXTCON=TXTCOD.getText().toString();
            TXTNUM = TXTNUMERO.getText().toString();
            TXTTIT = TXTTITULO.getText().toString();
            TXTTIP =tipo.getItemAtPosition(tipo.getSelectedItemPosition()).toString();
            TXTDUR = TXTDURACION.getText().toString();
            TXTDES = TXTDESCRIPCION.getText().toString();
            TXTFAS = TXTFASES.getText().toString();
            TXTINI = TXTINICIO.getText().toString();
            TXTFI = TXTFIN.getText().toString();
            TXTGAS = TXTGASTOS.getText().toString();
            String parametros[]=new String[10];
            parametros[0]=TXTNUM;
            parametros[1]=TXTTIT;
            parametros[2]=TXTTIP;
            parametros[3]=TXTDUR;
            parametros[4]=TXTDES;
            parametros[5]=TXTFAS;
            parametros[6]=TXTINI;
            parametros[7]=TXTFI;
            parametros[8]=TXTGAS;
            parametros[9]=TXTCON;

            if(TXTNUMERO.length()==0){
                TXTNUMERO.setError("INGRESE EL NUMERO");
                TXTNUMERO.requestFocus();
            }else if(TXTTITULO.length()==0){
                TXTTITULO.setError("INGRESE EL TITULO DEL PROYECTO");
                TXTTITULO.requestFocus();
            }else if(TXTDURACION.length()==0){
                TXTDURACION.setError("INGRESE LA DURACION");
                TXTDURACION.requestFocus();
            }else if(TXTDESCRIPCION.length()==0){
                TXTDESCRIPCION.setError("INGRESE LA DESCRIPCION");
                TXTDESCRIPCION.requestFocus();
            }else if(TXTFASES.length()==0){
                TXTFASES.setError("INGRESE LA CANT DE FASES");
                TXTFASES.requestFocus();
            }else if(TXTINICIO.length()==0){
                TXTINICIO.setError("INGRESE LA FECHA DE INICIO");
                TXTINICIO.requestFocus();
            }else if(TXTFIN.length()==0){
                TXTFIN.setError("INGRESE LA FECHA DE ENTREGA");
                TXTFIN.requestFocus();
            }else if(TXTGASTOS.length()==0){
                TXTGASTOS.setError("INGRESE EL PRESUPUESTO");
                TXTGASTOS.requestFocus();
            }else {
                new Async_Modificar_Proyecto().execute(parametros);
            }
        }
        //ASYNCTASK

class Async_Modificar_Proyecto extends AsyncTask<String, Void, String> {
    private ProgressDialog progressDialog;
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(getActivity(), "", "Modificando", true);
    }

    @Override
    protected String doInBackground(String... params) {
        obj=new ProyectoDao();
        obj2=new ProyectoBean();
        String mensaje="";
        mensaje = String.valueOf(objconexion.isConnected(getActivity().getApplicationContext()));
        conexion = mensaje;
        if(conexion.equals("true")){
        obj2.setNumero(params[0]);
        obj2.setTitulo(params[1]);
        obj2.setTipo(params[2]);
        obj2.setDuracion(params[3]);
        obj2.setDescripcion(params[4]);
        obj2.setFases(params[5]);
        obj2.setInicio(params[6]);
        obj2.setFin(params[7]);
        obj2.setGastos(params[8]);
        obj2.setCODJEFE(params[9]);
        estado=obj.Modificar_Proyecto(obj2);}
        return mensaje;
    }
    protected void onPostExecute(String result) {
        if(conexion.equals("true")){
        if (estado==1){
            Toast.makeText(getActivity(), "Registro Modificado Satisfactoriamente", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), "Registro No Modificado", Toast.LENGTH_SHORT).show();
        }
        retroceder();
        } else{
        Toast.makeText(getActivity(), "No  hay conexion a internet", Toast.LENGTH_LONG).show();

    }        progressDialog.dismiss();
    }
}

class Async_Cargar_Proyecto extends AsyncTask<Void, Void, String> {


    private ProgressDialog progressDialog;
  protected void onPreExecute() {
        progressDialog = ProgressDialog.show(getActivity(), "", "Cargando", true);

    }

    @Override
    protected String doInBackground(Void... obj) {
        String mensaje="";
        mensaje = String.valueOf(objconexion.isConnected(getActivity().getApplicationContext()));
        conexion2 = mensaje;
        if(conexion2.equals("true")){
        cod = getArguments().getString("numero_proyecto");
        nomb = getArguments().getString("titulo_proyecto");
        dura= getArguments().getString("duracion_proyecto");
        descrip = getArguments().getString("descripcion_proyecto");
        fases= getArguments().getString("fases_proyecto");
        inicio= getArguments().getString("inicio_proyecto");
        fin= getArguments().getString("fin_proyecto");
        gastos = getArguments().getString("gastos_proyecto");
        coord= getArguments().getString("jefe_proyecto");}
        return mensaje;
    }
    protected void onPostExecute(String result) {
        if(conexion2.equals("true")){
        TXTNUMERO.setText(cod);
        TXTNUMERO.setEnabled(false);
        TXTTITULO.setText(nomb);
        TXTDURACION.setText(dura);
        TXTDESCRIPCION.setText(descrip);
        TXTFASES.setText(fases);
        TXTINICIO.setText(inicio);
        TXTFIN.setText(fin);
        TXTGASTOS.setText(gastos);
        TXTCOD.setText(coord);
        } else{
            Toast.makeText(getActivity(), "No  hay conexion a internet", Toast.LENGTH_LONG).show();

        }
        progressDialog.dismiss();
    }
}


    @Override
    public void onClick(View v) {
        if (v == TXTINICIO) {
            FechaInicio(getView());
        }

        if (v == TXTFIN) {
            FechaFin(getView());
        }
        int id = v.getId();
        switch (id){
            //fab es el id del floating button
            case R.id.fab_modificar_proyecto:
                animateFAB();
                break;
            case R.id.fab1_modificar_proyecto:
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿Deseas modificar el proyecto?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        modificar();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                dialogo1.show();
                break;
            case R.id.fab1_pdf_proyecto:
                AlertDialog.Builder dialogo2 = new AlertDialog.Builder(getActivity());
                dialogo2.setTitle("Importante");
                dialogo2.setMessage("¿Deseas crear pdf del proyecto?");
                dialogo2.setCancelable(false);
                dialogo2.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        pdf_proyecto();
                    }
                });
                dialogo2.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo2.show();
        }

    }

    public void FechaInicio(View v) {
        DialogFragment newFragment = new FechaInicio();
        newFragment.show(getActivity().getSupportFragmentManager(),"datePicker");
    }
    public void FechaFin(View v) {
        DialogFragment newFragment = new FechaFin();
        newFragment.show(getActivity().getSupportFragmentManager(),"datePicker");
    }
    public void retroceder(){
        getActivity().onBackPressed();
    }
    public void cargardatos(){
        new Async_Cargar_Proyecto().execute();
    }

    public void animateFAB(){
        if(isFabOpen){
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
        } else {
            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }
    public void pdf_proyecto(){
        new async_generar_pdf().execute();
    }
    class async_generar_pdf extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;
        JSONObject objeto;
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Generando PDF", true);
        }

        @Override
        protected String doInBackground(String... params) {
            String mensaje = "";
            // Creamos el documento.
            Document documento = new Document();
            try {
                objbean=new ProyectoBean();
                objdao=new ProyectoDao();
                int num=Integer.parseInt(getArguments().getString("numero_proyecto"));
                objbean=objdao.Listar_Proyecto_pdf(num);
                // Creamos el fichero con el nombre que deseemos.
                File f = crearFichero(NOMBRE_DOCUMENTO);
                // Creamos el flujo de datos de salida para el fichero donde
                // guardaremos el pdf.
                FileOutputStream ficheroPdf = new FileOutputStream(
                        f.getAbsolutePath());
                // Asociamos el flujo que acabamos de crear al documento.
                PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);
                documento.open();
                Paragraph par1=new Paragraph();
                Font fontitulo= FontFactory.getFont(FontFactory.HELVETICA, 16,
                        Font.BOLD);
                par1.add(new Phrase("GESTION DE PROYECTOS DE LA EMPRESA ISAGEN ",fontitulo));
                par1.setAlignment(Element.ALIGN_CENTER);
                par1.add(new Phrase(Chunk.NEWLINE));
                par1.add(new Phrase(Chunk.NEWLINE));
                documento.add(par1);
                Paragraph par2=new Paragraph();
                Font descripcion = FontFactory.getFont(FontFactory.HELVETICA, 13,
                        Font.BOLD);
                par2.add(new Phrase("INFORMACION DEL PROYECTO ",descripcion));
                par2.setAlignment(Element.ALIGN_CENTER);
                par2.add(new Phrase(Chunk.NEWLINE));
                par2.add(new Phrase(Chunk.NEWLINE));
                documento.add(par2);
                PdfPTable tabla=new PdfPTable(10);
                tabla.setWidthPercentage(100);
                PdfPCell celda1=new PdfPCell(new Paragraph("NUM. NUMERO", FontFactory.getFont("Arial",12, Font.BOLD )));
                PdfPCell celda2=new PdfPCell(new Paragraph("TITULO", FontFactory.getFont("Arial",12, Font.BOLD )));
                PdfPCell celda3=new PdfPCell(new Paragraph("DURACION", FontFactory.getFont("Arial",12, Font.BOLD )));
                PdfPCell celda4=new PdfPCell(new Paragraph("DESCRIPCION", FontFactory.getFont("Arial",12, Font.BOLD )));
                PdfPCell celda5=new PdfPCell(new Paragraph("TIPO", FontFactory.getFont("Arial",12, Font.BOLD )));
                PdfPCell celda6=new PdfPCell(new Paragraph("FASES", FontFactory.getFont("Arial",12, Font.BOLD)));
                PdfPCell celda7=new PdfPCell(new Paragraph("INICIO", FontFactory.getFont("Arial",12, Font.BOLD)));
                PdfPCell celda8=new PdfPCell(new Paragraph("FIN", FontFactory.getFont("Arial",12, Font.BOLD)));
                PdfPCell celda9=new PdfPCell(new Paragraph("GASTOS", FontFactory.getFont("Arial",12, Font.BOLD)));
                PdfPCell celda10=new PdfPCell(new Paragraph("CODIGO JEFE", FontFactory.getFont("Arial",12, Font.BOLD )));
                tabla.addCell(celda1);
                tabla.addCell(celda2);
                tabla.addCell(celda3);
                tabla.addCell(celda4);
                tabla.addCell(celda5);
                tabla.addCell(celda6);
                tabla.addCell(celda7);
                tabla.addCell(celda8);
                tabla.addCell(celda9);
                tabla.addCell(celda10);
                tabla.addCell(objbean.getNumero());
                tabla.addCell(objbean.getTitulo());
                tabla.addCell(objbean.getDuracion());
                tabla.addCell(objbean.getDescripcion());
                tabla.addCell(objbean.getTipo());
                tabla.addCell(objbean.getFases());
                tabla.addCell(objbean.getInicio());
                tabla.addCell(objbean.getFin());
                tabla.addCell(objbean.getGastos());
                tabla.addCell(objbean.getCODJEFE());
                documento.add(tabla);
                //CREAR NOTIFICACIONES
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getActivity())
                                .setSmallIcon(R.drawable.profile)
                                .setLargeIcon((((BitmapDrawable)getResources()
                                        .getDrawable(R.drawable.isagen)).getBitmap()))
                                .setContentTitle("Isagen")
                                .setContentText("Se genero PDF.")
                                .setContentInfo("")
                                .setTicker("Alerta!");
                //direccionar al dar click en notificacion
                Intent notIntent =
                        new Intent(getActivity(),Loading.class);

                PendingIntent contIntent =
                        PendingIntent.getActivity(
                                getActivity(), 0, notIntent, 0);
                mBuilder.setContentIntent(contIntent);
                NotificationManager mNotificationManager =(NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(NOTIF_ALERTA_ID, mBuilder.build());
                //SNACBAR CON BUTTON
                Snackbar.make(getView(), "Se guardo el PDF", Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(R.color.colorPrimaryDark))
                        .setAction("ABRIR PDF", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AbrirPDF();
                            }
                        }).show();
            }
            catch (DocumentException e) {
                Log.e(ETIQUETA_ERROR, e.getMessage());
                Snackbar.make(getView(), "Error al guardar PDF", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } catch (IOException e) {
                Log.e(ETIQUETA_ERROR, e.getMessage());
                Snackbar.make(getView(), "Error al guardar PDF", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } finally {
                // Cerramos el documento.
                documento.close();
            }
            return mensaje;
        }
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
        }
    }

    //Procedimiento para mostrar el documento PDF generado
    public void AbrirPDF() {
        try {
            //Llama a aplicacion de PDF
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+NOMBRE_DIRECTORIO+"/"+NOMBRE_DOCUMENTO;
            File targetFile = new File(path);
            Uri targetUri = Uri.fromFile(targetFile);
            Intent intent;
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(targetUri, "application/pdf");
            startActivity(intent);
        }catch (Exception e){
            Snackbar.make(getView(), "No hay aplicacion para abrir el Pdf", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    public static File crearFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        return fichero;
    }


    public static File getRuta() {
        // El fichero será almacenado en un directorio dentro del directorio
        // Descargas
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            ruta = new File(
                    Environment
                            .getExternalStorageDirectory(),
                    NOMBRE_DIRECTORIO);

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        } else {
        }
        return ruta;
    }

}
