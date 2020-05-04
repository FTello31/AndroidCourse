package com.example.fernando.example1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private EditText editTextWeb;
    private EditText editTextContact;
    private EditText editTextEmail1;
    private EditText editTextEmail2;
    private EditText editTextPhone2;
    private ImageButton imageButtonPhone;
    private ImageButton imageButtonWeb;
    private ImageButton imageButtonCamera;
    private ImageButton imageButtonContact;
    private ImageButton imageButtonEmail1;
    private ImageButton imageButtonEmail2;
    private ImageButton imageButtonPhone2;

    private final int PHONE_CALL_CODE = 100;
    private final int PICTURE_FROM_CAMERA = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //Activar flecha ir atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextWeb = (EditText) findViewById(R.id.editTextWeb);
        imageButtonPhone = (ImageButton) findViewById(R.id.imageButtonPhone);
        imageButtonWeb = (ImageButton) findViewById(R.id.imageButtonWeb);
        imageButtonCamera = (ImageButton) findViewById(R.id.imageButtonCamera);

        editTextContact = (EditText) findViewById(R.id.editTextContact);
        editTextEmail1 = (EditText) findViewById(R.id.editTextEmail1);
        editTextEmail2 = (EditText) findViewById(R.id.editTextEmail2);
        editTextPhone2 = (EditText) findViewById(R.id.editTextPhone2);

        imageButtonContact = (ImageButton) findViewById(R.id.imageButtonContact);
        imageButtonEmail1 = (ImageButton) findViewById(R.id.imageButtonEmail1);
        imageButtonEmail2 = (ImageButton) findViewById(R.id.imageButtonEmail2);
        imageButtonPhone2 = (ImageButton) findViewById(R.id.imageButtonPhone2);

        //boton para llamada
        imageButtonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = editTextPhone.getText().toString();
                if ((phoneNumber != null) && !phoneNumber.isEmpty()) {
                    //comprobar version actual de android que estamos corriendo

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//versiones nuevas
                        // Comprobar si ha aceptado, no ha aceptado o nunca se le ha preguntado

                        if (CheckPermission(Manifest.permission.CALL_PHONE)) {
                            //ha aceptado
                            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                            if (ActivityCompat.checkSelfPermission(ThirdActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            startActivity(i);
                        } else {
                            //Ha denegado o es la primera vez que se le pregunta
                            if (!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) { //comprobamos si ha aceptado o no --> le pasamos el permiso que estamos trabajando
                                //No se le ha preguntado aún
                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);//preguntar los permisios en tiempo de ejecucion, se le pasa un array de los permisos y un codigo para clasificarlo...
                            } else {
                                //Ha denegado
                                Toast.makeText(ThirdActivity.this, "Please,enable the request permission", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS); // ir a la ventana de settings
                                i.addCategory(Intent.CATEGORY_DEFAULT);
                                i.setData(Uri.parse("package:" + getPackageName())); //para que localice nuestra aplicacion
                                //flags son para ,
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // abrir como una nueeva ventana
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY); //no añade el activity al history para que al poner atras no regrese en un bucle https://stackoverflow.com/questions/12358485/android-open-activity-without-save-into-the-stack
                                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS); // para excluirlo de los activities recientes abiertos que el Sistema maneja y guarda
                                startActivity(i);
                            }
                        }

                    } else {
                        OlderVersions(phoneNumber);
                    }
                } else {
                    Toast.makeText(ThirdActivity.this, "Please insert a phone number", Toast.LENGTH_LONG).show();
                }
            }

            //para las antiguas versiones de android (de 6 para abajo)
            private void OlderVersions(String phoneNumber) {
                Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                //action y depsues un URI = es un tipo de referencia que se le pasa, que va a saber como comportarse
                if (CheckPermission(Manifest.permission.CALL_PHONE)) {
                    startActivity(intentCall);
                } else {
                    Toast.makeText(ThirdActivity.this, "You decline the access", Toast.LENGTH_LONG).show();
                }
            }
        });


        //boton para direccion WEB
        //no necesita permisos
        imageButtonWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editTextWeb.getText().toString();

                if (url != null && !url.isEmpty()) {
                    //Intent intentWeb = new Intent(Intent.ACTION_VIEW,Uri.parse("http://"+url));
                    Intent intentWeb = new Intent();
                    intentWeb.setAction(Intent.ACTION_VIEW);
                    intentWeb.setData(Uri.parse("http://" + url));

                    startActivity(intentWeb);
                }
            }
        });

        //intents para contactos
        imageButtonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentContacts = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));
                startActivity(intentContacts);
            }
        });

        //intent para Email Rapido
        imageButtonEmail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail1.getText().toString();
                Intent intentMailto = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                startActivity(intentMailto);
            }
        });

        //intent para Email Completo
        imageButtonEmail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = editTextEmail2.getText().toString();
                Intent intentMail = new Intent(Intent.ACTION_SEND, Uri.parse(email)); // persona que lo manda
                //intentMail.setClassName("com.google.android.gm","com.google.android.gm.ComposeActivityGmail"); // por defecto a gmail
                intentMail.setType("plain/text");
                // intentMail.setType("message/rfc822");
                intentMail.putExtra(Intent.EXTRA_SUBJECT, "Mail's tittle"); //titulo del email
                intentMail.putExtra(Intent.EXTRA_TEXT, "Hi there, i love MyForm app, but...");//texto
                intentMail.putExtra(Intent.EXTRA_EMAIL, new String[]{"antonio@aaa", "Johanna@aaaaa"}); //destinatarios: otros emails, se le pasa un array de strings ,

                startActivity(Intent.createChooser(intentMail, "Elige cliente de correo"));// devuelve otro intent //esto fuerza a que se le pregnte al usuario

            }
        });

        //Telefono 2 RECOMENDADO en vez del primero
        imageButtonPhone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = editTextPhone2.getText().toString();
                Intent intentPhone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                startActivity(intentPhone);
            }
        });

        //Abrir camara
        imageButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamara = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intentCamara);

                //extras PARA MANIPULAR LA IMAGEN, para hacer esto tenemos que sobreescribir el metodo onActivityResult por ser un resultado asincrono
                //startActivityForResult(intentCamara,PICTURE_FROM_CAMERA); // es un activity que esta esperando una resultado, para manejar acciones asincronas
            }
        });
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICTURE_FROM_CAMERA:

                if (resultCode == Activity.RESULT_OK) {
                    String result = data.toUri(0);
                    Toast.makeText(this, "Result: ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "There was an error, please try again.", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
*/
        // sobreescribimos metodo para manejar las respuestas asincronas para los permisos ( es para el requestPermissions)
        @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            // llega, codigo de peticion(codigo que le mandamos en PHONE_CALL_CODE) y llega a este metodo , array de permision y el resultado

            switch (requestCode) {
                case PHONE_CALL_CODE:
                    //si estamos en el caso del telefono
                    String permission = permissions[0];
                    int result = grantResults[0];

                    if (permission.equals(Manifest.permission.CALL_PHONE)) {
                        //comprobar si ha sido aceptado o denegado la peticion de permiso
                        if (result == PackageManager.PERMISSION_GRANTED) {
                            //concedió su permiso
                            String phonenumber = editTextPhone.getText().toString();
                            Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phonenumber));
                            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // comprueba por ultima vez el permiso de sistema que el call phone ha sido aceptado
                                // o si ha sido denegado devuelve un return pero sino sigue con el flujo
                                return;
                            }
                            startActivity(intentCall);
                        } else {
                            // No concedio su permiso
                            Toast.makeText(ThirdActivity.this, "You declined the acess", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                default:
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                    break;
            }
        }

        //comprueba que el usuario realmente haya aceptado el permiso

    private boolean CheckPermission(String permission) {
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
        //es igual que return result==0
    }


}
