package dao;

import java.util.List;

import model.Especialidade;
import model.Profissional;

public interface IEspecialidadeDAO {
	public List<Especialidade> carregarEspecialidades();

	public Especialidade pesquisarEspecialidade(int id);

	public List<String> listarHorarios(int id);
	
	public boolean inserirHorarios(int id, String hora);

	public String pesqDescricao(int id);

	public List<String> pesquisarHorarios();
	
	public boolean adicionarProfissional(Profissional p);

	public Profissional pesquisarProf(String nome);

	public boolean alterarProf(Profissional profissional, int id);

	public boolean excluirProf(int id);
	
	public int pesquisarIdEspecialidade(String nome, String especialidade);
	
	public List<Especialidade> listarTodasEspecialidades();
}
