package id.dipayudha.modul4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    private DBPenduduk MyDatabase;
    private TextView NIK;
    private EditText NewNama, NewUsia;
    private String getNewNama, getNewUsia;
    private Button Update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        MyDatabase = new DBPenduduk(getBaseContext());

        NIK = findViewById(R.id.nikUpdateTv);
        NewNama = findViewById(R.id.namaUpdateEdit);
        NewUsia = findViewById(R.id.usiaUpdateEdit);

        //Menerima Data Nama dan NIK yang telah dipilih Oleh User untuk diposes
        NewNama.setText(getIntent().getExtras().getString("SendNama"));
        NIK.setText(getIntent().getExtras().getString("SendNIK"));

        Update = findViewById(R.id.btnUpdate);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpdateData();
                startActivity(new Intent(UpdateActivity.this, ViewData.class));
                finish();
            }
        });
    }

    private void setUpdateData(){
        getNewNama = NewNama.getText().toString();
        getNewUsia = NewUsia.getText().toString();
        Intent intentPenduduk = getIntent();
        String NIK = intentPenduduk.getExtras().getString("SendNIK");

        SQLiteDatabase database = MyDatabase.getReadableDatabase();

        if (getNewNama.length() == 0 || getNewUsia.length() == 0){
            Toast.makeText(UpdateActivity.this,"Update Data Gagal",Toast.LENGTH_SHORT).show();
            Toast.makeText(UpdateActivity.this,"Data Tidak Boleh Kosong!",Toast.LENGTH_SHORT).show();
        }else{
            //Memasukan Data baru pada 2 kolom
            ContentValues values = new ContentValues();
            values.put(DBPenduduk.MyColumns.Nama, getNewNama);
            values.put(DBPenduduk.MyColumns.Usia, getNewUsia);

            //Untuk Menentukan Data/Item yang ingin diubah berdasarkan NIK
            String selection = DBPenduduk.MyColumns.NIK + " LIKE ?";
            String[] selectionArgs = {NIK};
            database.update(DBPenduduk.MyColumns.NamaTabel, values, selection, selectionArgs);
            Toast.makeText(getApplicationContext(), "Data Berhasil Diubah", Toast.LENGTH_SHORT).show();
        }

    }
}