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

    public void salvar(){

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference empresaRef = firebaseRef.child("fotos");

        empresaRef.setValue(this);

    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }
}
