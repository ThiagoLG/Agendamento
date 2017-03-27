package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Profissional;

public class ProfissionalDao implements IProfissionalDao {

	@Override
	public List<Profissional> listarProfissionais(int id) {
		// String sql = "select * from profEsp where id_especialidade = ?";
		String sql = "select profEsp.id_profissional, profissional.nome, profissional.loja from profEsp	inner join profissional	on profEsp.id_profissional = profissional.id_profissional where profEsp.id_especialidade = ? and profissional.ativo = true order by profissional.loja, profissional.nome";
		List<Profissional> profissionais = new ArrayList<Profissional>();

		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Profissional p = new Profissional();
				p.setId(rs.getInt("id_profissional"));
				p.setLoja(rs.getString("loja"));
				p.setNome(p.getLoja() + " - " + rs.getString("nome"));
				profissionais.add(p);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return profissionais;
	}

	@Override
	public String pesqNome(int id) {
		String sql = "select * from profissional where id_profissional = ? order by loja asc, nome asc";
		String nome = "";

		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				nome = rs.getString("loja") + " - ";
				nome += rs.getString("nome");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return nome;
	}

	@Override
	public List<String> listarHorarios(int id) {
		List<String> lista = new ArrayList<String>();
		String hora;
		String sql = "select horario from agendaProfissional where id_profissional = ?";

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
	public List<Profissional> listarTodos() {
		String sql = "select * from profissional where ativo = true order by loja asc, nome asc";
		List<Profissional> profissionais = new ArrayList<Profissional>();
		String nome;

		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Profissional p = new Profissional();
				p.setId(rs.getInt("id_profissional"));
				nome = (rs.getString("loja"));
				p.setNome(nome + " - " + rs.getString("nome"));

				profissionais.add(p);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return profissionais;
	}

	@Override
	public int idProfissional(int id_agendamento) {
		int id = 0;
		String sql = "select id_profissional from agendamento where id_agendamento = ?";

		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, id_agendamento);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				id = rs.getInt("id_profissional");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return id;
	}

	@Override
	public boolean adicionarProfissional(Profissional p) {
		String sql = "insert into profissional (nome, loja, ativo) values (?, ?, true)";
		boolean adicionou = false;

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
	public int idProfissionalCad(Profissional p) {
		String sql = "select id_profissional from profissional where nome like ? and loja like ?";
		int id = 0;

		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, p.getNome());
			ps.setString(2, p.getLoja());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				id = rs.getInt("id_profissional");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return id;
	}

	@Override
	public void insertEspecialidadeProf(int id_profissional, int id_especialidade) {
		String sql = "insert into profEsp (id_especialidade, id_profissional) values (?,?)";

		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, id_especialidade);
			ps.setInt(2, id_profissional);

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void inserirHorarios(int id_profissional, String hora) {
		String sql = "insert into agendaProfissional (id_profissional, horario) values (?, ?)";

		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id_profissional);
			ps.setString(2, hora);

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Profissional pesquisarProfissional(String nome, String loja) {
		String sql = "select * from profissional where nome like ? and loja like ? and ativo = true";
		Profissional p = new Profissional();

		try {

			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, "%" + nome + "%");
			ps.setString(2, loja);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				p.setId(rs.getInt("id_profissional"));
				p.setNome(rs.getString("nome"));
				p.setLoja(rs.getString("loja"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return p;
	}

	@Override
	public List<String> pesquisarEspecialidadesProf(int id_profissional) {
		String sql = "select * from profEsp where id_profissional = ?";
		List<String> espec = new ArrayList<String>();
		
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, id_profissional);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				espec.add(Integer.toString(rs.getInt("id_especialidade")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return espec;
	}

	@Override
	public boolean alterarProfissional(Profissional p) {
		String sql = "update profissional set nome = ?, loja = ? where id_profissional = ?";
		String sql2 = "delete from profEsp where id_profissional = ?";
		boolean att = false;
		
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			PreparedStatement ps2 = con.prepareStatement(sql2);
			
			ps.setString(1, p.getNome());
			ps.setString(2, p.getLoja());
			ps.setInt(3, p.getId());
			
			ps2.setInt(1, p.getId());
			
			ps.executeUpdate();
			ps2.executeUpdate();
			att = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return att;
	}

	@Override
	public boolean excluirProfissional(Profissional p) {
		String sql = "update profissional set ativo = false where id_profissional = ?";
		boolean excluiu = false;
		
		try {
			
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, p.getId());
			
			ps.executeUpdate();
			
			excluiu = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return excluiu;
	}

	@Override
	public boolean alterarEspecProf(int id_profissional, int id_especialidade) {
		String sql = "insert into profEsp (id_profissional, id_especialidade) values (?,?)";
		
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1,id_profissional);
			ps.setInt(2, id_especialidade);
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
