package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.AgPesquisa;
import model.Agendamento;

public class AgendamentoDAO implements IAgendamentoDAO {

	@Override
	public boolean adicionarAgendamento(Agendamento a) {
		String sql = "insert into agendamento (id_especialidade, id_profissional,  cpf, dataCons, horario) values (?, ?, ?, ?, ?)";
		boolean sts = false;
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, a.getId_especialidade());
			ps.setInt(2, a.getId_profissional());
			ps.setString(3, a.getCpf());
			java.sql.Date sd = new java.sql.Date(a.getData().getTime());
			ps.setDate(4, sd);
			ps.setString(5, a.getHorario());

			ps.executeUpdate();
			sts = true;
			System.out.println("inseriu");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sts;
	}

	@Override
	public List<String> horariosOcupados(Agendamento a) {
		List<String> horarios = new ArrayList<String>();
		String sql = "select horario from agendamento where id_profissional = ? and dataCons = ? order by horario asc";
		String hora;
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, a.getId_profissional());
			java.sql.Date sd = new java.sql.Date(a.getData().getTime());
			System.out.println(sd);
			System.out.println(a.getId_profissional());
			ps.setDate(2, sd);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				System.out.println("achou alguma coisa");
				hora = rs.getString("horario");
				horarios.add(hora);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return horarios;

	}

	@Override
	public List<AgPesquisa> pesquisarPorNome(String nome, int id_profissional) {
		List<AgPesquisa> lista = new ArrayList<AgPesquisa>();
		String sql = "select agendamento.id_agendamento, agendamento.cpf, agendamento.dataCons, agendamento.horario, profissional.nome as nomeProf, profissional.loja, especialidade.descricao, cliente.telefone, cliente.nome as nomeCli from profissional inner join agendamento on profissional.id_profissional = agendamento.id_profissional inner join especialidade on agendamento.id_especialidade = especialidade.id_especialidade inner join cliente on agendamento.cpf = cliente.cpf where cliente.nome like ? and agendamento.id_profissional = ? order by dataCons desc, horario desc";
		// String sql2 = "select * from agendamento where nome like ? order by
		// dataCons DESC, horario asc";
		// boolean avisado;
		System.out.println("id do profissional: " + id_profissional);
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps;

			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + nome + "%");
			ps.setInt(2, id_profissional);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				if (rs.getString("nomeCli").equals("ind")) {
					rs.next();
				} else {
					AgPesquisa a = new AgPesquisa();

					a.setId_agendamento(rs.getInt("id_agendamento"));
					a.setTelefone(rs.getString("telefone"));
					a.setCpf(rs.getString("cpf"));
					a.setDataCons(rs.getDate("dataCons"));
					a.setHorario(rs.getString("horario"));
					a.setLoja(rs.getString("loja"));
					a.setNome(rs.getString("nomeCli"));
					a.setProfissional(rs.getString("nomeProf"));
					a.setServico(rs.getString("descricao"));

					lista.add(a);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public List<AgPesquisa> pesquisarPorData(Date data, int id_profissional) {
		List<AgPesquisa> lista = new ArrayList<AgPesquisa>();
		String sql = "select agendamento.id_agendamento, agendamento.cpf, agendamento.dataCons, agendamento.horario, profissional.nome as nomeProf, profissional.loja, especialidade.descricao, cliente.telefone, cliente.nome as nomeCli from profissional inner join agendamento on profissional.id_profissional = agendamento.id_profissional inner join especialidade on agendamento.id_especialidade = especialidade.id_especialidade inner join cliente on agendamento.cpf = cliente.cpf where agendamento.dataCons like ? and agendamento.id_profissional = ? order by dataCons desc, horario desc";

		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps;

			ps = con.prepareStatement(sql);
			java.sql.Date sd = new java.sql.Date(data.getTime());
			ps.setDate(1, sd);
			ps.setInt(2, id_profissional);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				AgPesquisa a = new AgPesquisa();

				a.setId_agendamento(rs.getInt("id_agendamento"));
				a.setTelefone(rs.getString("telefone"));
				a.setId_agendamento(rs.getInt("id_agendamento"));
				a.setTelefone(rs.getString("telefone"));
				a.setCpf(rs.getString("cpf"));
				a.setDataCons(rs.getDate("dataCons"));
				a.setHorario(rs.getString("horario"));
				a.setLoja(rs.getString("loja"));
				a.setNome(rs.getString("nomeCli"));
				a.setProfissional(a.getLoja() + " - " + rs.getString("nomeProf"));
				a.setServico(rs.getString("descricao"));

				lista.add(a);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public boolean atualizar(Agendamento a) {
		String sql = "update agendamento set dataCons = ?, horario = ? where id_agendamento = ?";
		boolean alterou = false;
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);

			java.sql.Date sd = new java.sql.Date(a.getData().getTime());
			ps.setDate(1, sd);
			ps.setString(2, a.getHorario());
			ps.setInt(3, a.getId_agendamento());

			// System.out.println(a.getId_agendamento());
			ps.executeUpdate();
			System.out.println("alterou");
			alterou = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return alterou;
	}

	@Override
	public boolean confirmar(Agendamento a) {
		String sql = "update agendamento set statusCons = true, avisado = true where id_agendamento = ?";
		boolean alterou = false;
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, a.getId_agendamento());

			System.out.println(a.getId_agendamento());
			ps.executeUpdate();
			System.out.println("alterou");
			alterou = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return alterou;
	}

	@Override
	public boolean remover(int id) {
		String sql = "delete from agendamento where id_agendamento = ?";
		boolean removeu = false;
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);

			ps.executeUpdate();

			removeu = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return removeu;
	}

	@Override
	public boolean verificaAgenda(Agendamento a) {
		String sql = "select * from agendamento where dataCons like ? and id_profissional = ? and horario like ?";
		boolean sts = true;
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			java.sql.Date sd = new java.sql.Date(a.getData().getTime());
			ps.setDate(1, sd);
			ps.setInt(2, a.getId_profissional());
			ps.setString(3, a.getHorario());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				System.out.println("Agendamento do obj: " + a.getId_agendamento() + "Agendamenot do result: "
						+ rs.getInt("id_agendamento"));
				if (a.getId_agendamento() != rs.getInt("id_agendamento")) {
					sts = false;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sts;
	}

	@Override
	public boolean verificarDia(int id, Date data) {
		boolean sts = true;
		String sql = "select * from agendamento where id_profissional = ? and dataCons like ?";

		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, id);
			java.sql.Date sd = new java.sql.Date(data.getTime());
			ps.setDate(2, sd);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				sts = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sts;
	}

	@Override
	public boolean avisado(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int protocoloAgendamento(Agendamento a) {
		int protocolo = 0;
		String sql = "select id_agendamento from agendamento where cpf like ? and dataCons like ? and horario like ?";

		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, a.getCpf());
			java.sql.Date sd = new java.sql.Date(a.getData().getTime());
			ps.setDate(2, sd);
			ps.setString(3, a.getHorario());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				protocolo = rs.getInt("id_agendamento");
				System.out.println("prot " + protocolo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return protocolo;
	}

	@Override
	public List<AgPesquisa> pesquisarAnulados(int id_profissional) {
		List<AgPesquisa> lista = new ArrayList<AgPesquisa>();
		String sql = "select agendamento.id_agendamento, agendamento.cpf, agendamento.dataCons, agendamento.horario, profissional.nome as nomeProf, profissional.loja, especialidade.descricao, cliente.telefone, cliente.nome as nomeCli from profissional inner join agendamento on profissional.id_profissional = agendamento.id_profissional inner join especialidade on agendamento.id_especialidade = especialidade.id_especialidade inner join cliente on agendamento.cpf = cliente.cpf where cliente.nome like 'ind' and agendamento.id_profissional = ? order by dataCons desc, horario desc";
		// String sql2 = "select * from agendamento where nome like ? order by
		// dataCons DESC, horario asc";
		// boolean sts;
		// boolean avisado;
		// System.out.println("id da espec: " + id_especialidade);
		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps;

			ps = con.prepareStatement(sql);
			ps.setInt(1, id_profissional);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				AgPesquisa a = new AgPesquisa();

				a.setId_agendamento(rs.getInt("id_agendamento"));
				a.setTelefone(rs.getString("telefone"));
				a.setCpf(rs.getString("cpf"));
				a.setDataCons(rs.getDate("dataCons"));
				a.setHorario(rs.getString("horario"));
				a.setLoja(rs.getString("loja"));
				a.setNome(rs.getString("nomeCli"));
				a.setProfissional(rs.getString("nomeProf"));
				a.setServico(rs.getString("descricao"));

				lista.add(a);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;

	}

	@Override
	public AgPesquisa pesqProtocolo(int id_agendamento, String cpf) {
		AgPesquisa a = new AgPesquisa();
		String sql = "select agendamento.datacons, agendamento.horario, profissional.nome as nomeProf, profissional.loja, especialidade.descricao, cliente.nome from profissional inner join agendamento on profissional.id_profissional = agendamento.id_profissional inner join especialidade on agendamento.id_especialidade = especialidade.id_especialidade inner join cliente on agendamento.cpf= cliente.cpf where id_agendamento = ? and agendamento.cpf like ?";

		try {
			Connection con = DBResourceManager.getInstanceConnection().getCon();
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, id_agendamento);
			ps.setString(2, cpf);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				a.setDataCons(rs.getDate("dataCons"));
				a.setHorario(rs.getString("horario"));
				a.setProfissional(rs.getString("descricao")+" - "+rs.getString("loja")+" - "+rs.getString("nomeProf"));
				a.setNome(rs.getString("nome"));
				a.setServico(rs.getString("descricao") + " - " + a.getProfissional());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return a;
	}

}
