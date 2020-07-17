package cl.dyi.basedatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edtRut, edtNombre, edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtRut = (EditText) findViewById(R.id.edtRut);
        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
    }

    public void registrar(View view){
        AdminSQLiteHelper adminSQLiteHelper = new AdminSQLiteHelper(this, "registros", null, 1);
        SQLiteDatabase BasedeDatos = adminSQLiteHelper.getWritableDatabase();

        String rut = edtRut.getText().toString();
        String nombre = edtNombre.getText().toString();
        String email = edtEmail.getText().toString();

        if( !rut.isEmpty() && !nombre.isEmpty() && !email.isEmpty() ){
            ContentValues contenedor = new ContentValues();
            contenedor.put("rut", rut);
            contenedor.put("nombre", nombre);
            contenedor.put("email", email);

            BasedeDatos.insert("personas", null, contenedor);
            BasedeDatos.close();
            edtRut.setText("");
            edtNombre.setText("");
            edtEmail.setText("");

            Toast.makeText(this,"Registro Exitoso", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Faltas datos que llenar", Toast.LENGTH_LONG).show();
        }
    }

    public void buscar(View view){
        AdminSQLiteHelper adminSQLiteHelper = new AdminSQLiteHelper(this,"registros", null, 1);
        SQLiteDatabase sqLiteDatabase = adminSQLiteHelper.getWritableDatabase();

        String rut = edtRut.getText().toString();
        if( !rut.isEmpty() ){
            String query = "SELECT nombre, email FROM personas WHERE rut = '"+rut+"';";

           Cursor fila = sqLiteDatabase.rawQuery(query, null);

            if(fila.moveToFirst()){
                edtNombre.setText(fila.getString(0));
                edtEmail.setText(fila.getString(1));
                Toast.makeText(this, "registro encontrado",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,"No existe persona",Toast.LENGTH_LONG).show();
                edtRut.setText("");
            }
            fila.close();
            sqLiteDatabase.close();
        } else {
            Toast.makeText(this, "Falta rut para realizar la busqueda", Toast.LENGTH_LONG).show();
        }
    }
}
