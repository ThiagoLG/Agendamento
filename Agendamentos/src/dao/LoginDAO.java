package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class LoginDAO implements ILoginDAO {

	public boolean autenticado = false;

	@Override
	public boolean autenticarLogin(String usuario, String senha) {
		String sql = "SELECT * FROM login WHERE usuario like ? and senha like ?";
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, usuario);
			ps.setString(2, senha);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				autenticado = true;
			}

			return autenticado;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return autenticado;
	}

/*	@Override
	public String buscarUsuario(String user, String senha) {
		String administrador = "";
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			String sql = "SELECT * FROM usuario WHERE login_usuario like ? and senha_usuario like ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, user);
			stmt.setString(2, senha);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				administrador = rs.getString("admnistrador");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return administrador;
	}*/

}
