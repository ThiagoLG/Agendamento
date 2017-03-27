package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Especialidade;
import model.Profissional;

public class EspecialidadeDAO implements IEspecialidadeDAO {

	@Override
	public List<Especialidade> carregarEspecialidades() {
		List<Especialidade> lista = new ArrayList<Especialidade>();
		String sql = "select * from especialidade";

		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Especialidade e = new Especialidade();
				e.setId_especialidade(rs.getInt("id_especialidade"));
				e.setDescricao(rs.getString("descricao"));
				lista.add(e);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public Especialidade pesquisarEspecialidade(int id) {
		String sql = "select * from especialidade where id_especialidade = ?";
		Especialidade e = new Especialidade();
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				e.setId_especialidade(rs.getInt("id_especialidade"));
				e.setDescricao(rs.getString("descricao"));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return e;
	}

	@Override
	public List<String> listarHorarios(int id) {
		List<String> lista = new ArrayList<String>();
		String hora;
		String sql = "select horario from agendaEspecialidade where id_especialidade = ?";

		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				hora = rs.getString("horario");
				lista.add(hora);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public String pesqDescricao(int id) {
		String sql = "select * from especialidade where id_especialidade = ?";
		String desc = "";
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				desc = rs.getString("descricao");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return desc;
	}

	@Override
	public boolean adicionarProfissional(Profissional p) {
		boolean adicionou = false;
		String sql = "insert into especialidade (descricao, responsavel, loja, ativa) values (?, ?, ?, true)";

		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, p.getNome());
			ps.setString(2, p.getLoja());

			ps.executeUpdate();
			adicionou = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return adicionou;
	}

	@Override
	public Profissional pesquisarProf(String nome) {
		String sql = "select * from especialidade where responsavel like ? and ativa = true";
		Profissional p = new Profissional();
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "%" + nome + "%");
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				p.setLoja(rs.getString("loja"));
				p.setNome(rs.getString("responsavel"));
				p.setId(rs.getInt("id_especialidade"));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return p;
	}

	@Override
	public boolean alterarProf(Profissional profissional, int id) {
		String sql = "update especialidade set responsavel = ?, descricao = ?, loja = ? where id_especialidade = ?";
		boolean alterou = false;
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, profissional.getNome());
			ps.setString(2, profissional.getLoja());
			ps.setInt(4, id);

			ps.executeUpdate();
			System.out.println("alterou");
			alterou = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return alterou;
	}

	@Override
	public boolean excluirProf(int id) {
		String sql = "update especialidade set ativa = false where id_especialidade = ?";
		boolean alterou = false;
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, id);

			ps.executeUpdate();
			System.out.println("'excluiu'");
			alterou = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return alterou;
	}

	@Override
	public List<String> pesquisarHorarios() {
		List<String> lista = new ArrayList<String>();
		String sql = "select horario from agendaEspecialidade where id_especialidade = 1";

		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String hora = rs.getString("horario");
				lista.add(hora);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public boolean inserirHorarios(int id, String hora) {
		boolean inseriu = false;
		String sql = "insert into agendaEspecialidade (id_especialidade, horario) values (?, ?)";

		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, hora);
			
			ps.executeUpdate();
			
			inseriu = true;
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return inseriu;
	}

	@Override
	public int pesquisarIdEspecialidade(String nome, String especialidade) {
		int id = 0;
		String sql = "select * from especialidade where responsavel like ? and descricao like ?";
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, nome);
			ps.setString(2, especialidade);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				id = rs.getInt("id_especialidade");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return id;
	}

	@Override
	public List<Especialidade> listarTodasEspecialidades() {
		List<Especialidade> lista = new ArrayList<Especialidade>();
		String sql = "select * from especialidade";
		
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			
			ResultSet rs= ps.executeQuery();
			
			while(rs.next()){
				Especialidade e = new Especialidade();
				e.setId_especialidade(rs.getInt("id_especialidade"));
				e.setDescricao(rs.getString("descricao"));
				
				lista.add(e);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lista;
	}

}
