package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Cliente;



public class ClienteDao implements IClienteDao{

	@Override
	public boolean cadastrado(String cpf) {
		String sql = "select * from cliente where cpf like ?";
		boolean cad = false;
		
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, cpf);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				cad = true;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return cad;
	}

	
	@Override
	public boolean cadastrarCliente(Cliente c) {
		String sql = "insert into cliente (cpf, nome, telefone, email) values (?,?,?,?)";
		boolean cadastrou = false;
		
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, c.getCpf());
			ps.setString(2, c.getNome());
			ps.setString(3, c.getTelefone());
			ps.setString(4, c.getEmail());
			
			ps.executeUpdate();
			
			cadastrou = true;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cadastrou;
	}


	@Override
	public Cliente getCliente(String cpf) {
		String sql = "select * from cliente where cpf like ?";
		Cliente c = new Cliente();
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, cpf);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				c.setCpf(cpf);
				c.setEmail(rs.getString("email"));
				c.setNome(rs.getString("nome"));
				c.setTelefone(rs.getString("telefone"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
			
		return c;
	}

}
