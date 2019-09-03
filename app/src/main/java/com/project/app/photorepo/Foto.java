package com.project.app.photorepo;

import com.google.firebase.database.DatabaseReference;
import com.project.app.photorepo.helper.ConfiguracaoFirebase;

public class Foto {

    private String urlImagem;
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Foto() {
    }

    public void salvar(Foto foto){

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        String uniqueKey = firebaseRef.child("fotos").push().getKey();
        DatabaseReference uniqueKeyRef = firebaseRef.child("fotos").child(uniqueKey);
        uniqueKeyRef.setValue(foto);
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }
}
