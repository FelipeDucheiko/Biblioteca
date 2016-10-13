/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PERS;

import VO.AssociadoVO;
import VO.EnderecoVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author felip
 */
public class EnderecoPERS {

    private final ConexaoPERS cx = new ConexaoPERS();
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private String query, msg;
    private PreparedStatement ps;

    public EnderecoVO retornarEndereco(String codigoAssociado) {
        con = cx.conectar();
        EnderecoVO enderecoVO = new EnderecoVO();

        if (con == null) {
            System.out.println("Erro na conexão");
        }
        try {
            st = con.createStatement();
            query = "Select * from endereco where codigo_endereco = (Select codigo_associado_endereco from associado where codigo_associado = '" + codigoAssociado + "')";

            rs = st.executeQuery(query);

            if (rs.next()) {
                enderecoVO.setCodigo(rs.getString("codigo_endereco"));
                enderecoVO.setnCasa(rs.getString("numero_casa"));
                enderecoVO.setnCasa(rs.getString("numero_casa"));
                enderecoVO.setRua(rs.getString("logradouro"));
                enderecoVO.setBairro(rs.getString("bairro"));
                enderecoVO.setnCasa(rs.getString("numero_casa"));
                enderecoVO.setCidade(rs.getString("cidade"));
                enderecoVO.setEstado(rs.getString("estado"));
            }

        } catch (SQLException e) {
            System.out.println("Erro no banco");
        } finally {
            cx.desconectar();
        }
        return enderecoVO;
    }

    public int cadastrarEndereco(EnderecoVO ederecoVO) {
        con = cx.conectar();
        int id = -1;
        try {

            query = "INSERT INTO endereco (numero_casa, logradouro, bairro, cidade, estado) VALUES(" + ederecoVO.getnCasa() + ",'" + ederecoVO.getRua() + "', '" + ederecoVO.getBairro() + "', '" + ederecoVO.getCidade() + "', '" + ederecoVO.getEstado() + "');";
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException e) {
            msg = "Erro no Banco de dados: " + e.getMessage();
        } finally {
            cx.desconectar();
        }
        return id;
    }

    public void atualizar(AssociadoVO associadoVO) {
        con = cx.conectar();
        EnderecoVO enderecoVO = associadoVO.getEndereco();
        try {

            query = "UPDATE endereco SET numero_casa = " + enderecoVO.getnCasa() + ", logradouro = '" + enderecoVO.getRua() + "', bairro = '" + enderecoVO.getBairro() + "', cidade = '" + enderecoVO.getCidade() + "', estado = '" + enderecoVO.getEstado() + "' WHERE codigo_endereco = (SELECT codigo_associado_endereco FROM associado WHERE codigo_associado = " + associadoVO.getCodigo()+ ");";
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();
            

        } catch (SQLException e) {
            msg = "Erro no Banco de dados: " + e.getMessage();
        } finally {
            cx.desconectar();
        }
    }

}
