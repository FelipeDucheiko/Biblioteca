/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VO;

import java.util.ArrayList;

/**
 *
 * @author felip
 */
public class LivroVO {
    private String codigo;
    
    private String titulo;
    
    private String ISBN;

    private String editora;

    private String localEdicao;

    private ArrayList<String> autor;

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the ISBN
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * @param ISBN the ISBN to set
     */
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * @return the editora
     */
    public String getEditora() {
        return editora;
    }

    /**
     * @param editora the editora to set
     */
    public void setEditora(String editora) {
        this.editora = editora;
    }

    /**
     * @return the localEdicao
     */
    public String getLocalEdicao() {
        return localEdicao;
    }

    /**
     * @param localEdicao the localEdicao to set
     */
    public void setLocalEdicao(String localEdicao) {
        this.localEdicao = localEdicao;
    }

    /**
     * @return the autor
     */
    public ArrayList<String> getAutor() {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(ArrayList<String> autor) {
        this.autor = autor;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
