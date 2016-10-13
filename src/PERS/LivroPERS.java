/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PERS;

import VO.LivroVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author felip
 */
public class LivroPERS {

    private final ConexaoPERS cx = new ConexaoPERS();
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private String query, msg;
    private PreparedStatement ps;
    
    public List retornarLivros() {
        con = cx.conectar();
        List livros = new ArrayList();        
        
        if(con == null){
            System.out.println("Erro na conexão");
        }
        
        try { 
            st = con.createStatement();
            query = "Select * from livro";
    		
            rs = st.executeQuery(query);
            
            while(rs.next()) {
               LivroVO livroVO = new LivroVO();
               livroVO.setCodigo(rs.getString("codigo_livro"));
               livroVO.setTitulo(rs.getString("titulo"));
               livroVO.setISBN(rs.getString("ISBN"));
               livroVO.setEditora(rs.getString("nome_editora"));
               livroVO.setLocalEdicao(rs.getString("local_edicao"));
               
               livros.add(livroVO);
            }
			
        }catch(SQLException e) { 
            System.out.println("Erro no banco");
        }finally { 
            cx.desconectar(); 
        }
        return livros;
    }
    
    public List retornarAutores(String codigoLivro) {
        con = cx.conectar();
        List autores = new ArrayList();        
        
        if(con == null){
            System.out.println("Erro na conexão");
        }
        
        try { 
            st = con.createStatement();
            query = "SELECT nome_autor FROM autor, autoria WHERE (autoria.codigo_autoria_livro = " + codigoLivro + ") AND (autoria.codigo_autoria_autor = autor.codigo_autor)";
    		
            rs = st.executeQuery(query);
            
            while(rs.next()) {           
               autores.add(rs.getString("nome_autor"));
            }
			
        }catch(SQLException e) { 
            System.out.println("Erro no banco");
        }finally { 
            cx.desconectar(); 
        }
        return autores; 
    }

    public String cadastrarLivro(LivroVO livroVO) {
        con = cx.conectar();
        ArrayList autores = livroVO.getAutor();
        ArrayList<Integer> idAutores = new ArrayList<Integer>();

        try {
            int i = 0;
            for (Object autor : autores) {
                query = "INSERT INTO autor (nome_autor) VALUES('" + autor + "');";
                ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idAutores.add(rs.getInt(1));
                }
                i++;
            }

            query = "INSERT INTO livro (titulo, ISBN, nome_editora, local_edicao) VALUES ('" + livroVO.getTitulo() + "', '" + livroVO.getISBN() + "', '" + livroVO.getEditora() + "', '" + livroVO.getLocalEdicao() + "');";
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            int idLivro = -1;
            if (rs.next()) {
                idLivro = rs.getInt(1);
            }
            
            int id = -1;
            for (i = 0; i < idAutores.size(); i++) {
                query = "INSERT INTO autoria (codigo_autoria_livro, codigo_autoria_autor) VALUES (" + idLivro + ", " + idAutores.get(i) + ");";
                ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
            if (id > 0) {
                msg = "Livro cadastrado com sucesso.";
            } else {
                msg = "Erro no cadastro do livro.";
            }

        } catch (SQLException e) {
            msg = "Erro no Banco de dados: " + e.getMessage();
        } finally {
            cx.desconectar();
        }
        return msg;
    }

    public String excluirLivro(String codigoLivro) {
        con = cx.conectar();
        try {
            st = con.createStatement();
            query = "DELETE FROM autoria WHERE codigo_autoria_livro = " + codigoLivro;
            int rs = st.executeUpdate(query);
            
            query = "DELETE FROM livro WHERE codigo_livro = " + codigoLivro;
            int rs2 = st.executeUpdate(query);
            
            if (rs > 0 && rs2 > 0) {
                msg = "Livro exluído com sucesso.";
            } else {
                msg = "Erro ao exluir o associado.";
            }
        } catch (SQLException e) {
            msg = "Erro no Banco de dados: " + e.getMessage();
        } finally {
            cx.desconectar();
        }
        return msg;
    }

    public String alterarLivro(LivroVO livroVO) {
        con = cx.conectar();
        try {
            st = con.createStatement();
            query = "UPDATE livro SET titulo = '" + livroVO.getTitulo()+ "', ISBN = '" + livroVO.getISBN()+ "', nome_editora = '" + livroVO.getEditora()+ "', local_edicao = '" + livroVO.getLocalEdicao()+ "' WHERE codigo_livro = '" + livroVO.getCodigo() + "' ";
            int result = st.executeUpdate(query);
            
            
            query = "DELETE FROM autoria WHERE codigo_autoria_livro = " + livroVO.getCodigo();
            int result2 = st.executeUpdate(query);
            
            
            ArrayList autores = livroVO.getAutor();
            ArrayList<Integer> idAutores = new ArrayList<Integer>();
            
            int i = 0;
            for (Object autor : autores) {
                query = "INSERT INTO autor (nome_autor) VALUES('" + autor + "');";
                ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idAutores.add(rs.getInt(1));
                }
                i++;
            }
            
            int id = -1;
            int tam = idAutores.size();
            for (i = 0; i < tam; i++) {
                query = "INSERT INTO autoria (codigo_autoria_livro, codigo_autoria_autor) VALUES (" + livroVO.getCodigo() + ", " + idAutores.get(i) + ");";
                ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
            
            if (result > 0 && result2 > 0) {
                msg = "Livro atualizado com sucesso.";
            } else {
                msg = "Erro ao atualizar o livro.";
            }
        } catch (SQLException e) {
            msg = "Erro no Banco de dados: " + e.getMessage();
        } finally {
            cx.desconectar();
        }
        return msg;
    }
}
