package dao;

import java.util.Date;
import java.util.List;

import model.AgPesquisa;
import model.Agendamento;

public interface IAgendamentoDAO {

	public boolean adicionarAgendamento(Agendamento a);
	
	public List<String> horariosOcupados(Agendamento a);
	
	public List<AgPesquisa> pesquisarPorNome(String nome, int id_profissional);
	
	public List<AgPesquisa> pesquisarAnulados(int id_profissional);
	
	public List<AgPesquisa> pesquisarPorData(Date data, int id_profissional);
	
	public boolean atualizar (Agendamento a);
	
	public int protocoloAgendamento(Agendamento a);
	
	public boolean confirmar (Agendamento a);
	
	public boolean remover (int id);

	public boolean verificaAgenda (Agendamento a);
	
	public boolean verificarDia(int id, Date data);
	
	public boolean avisado(int id);
	
	public AgPesquisa pesqProtocolo(int id_agendamento, String cpf);
}
