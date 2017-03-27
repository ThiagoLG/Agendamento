package dao;

import java.util.List;

import model.Profissional;

public interface IProfissionalDao {

	public List<Profissional> listarProfissionais(int id);

	public String pesqNome(int id);

	public List<String> listarHorarios(int id);

	public List<Profissional> listarTodos();

	public int idProfissional(int id_agendamento);

	public boolean adicionarProfissional(Profissional p);

	public int idProfissionalCad(Profissional p);

	public void insertEspecialidadeProf(int id_profissional, int id_especialidade);

	public void inserirHorarios(int id_profissional, String hora);

	public Profissional pesquisarProfissional(String nome, String loja);

	public List<String> pesquisarEspecialidadesProf(int id_profissional);

	public boolean alterarProfissional(Profissional p);
	
	public boolean excluirProfissional(Profissional p);
	
	public boolean alterarEspecProf(int id_profissional, int id_especialidade);
}
