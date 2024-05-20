package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {
	/** Módulo de conexão **/
	// Parâmetro de conexão

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "1234567";

	// Metodo de conexão

	private Connection conectar() {
		Connection conn = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public void inserirContato(JavaBeans contato) {

		String insert = "insert into contatos (nome,fone,email) values (?,?,?)";
		try {
			Connection conn = conectar();

			PreparedStatement pst = conn.prepareStatement(insert);

			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());

			pst.executeUpdate();

			conn.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public ArrayList<JavaBeans> listarContatos() {
		ArrayList<JavaBeans> contatos = new ArrayList();
		String listaContatos = "select * from contatos order by idcon";

		try {
			Connection conn = conectar();
			PreparedStatement pst = conn.prepareStatement(listaContatos);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);

				contatos.add(new JavaBeans(idcon, nome, fone, email));

			}
			conn.close();
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public void selecionarContato(JavaBeans contato) {
		
		String idContato = "select * from contatos where idcon=?";
		
		try {
			Connection conn = conectar();
			PreparedStatement pst = conn.prepareStatement(idContato);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void alterarContato(JavaBeans contato) {
		String alteraCon = "update contatos set nome=?,fone=?,email=? where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(alteraCon);
			pst.setString(1,contato.getNome());
			pst.setString(2,contato.getFone());
			pst.setString(3,contato.getEmail());
			pst.setString(4,contato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
			
		}
	}
	
	public void deletarContato(JavaBeans contato) {
		String deleteContato = "delete from contatos where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(deleteContato);
			pst.setString(1, contato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
