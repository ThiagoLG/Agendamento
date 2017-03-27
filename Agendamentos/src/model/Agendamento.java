package model;

import java.util.Date;

public class Agendamento {

	private int id_especialidade;
	private int id_agendamento;
	private int id_profissional;
	private Date data;
	private String horario;
	private String cpf;
	private String descServProf;

	public int getId_especialidade() {
		return id_especialidade;
	}

	public void setId_especialidade(int id_especialidade) {
		this.id_especialidade = id_especialidade;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public int getId_agendamento() {
		return id_agendamento;
	}

	public void setId_agendamento(int id_agendamento) {
		this.id_agendamento = id_agendamento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public int getId_profissional() {
		return id_profissional;
	}

	public void setId_profissional(int id_profissional) {
		this.id_profissional = id_profissional;
	}

	public String getDescServProf() {
		return descServProf;
	}

	public void setDescServProf(String descServProf) {
		this.descServProf = descServProf;
	}
	
	

}
