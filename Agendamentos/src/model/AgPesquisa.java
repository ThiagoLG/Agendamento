package model;

import java.util.Date;

public class AgPesquisa {

	private String cpf;
	private Date dataCons;
	private String horario;
	private String nome;
	private String loja;
	private String servico;
	private String profissional;
	private int id_agendamento;
	private String telefone;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataCons() {
		return dataCons;
	}

	public void setDataCons(Date dataCons) {
		this.dataCons = dataCons;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLoja() {
		return loja;
	}

	public void setLoja(String loja) {
		this.loja = loja;
	}

	public String getServico() {
		return servico;
	}

	public void setServico(String servico) {
		this.servico = servico;
	}

	public String getProfissional() {
		return profissional;
	}

	public void setProfissional(String profissional) {
		this.profissional = profissional;
	}

	public int getId_agendamento() {
		return id_agendamento;
	}

	public void setId_agendamento(int id_agendamento) {
		this.id_agendamento = id_agendamento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
