package empresa.android.myappregistrodepaquetes2024;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    DBHelper myDb;
    EditText editEmpresa, editDescripcion, editPiso;
    TextView textViewFecha, textViewHora;
    Button btnAgregar, btnVer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DBHelper(this);

        editEmpresa = findViewById(R.id.editTextEmpresa);
        editDescripcion = findViewById(R.id.editTextDescripcion);
        editPiso = findViewById(R.id.editTextPiso);
        textViewFecha = findViewById(R.id.textViewFecha);
        textViewHora = findViewById(R.id.textViewHora);
        btnAgregar = findViewById(R.id.buttonAgregar);
        btnVer = findViewById(R.id.buttonVer);

        // Establecer fecha y hora automáticas
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        textViewFecha.setText("Fecha: " + currentDate);
        textViewHora.setText("Hora: " + currentTime);

        agregarPaquete();
        verPaquetes();
    }

    public void agregarPaquete() {
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                boolean isInserted = myDb.insertData(editEmpresa.getText().toString(),
                        editDescripcion.getText().toString(),
                        editPiso.getText().toString(),
                        currentDate,
                        currentTime);

                if (isInserted)
                    Toast.makeText(MainActivity.this, "Paquete Registrado", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Error al Registrar", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void verPaquetes() {
        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0) {
                    showMessage("Error", "No se encontraron paquetes");
                    return;
                }

                StringBuilder buffer = new StringBuilder();
                while (res.moveToNext()) {
                    buffer.append("ID: ").append(res.getString(0)).append("\n");
                    buffer.append("Empresa: ").append(res.getString(1)).append("\n");
                    buffer.append("Descripción: ").append(res.getString(2)).append("\n");
                    buffer.append("Piso: ").append(res.getString(3)).append("\n");
                    buffer.append("Fecha: ").append(res.getString(4)).append("\n");
                    buffer.append("Hora: ").append(res.getString(5)).append("\n\n");
                }

                showMessage("Paquetes Registrados", buffer.toString());
            }
        });
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
