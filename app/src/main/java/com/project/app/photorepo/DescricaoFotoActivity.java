package com.project.app.photorepo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.project.app.photorepo.helper.ConfiguracaoFirebase;
import com.squareup.picasso.Picasso;

public class DescricaoFotoActivity extends AppCompatActivity {

    ImageView imagem;
    TextView textDescricao;
    DatabaseReference firebaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descricao_foto);

        firebaseRef = ConfiguracaoFirebase.getFirebase();

        imagem = findViewById(R.id.imagePhotoDescricao);
        textDescricao = findViewById(R.id.textDescricao);

        recuperarFotoDescricao();
    }


    private void recuperarFotoDescricao(){
        DatabaseReference fdRef = firebaseRef
                .child("fotos");

        fdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for(DataSnapshot ds: dataSnapshot.getChildren()){
                    String descricao  = String.valueOf(ds.child("descricao").getValue());
                    String urlImagem = String.valueOf(ds.child("urlImagem").getValue());
                    textDescricao.setText(descricao);
                    if(urlImagem == null){
                        imagem.setImageResource(R.drawable.perfil);
                    }else{
                        Picasso.get().load(urlImagem).into(imagem);
                    }

               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
