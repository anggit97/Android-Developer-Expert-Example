package com.anggitprayogo.dicoding.myreadwritefile;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnNew;
    Button btnOpen;
    Button btnSave;
    EditText editContent;
    EditText editTitle;

    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNew = (Button) findViewById(R.id.button_new);
        btnOpen = (Button) findViewById(R.id.button_open);
        btnSave = (Button) findViewById(R.id.button_save);
        editContent = (EditText) findViewById(R.id.edit_file);
        editTitle = (EditText) findViewById(R.id.edit_title);

        btnNew.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        path = getFilesDir();
    }

    private void saveFile() {
        if (editTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        }else{
            String title = editTitle.getText().toString();
            String content = editContent.getText().toString();
            FileHelper.writeToFile(title, content, this);
            Toast.makeText(this, "Berhasil simpan file baru "+title, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadItems(String string) {
        String text = FileHelper.readFromFile(this, string);
        editTitle.setText(string);
        editContent.setText(text);
        Toast.makeText(this, "Memuat data "+string, Toast.LENGTH_SHORT).show();
    }

    private void showList() {
        final ArrayList<String> arrayList = new ArrayList<>();
        for (String file: path.list()){
            arrayList.add(file);
        }

        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang anda inginkan : ");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadItems(items[which].toString());
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void openFile() {
        showList();
    }

    private void newFile() {
        editTitle.setText("");
        editContent.setText("");

        Toast.makeText(this, R.string.clearing_file, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_new:
                newFile();
                break;
            case R.id.button_open:
                openFile();
                break;
            case R.id.button_save:
                saveFile();
                break;
        }
    }
}
