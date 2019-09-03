package com.project.app.photorepo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.app.photorepo.helper.ConfiguracaoFirebase;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {

    private static final int CAMERA_PIC_REQUEST = 200;
    private Button cameraButton, savePhoto;
    private ImageView imageCamera;
    private String urlImagemSelecionada;
    private Bitmap image;
    private StorageReference storageReference;
    private EditText editDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        storageReference = ConfiguracaoFirebase.getFirebaseStorage();

        initViews();

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });

        savePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    if(image != null)
                    {imageCamera.setImageBitmap(image);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        image.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                        final byte[] dadosImagem = baos.toByteArray();

                        DatabaseReference ref = ConfiguracaoFirebase.getFirebase();
                        String key = ref.push().getKey();

                        final StorageReference imagemRef = storageReference
                                .child("imagens")
                                .child("fotos" )
                                .child( key + "jpeg");

                        UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CameraActivity.this,
                                        "Erro ao fazer upload da imagem",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                //urlImagemSelecionada = taskSnapshot.getStorage().getDownloadUrl().toString();
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while(!uriTask.isSuccessful());
                                Uri url = uriTask.getResult();
                                urlImagemSelecionada = url.toString();
                                Toast.makeText(CameraActivity.this,
                                        "Sucesso ao fazer upload da imagem",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        });//sets imageview as the bitmap
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                String descriccao = editDescricao.getText().toString();
                Foto foto = new Foto();
                foto.setUrlImagem(urlImagemSelecionada);
                foto.setDescricao(descriccao);
                foto.salvar(foto);
            }
        });

    }

    private void initViews() {

        cameraButton = findViewById(R.id.cameraButton);
        savePhoto = findViewById(R.id.savePhoto);
        imageCamera = findViewById(R.id.imageCamera);
        editDescricao = findViewById(R.id.editDescricao);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            image = (Bitmap) data.getExtras().get("data");
            ImageView imageview = (ImageView) findViewById(R.id.imageCamera);
            imageview.setImageBitmap(image);

        }

    }

}
