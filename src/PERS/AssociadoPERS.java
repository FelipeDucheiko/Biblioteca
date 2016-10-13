/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PERS;

import VO.AssociadoVO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author felip
 */
public class AssociadoPERS {
    private final ConexaoPERS cx = new ConexaoPERS();
    private Connection con; 
    private Statement st; 
    private ResultSet rs;
    private String query, msg;
    
    public List retornarAssociados(){
        con = cx.conectar();
        List associados = new ArrayList();        
        
        if(con == null){
            System.out.println("Erro na conexão");
        }
        try { 
            st = con.createStatement();
            query ="Select * from associado";
    		
            rs = st.executeQuery(query);
            
            while(rs.next()) {
               AssociadoVO associado = new AssociadoVO();
               associado.setCodigo(rs.getString("codigo_associado"));
               associado.setNome(rs.getString("nome_associado"));
               associado.setCPF(rs.getString("cpf"));
               associado.setRG(rs.getString("rg"));
               associado.setTelefone(rs.getString("telefone"));
               associado.setEmail(rs.getString("email"));
               
               associados.add(associado);
            }
			
        }catch(SQLException e) { 
            System.out.println("Erro no banco");
        }finally { 
            cx.desconectar(); 
        }
        return associados;
    }

    public String cadastrarAssociado(AssociadoVO associadoVO, int codigoEndereco) {
        con = cx.conectar();
        try {
            st = con.createStatement();
            query = "INSERT INTO associado (nome_associado, CPF, RG, telefone, email, codigo_associado_endereco) VALUES ('" + associadoVO.getNome()+ "', '" + associadoVO.getCPF()+ "', '" + associadoVO.getRG()+ "', '" + associadoVO.getTelefone()+ "', '" + associadoVO.getEmail()+ "', '" + codigoEndereco + "')";
            int rs = st.executeUpdate(query);
            if (rs > 0) {
                msg = "Associado cadastrado com sucesso.";
            } else {
                msg = "Erro no cadastro do associado.";
            }
        } catch (SQLException e) {
            msg = "Erro no Banco de dados: " + e.getMessage();
        } finally {
            cx.desconectar();
        }
        return msg;
    }

    public String excluirAssociado(String codigoAssociado) {
        con = cx.conectar();
        try {
            st = con.createStatement();
            query = "DELETE FROM associado WHERE codigo_associado = " + codigoAssociado;
            int rs = st.executeUpdate(query);
            
            if (rs > 0) {
                msg = "Associado exluído com sucesso.";
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

    public String atualizarAssociado(AssociadoVO associadoVO) {
        con = cx.conectar();
        try {
            st = con.createStatement();
            query = "UPDATE associado SET nome_associado = '" + associadoVO.getNome()+ "', CPF = '" + associadoVO.getCPF()+ "', RG = '" + associadoVO.getRG()+ "', telefone = '" + associadoVO.getTelefone()+ "', email = '" + associadoVO.getEmail()+ "' WHERE codigo_associado = '" + associadoVO.getCodigo() + "' ";
            int rs = st.executeUpdate(query);
            if (rs > 0) {
                msg = "Associado atualizado com sucesso.";
            } else {
                msg = "Erro ao atualizar o associado.";
            }
        } catch (SQLException e) {
            msg = "Erro no Banco de dados: " + e.getMessage();
        } finally {
            cx.desconectar();
        }
        return msg;
    }
}
