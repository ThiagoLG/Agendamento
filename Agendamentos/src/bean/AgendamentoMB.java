package bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import dao.AgendamentoDAO;
import dao.ClienteDao;
import dao.EspecialidadeDAO;
import dao.IAgendamentoDAO;
import dao.IClienteDao;
import dao.IEspecialidadeDAO;
import dao.IProfissionalDao;
import dao.ProfissionalDao;
import model.AgPesquisa;
import model.Agendamento;
import model.Cliente;
import model.Especialidade;
import model.Gmail;
import model.Profissional;

@ManagedBean
@SessionScoped
public class AgendamentoMB {
	public Especialidade especialidade;
	public Profissional profissional;
	public AgPesquisa agEdit;
	public Agendamento agendamento;
	public Cliente cliente;
	public AgPesquisa agPesquisa;
	public String protocolo;
	public boolean cli;
	public List<String> especSelecionadas;
	public List<String> horariosDisponiveis;
	public List<Especialidade> lstEspecialidade;
	public List<Especialidade> lstTodasEspecialidades;
	public List<AgPesquisa> lstAgendamentos;
	public List<Profissional> lstProfissionais;
	public List<Profissional> todosProfissionais;

	@PostConstruct
	public void Inicializar() {
		especialidade = new Especialidade();
		// especialidade.setId_especialidade(0);
		agendamento = new Agendamento();
		agEdit = new AgPesquisa();
		listarEspecialidades();
		listarTodosProfissionais();
		profissional = new Profissional();
		cliente = new Cliente();
		protocolo = "0";
		cli = false;
		listarTodasEspecialidades();
		especSelecionadas = new ArrayList<String>();
		especialidade.setId_especialidade(1);
		carregarProfissionais();

	}

	public void print() {
		for (int i = 0; i < especSelecionadas.size(); i++) {
			System.out.println(especSelecionadas.get(i));
		}
	}

	public void listarTodasEspecialidades() {
		IEspecialidadeDAO espDao = new EspecialidadeDAO();
		lstTodasEspecialidades = espDao.listarTodasEspecialidades();
	}

	public void listarTodosProfissionais() {
		IProfissionalDao profDao = new ProfissionalDao();
		todosProfissionais = profDao.listarTodos();
	}

	public String carregarProfissionais() {
		IProfissionalDao profDao = new ProfissionalDao();
		lstProfissionais = new ArrayList<Profissional>();

		lstProfissionais = profDao.listarProfissionais(especialidade.getId_especialidade());

		return "";
	}

	public String redirecionarPagina() {
		String pagina;

		System.out.println(especialidade.getId_especialidade());
		if (especialidade.getId_especialidade() == 0) {
			pagina = "";
		} else {
			carregarEspecialidade();
			System.out.println(especialidade.getDescricao());
			pagina = "adicionarAgendamento?faces-redirect=true";
			agendamento = new Agendamento();
			horariosDisponiveis = new ArrayList<String>();

		}

		return pagina;
	}

	public String listarEspecialidades() {
		IEspecialidadeDAO especDao = new EspecialidadeDAO();
		lstEspecialidade = especDao.carregarEspecialidades();

		for (Especialidade e : lstEspecialidade) {
			e.setDescricao(especDao.pesqDescricao(e.getId_especialidade()));
			System.out.println(e.getDescricao());
		}

		return "";
	}

	public void carregarEspecialidade() {
		IEspecialidadeDAO especDao = new EspecialidadeDAO();
		especialidade = especDao.pesquisarEspecialidade(especialidade.getId_especialidade());
		especialidade.setDescricao(especDao.pesqDescricao(especialidade.getId_especialidade()));
	}

	public void carregarHorarios() {
		System.out.println("entrou aqui");
		System.out.println(agendamento.getData());
		// agendamento.setId_especialidade(especialidade.getId_especialidade());
		agendamento.setId_profissional(profissional.getId());
		IProfissionalDao profDao = new ProfissionalDao();

		IAgendamentoDAO agDao = new AgendamentoDAO();

		List<String> horarios = new ArrayList<String>();
		System.out.println(especialidade.getId_especialidade());
		horariosDisponiveis = profDao.listarHorarios(profissional.getId());

		horarios = agDao.horariosOcupados(agendamento);

		if (especialidade.getId_especialidade() == 3) {

			String hora;
			System.out.println("ele vai remover o: " + horariosDisponiveis.get(21));
			horariosDisponiveis.remove(21);

			int index = 0;
			for (int i = 0; i < horarios.size(); i++) {
				hora = horarios.get(i);
				System.out.println(hora);
				try {

					for (int x = 0; x < 22; x++) {
						index = x;
						if (hora.equals("09:00")) {
							horariosDisponiveis.remove(0);
							x = 22;
						} else if (horariosDisponiveis.get(x).equals(hora)) {

							if (!hora.equals(horariosDisponiveis.get(0))) {
								String h2 = horariosDisponiveis.get(x - 1);

								// System.out.println("h2- "+h2);
								// System.out.println("hD
								// -"+horariosDisponiveis.get(x));
								StringBuffer sb = new StringBuffer();
								sb.append(hora.charAt(0));
								sb.append(hora.charAt(1));
								String horaAtu = sb.toString();

								sb = new StringBuffer();
								sb.append(hora.charAt(3));
								sb.append(hora.charAt(4));
								String minAtu = sb.toString();

								sb = new StringBuffer();
								sb.append(h2.charAt(0));
								sb.append(h2.charAt(1));
								String horaAnt = sb.toString();

								sb = new StringBuffer();
								sb.append(h2.charAt(3));
								sb.append(h2.charAt(4));
								String minAnt = sb.toString();

								int hAtu = Integer.parseInt(horaAtu);
								int mAtu = Integer.parseInt(minAtu);
								int hAnt = Integer.parseInt(horaAnt);
								int mAnt = Integer.parseInt(minAnt);

								System.out.println("Hora anterior: " + horaAnt + ":" + minAnt);
								System.out.println("Hora atual: " + horaAtu + ":" + minAtu);

								if (mAtu == 30) {
									if (hAtu == hAnt) {

										System.out.println("Removido: " + horariosDisponiveis.get(x));
										System.out.println("Removido: " + horariosDisponiveis.get(x - 1));
										System.out.println("=====================================");

										horariosDisponiveis.remove(x);
										horariosDisponiveis.remove(x - 1);

									} else {

										System.out.println("Removido: " + horariosDisponiveis.get(x));

										System.out.println("=====================================");

										horariosDisponiveis.remove(x);

									}
								} else {
									if (hAnt == hAtu - 1) {
										if (mAnt == 30) {

											System.out.println("Removido: " + horariosDisponiveis.get(x));
											System.out.println("Removido: " + horariosDisponiveis.get(x - 1));
											System.out.println("=====================================");
											horariosDisponiveis.remove(x);
											horariosDisponiveis.remove(x - 1);

										} else {

											System.out.println("Removido: " + horariosDisponiveis.get(x));
											System.out.println("=====================================");
											horariosDisponiveis.remove(x);

										}

									} else {
										System.out.println("Removido: " + horariosDisponiveis.get(x));
										System.out.println("=====================================");
										horariosDisponiveis.remove(x);

									}
								}
								//
								// horariosDisponiveis.remove(x);
								// horariosDisponiveis.remove(x - 1);
								x = 22;
							} else {
								System.out.println("Removido: " + horariosDisponiveis.get(x));
								System.out.println("=====================================");
								horariosDisponiveis.remove(x);
								x = 22;
							}

						} else if (hora.equals("19:30")) {
							for (String h : horariosDisponiveis) {
								if (h.equals("19:00")) {
									horariosDisponiveis.remove(h);
								}
							}

						}
					}
				} catch (Exception e) {
					String erro = e.toString();
					if (erro.contains("java.lang.IndexOutOfBoundsException")) {
						System.out.println("index :" + index);
						System.out.println("=======================================================================");
						System.out.println("\nDEU ERRO POR CAUSA DO INDEX QUE EXCEDE A LISTA, MAS TA TUDO CERTO!\n");
						System.out.println("=======================================================================");
					}
				}
			}

		} else

		{
			String hora;
			for (int i = 0; i < horarios.size(); i++) {
				hora = horarios.get(i);
				// System.out.println(hora);
				try {
					for (int x = 0; x < 22; x++) {
						if (horariosDisponiveis.get(x).equals(hora)) {
							// System.out.println(horariosDisponiveis.get(x));
							// System.out.println(horariosDisponiveis.get(0));
							horariosDisponiveis.remove(x);
							x = 22;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

		if (horariosDisponiveis.isEmpty()) {
			horariosDisponiveis.add("Sem horarios!");
		}

		// return "";

	}

	public String painelGerenciamento() {
		Inicializar();
		listarEspecialidades();
		Especialidade e = new Especialidade();
		e.setId_especialidade(0);
		e.setDescricao("Qualquer");
		lstEspecialidade.add(e);

		return "gerenciarAgendamentos?faces-redirect=true";
	}

	public String cadastrar() {

		IAgendamentoDAO agDao = new AgendamentoDAO();
		IClienteDao cliDao = new ClienteDao();

		Cliente c = new Cliente();
		c = cliDao.getCliente(agendamento.getCpf());

		String pagina = "";

		agendamento.setId_especialidade(especialidade.getId_especialidade());
		agendamento.setId_profissional(profissional.getId());

		if (agendamento.getHorario().equals("")) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("formBody:cbbHorarios",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo horario obrigatório!", ""));
			System.out.println("horarios ta vazio");

		} else if (agendamento.getHorario().equalsIgnoreCase("Sem horarios!")) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("formBody", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível agendar",
					"o dia selecionado não possui horários disponíveis"));
		} else {

			// Agendar Corte + Barba
			if (agendamento.getId_especialidade() == 3) {
				if (cliDao.cadastrado(agendamento.getCpf())) {
					if (agDao.verificaAgenda(agendamento)) {
						if (agendamento.getId_especialidade() != 0) {
							String hora;
							for (int i = 0; i < horariosDisponiveis.size(); i++) {
								hora = horariosDisponiveis.get(i);
								if (hora.equals(agendamento.getHorario())) {
									if (agDao.adicionarAgendamento(agendamento)) {
										// Email
										SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
										protocolo = Integer.toString(agDao.protocoloAgendamento(agendamento));
										String mensagem = "Olá, " + c.getNome()
												+ ", seu agendamento foi registrado!\nDetalhes:\nProtocolo: "
												+ protocolo + "\nNome: " + c.getNome() + "\nServiço: "
												+ especialidade.getDescricao() + "\nData: "
												+ sdf.format(agendamento.getData()) + "\nHorario: "
												+ agendamento.getHorario();
										String assunto = "AGENDAMENTO - BRANCO BARBERSHOP";
										try {
											Gmail.EnviaEmail(c.getEmail(), mensagem, assunto);
											Gmail.EnviaEmail("agenda.brancobarbershop@gmail.com", mensagem, assunto);
										} catch (Exception e) {
											String ex = e.toString();
											if (ex.contains("org.apache.commons.mail.EmailException")) {
												System.out.println("==============================");
												System.out.println("\nDEU ERRO AO ENVIAR O EMAIL!!\n");
												System.out.println("===============================");

											}
										}
										agendamento.setHorario(horariosDisponiveis.get(i + 1));

										agDao.adicionarAgendamento(agendamento);

										System.out.println(mensagem);
										carregarHorarios();
										agendamento = new Agendamento();
										horariosDisponiveis.clear();
										pagina = "posAgendamento?faces-redirect=true";
									}
								}
							}

						}
					} else {
						FacesContext fc = FacesContext.getCurrentInstance();
						fc.addMessage("formBody", new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Não foi possível agendar", "O dia e horario selecionado já está ocupado"));
					}
				} else {
					FacesContext fc = FacesContext.getCurrentInstance();
					fc.addMessage("formBody", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cliente não registrado",
							"Por favor, realize seu cadastro para poder agendar horários."));

				}
			}

			// Agendar Normal
			if (cliDao.cadastrado(agendamento.getCpf())) {
				if (agDao.verificaAgenda(agendamento)) {
					if (agendamento.getId_especialidade() != 0) {

						if (agDao.adicionarAgendamento(agendamento)) {
							// System.out.println("o protocolo é: " +
							// agDao.protocoloAgendamento(agendamento));
							// agendamento.setId_agendamento(agDao.protocoloAgendamento(agendamento));
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							protocolo = Integer.toString(agDao.protocoloAgendamento(agendamento));
							String mensagem = "Olá, " + c.getNome()
									+ ", seu agendamento foi registrado!\nDetalhes:\nProtocolo: " + protocolo
									+ "\nNome: " + c.getNome() + "\nServiço: " + especialidade.getDescricao()
									+ "\nData: " + sdf.format(agendamento.getData()) + "\nHorario: "
									+ agendamento.getHorario();
							String assunto = "AGENDAMENTO - BRANCO BARBERSHOP";
							Gmail.EnviaEmail(c.getEmail(), mensagem, assunto);
							Gmail.EnviaEmail("agenda.brancobarbershop@gmail.com", mensagem, assunto);

							System.out.println(mensagem);
							carregarHorarios();
							agendamento = new Agendamento();
							horariosDisponiveis.clear();
							pagina = "posAgendamento?faces-redirect=true";
						}
					}
				} else {
					FacesContext fc = FacesContext.getCurrentInstance();
					fc.addMessage("formBody", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível agendar",
							"O dia e horario selecionado já está ocupado"));
				}
			} else {
				FacesContext fc = FacesContext.getCurrentInstance();
				fc.addMessage("formBody", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cliente não registrado",
						"Por favor, realize seu cadastro para poder agendar horários."));

			}

		}

		return pagina;

	}

	public String cadastrarCliente() {
		String pagina = "";
		IClienteDao cliDao = new ClienteDao();
		if (cliente.getEmail().contains("@") && cliente.getEmail().contains(".com")) {
			if (cliDao.cadastrarCliente(cliente)) {
				System.out.println("Cadastrou o cliente!!!");
				cliente = new Cliente();
				pagina = "index?faces-redirect=true";
			} else {
				FacesContext fc = FacesContext.getCurrentInstance();
				fc.addMessage("formBody", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Não foi possível cadastrar o cliente!", "Verifique os dados."));

			}
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("formBody:txtEmail",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo email inválido!", ""));

		}

		return pagina;
	}

	public String preencherDia() {
		String pagina = "";
		carregarHorarios();
		IAgendamentoDAO agDao = new AgendamentoDAO();
		if (profissional.getId() != 0) {
			if (agDao.verificarDia(profissional.getId(), agendamento.getData())) {
				for (int i = 0; i < horariosDisponiveis.size(); i++) {

					System.out.println("add o horario" + horariosDisponiveis.get(i));
					agendamento.setCpf("ind");
					agendamento.setHorario(horariosDisponiveis.get(i));
					agendamento.setId_especialidade(1);
					agendamento.setId_profissional(profissional.getId());

					agDao.adicionarAgendamento(agendamento);

				}
				pagina = "posAnularDia?faces-redirect=true";
				agendamento = new Agendamento();
			} else {
				System.out.println("nao pode");
				FacesContext fc = FacesContext.getCurrentInstance();
				fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível anular a data!",
						"Apenas dias sem agendamentos podem ser anulados, remaneje os agendamentos e tente novamente"));

			}

		}

		return pagina;
	}

	public void valueChangeMethod(ValueChangeEvent e) {
		horariosDisponiveis.clear();
	}

	public void pesquisarNome() {
		IAgendamentoDAO agDao = new AgendamentoDAO();
		// IEspecialidadeDAO espDao = new EspecialidadeDAO();

		// System.out.println(agendamento.getNome());

		if (cliente.getNome().equals("anulados")) {
			System.out.println("anul");
			lstAgendamentos = agDao.pesquisarAnulados(profissional.getId());

		} else {
			lstAgendamentos = agDao.pesquisarPorNome(cliente.getNome(), profissional.getId());
		}
		// List<AgPesquisa> ag = new ArrayList<AgPesquisa>();

		if (lstAgendamentos.isEmpty()) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nenhum agendamento foi localizado!",
					"Altere os dados de pesquisa e tente novamente"));
			cliente.setNome("");
		}

		// for (Agendamento a : lstAgendamentos) {
		// a.setNomeEspec(espDao.pesqDescricao(a.getId_especialidade()));
		// ag.add(a);
		// }

		// lstAgendamentos = ag;

	}

	public void pesquisarData() {
		IAgendamentoDAO agDao = new AgendamentoDAO();
		lstAgendamentos = agDao.pesquisarPorData(agendamento.getData(), profissional.getId());

		if (lstAgendamentos.isEmpty()) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nenhum agendamento foi localizado!",
					"Altere os dados de pesquisa e tente novamente"));
			cliente.setNome("");
		}

	}

	public String carregarCampos(AgPesquisa a) {
		// IEspecialidadeDAO espDao = new EspecialidadeDAO();
		// agendamento = new Agendamento();
		// agEdit = new Agendamento();
		// agendamento = a;
		// agEdit = a;
		//
		// especialidade.setId_especialidade(a.getId_especialidade());
		// especialidade.setDescricao(espDao.pesqDescricao(especialidade.getId_especialidade()));

		agPesquisa = a;

		agendamento = new Agendamento();
		agendamento.setId_agendamento(a.getId_agendamento());
		agendamento.setCpf(a.getCpf());
		agendamento.setDescServProf(a.getServico() + " - " + a.getProfissional());
		agendamento.setData(a.getDataCons());
		agendamento.setHorario(a.getHorario());

		carregarHorarios();
		horariosDisponiveis.add(a.getHorario());
		return "alterarAgendamento?faces-redirect=true";
	}

	public String atualizar() {
		String pagina = "";
		System.out.println(agendamento.getDescServProf());
		if (agendamento.getDescServProf().contains("Corte + Barba")) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Não é possível alterar um agendamento de corte + barba!",
					"Por favor, caso queira modificar o horário ou data, primeiro EXCLUA o agendamento e depois remarque para a data e horario desejado!"));
		} else {

			IAgendamentoDAO agDao = new AgendamentoDAO();

			IClienteDao cliDao = new ClienteDao();
			Cliente c = new Cliente();
			c = cliDao.getCliente(agendamento.getCpf());

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			// sdf.format(agendamento.getData());
			String mensagem = "Olá, " + agPesquisa.getNome() + ", seu agendamento foi remarcado! \nDetalhes:\n"
					+ "Nome: " + agPesquisa.getNome() + "\nServiço: " + agendamento.getDescServProf() + "\nData: "
					+ sdf.format(agendamento.getData()) + "\nHorario: " + agendamento.getHorario();
			String assunto = "ALTERAÇÃO DE AGENDAMENTO - BRANCO BARBERSHOP";

			System.out.println(mensagem);
			// agEdit.setId_agendamento(agendamento.getId_agendamento());

			// System.out.println(agendamento.getAvisado());

			if (agDao.verificaAgenda(agendamento)) {
				if (agDao.atualizar(agendamento)) {

					try {
						Gmail.EnviaEmail(c.getEmail(), mensagem, assunto);
						Gmail.EnviaEmail("agenda.brancobarbershop@gmail.com", mensagem, assunto);
					} catch (Exception e) {
						String ex = e.toString();
						if (ex.contains("org.apache.commons.mail.EmailException")) {
							System.out.println("==============================");
							System.out.println("\nDEU ERRO AO ENVIAR O EMAIL!!\n");
							System.out.println("===============================");

						}
					}
					agEdit = new AgPesquisa();

					agendamento = new Agendamento();
					horariosDisponiveis.clear();
					try {
						lstAgendamentos.clear();
					} catch (Exception e) {
						e.printStackTrace();
					}

					pagina = "posAlterarAgendamento?faces-redirect=true";
				} else {
					FacesContext fc = FacesContext.getCurrentInstance();
					fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Não foi possível atualizar o agendamento", "Verifique os dados e tente novamente."));

				}
			} else {
				FacesContext fc = FacesContext.getCurrentInstance();
				fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Não foi possível atualizar o agendamento", "Data e horario já ocupado!"));

			}
		}
		return pagina;
	}

	public String removerAgendamento(AgPesquisa a) {

		agPesquisa = a;

		agendamento = new Agendamento();
		agendamento.setId_agendamento(a.getId_agendamento());
		agendamento.setCpf(a.getCpf());
		agendamento.setDescServProf(a.getServico() + " - " + a.getProfissional());
		agendamento.setData(a.getDataCons());
		agendamento.setHorario(a.getHorario());

		cli = false;
		return "excluirAgendamento?faces-redirect-true";
	}

	public void limparCampos() {
		agendamento = new Agendamento();
		horariosDisponiveis.clear();
		int id = profissional.getId();
		profissional = new Profissional();
		profissional.setId(id);
		cliente = new Cliente();
	}

	public String anularDia() {
		agendamento = new Agendamento();
		especialidade = new Especialidade();
		carregarEspecialidade();
		return "anularDia?faces-redirect=true";
	}

	public String excluir() {
		IAgendamentoDAO agDao = new AgendamentoDAO();
		IClienteDao cliDao = new ClienteDao();

		Cliente c = new Cliente();
		c = cliDao.getCliente(agendamento.getCpf());

		// String mensagem = "Olá, " + agPesquisa.getNome() + ", seu agendamento
		// foi desmarcado!";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		protocolo = Integer.toString(agDao.protocoloAgendamento(agendamento));
		String mensagem = "Olá, " + c.getNome() + ", seu agendamento foi CANCELADO!\nDetalhes:\nProtocolo: " + protocolo
				+ "\nNome: " + c.getNome() + "\nServiço: " + especialidade.getDescricao() + "\nData: "
				+ sdf.format(agendamento.getData()) + "\nHorario: " + agendamento.getHorario();
		String assunto = "AGENDAMENTO CANCELADO - BRANCO BARBERSHOP";

		if (isCli()) {
			if (agendamento.getDescServProf().contains("Corte + Barba")) {
				if (agDao.remover(agendamento.getId_agendamento())) {
					try {
						Gmail.EnviaEmail(c.getEmail(), mensagem, assunto);
						Gmail.EnviaEmail("agenda.brancobarbershop@gmail.com", mensagem, assunto);
					} catch (Exception e) {
						String ex = e.toString();
						if (ex.contains("org.apache.commons.mail.EmailException")) {
							System.out.println("==============================");
							System.out.println("\nDEU ERRO AO ENVIAR O EMAIL!!\n");
							System.out.println("===============================");

						}
					}
					agDao.remover(agendamento.getId_agendamento() + 1);

					try {
						agendamento = new Agendamento();
					} catch (Exception e) {
						e.printStackTrace();
					}

					// lstAgendamentos.clear();
				} else {
					FacesContext fc = FacesContext.getCurrentInstance();
					fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Não foi possível remover o agendamento", ""));

				}
			} else {
				if (agDao.remover(agendamento.getId_agendamento())) {
					Gmail.EnviaEmail(c.getEmail(), mensagem, assunto);
					Gmail.EnviaEmail("agenda.brancobarbershop@gmail.com", mensagem, assunto);
					try {
						agendamento = new Agendamento();
					} catch (Exception e) {
						e.printStackTrace();
					}
					lstAgendamentos.clear();
				} else {
					FacesContext fc = FacesContext.getCurrentInstance();
					fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Não foi possível remover o agendamento", ""));

				}
			}
		} else {

			if (agDao.remover(agendamento.getId_agendamento())) {
				Gmail.EnviaEmail(c.getEmail(), mensagem, assunto);
				Gmail.EnviaEmail("agenda.brancobarbershop@gmail.com", mensagem, assunto);
				try {
					agendamento = new Agendamento();
				} catch (Exception e) {
					e.printStackTrace();
				}
				lstAgendamentos.clear();
			} else {
				FacesContext fc = FacesContext.getCurrentInstance();
				fc.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível remover o agendamento", ""));

			}
		}
		return "posExcluirAgendamento?faces-redirect=true";
	}

	public String adicionarProfissional() {
		String pagina = "";
		IProfissionalDao profDao = new ProfissionalDao();
		List<String> horas = new ArrayList<String>();

		if (profDao.adicionarProfissional(profissional)) {
			System.out.println("cadastrou o prof");
			int id = profDao.idProfissionalCad(profissional);
			for (int i = 0; i < especSelecionadas.size(); i++) {
				int id_espec = Integer.parseInt(especSelecionadas.get(i));
				System.out.println("id da especialidade: " + id_espec);
				profDao.insertEspecialidadeProf(id, id_espec);
			}
			horas = profDao.listarHorarios(1);
			for (String hora : horas) {
				profDao.inserirHorarios(id, hora);
			}
			especSelecionadas.clear();
			profissional.setNome("");
			Inicializar();
			pagina = "areaRestrita?faces-redirect=true";

		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("formBody", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Não foi possível cadastrar o profissional!", "Verifique os campos e tente novamente."));
		}

		// IEspecialidadeDAO espDao = new EspecialidadeDAO();
		// List<String> horas = new ArrayList<String>();
		// String pagina = "";
		//
		// if (espDao.adicionarProfissional(profissional)) {
		//
		// // horas = espDao.pesquisarHorarios();
		// // int id = espDao.pesquisarIdEspecialidade(profissional.getNome(),
		// // profissional.getEspecialidade());
		// // for (String hora : horas) {
		// // espDao.inserirHorarios(id, hora);
		// // }
		// // System.out.println("cadastrou o carinha");
		// // profissional = new Profissional();
		// // listarEspecialidades();
		// // pagina = "areaRestrita?faces-redirect=true";
		// } else {
		// FacesContext fc = FacesContext.getCurrentInstance();
		// fc.addMessage("formBody", new
		// FacesMessage(FacesMessage.SEVERITY_ERROR,
		// "Não foi possível cadastrar o Serviço / Profissional", "Verifique os
		// campos e tente novamente"));
		//
		// }

		return pagina;
	}

	public String pesquisarProfissional() {
		String pagina = "";
		IProfissionalDao profDao = new ProfissionalDao();
		Profissional p = new Profissional();
		p = profDao.pesquisarProfissional(profissional.getNome(), profissional.getLoja());

		if (p.getNome() == null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nenhum profissional foi localizado!",
					"Verifique os dados e tente novamente"));

		} else {
			profissional = p;
			// listarEspecialidades();
			especSelecionadas = profDao.pesquisarEspecialidadesProf(profissional.getId());
			pagina = "alterarProfissional?faces-redirect=true";
		}

		return pagina;
	}

	public String pesquisarProfissionalToExcl() {

		String pagina = "";
		IProfissionalDao profDao = new ProfissionalDao();
		Profissional p = new Profissional();
		p = profDao.pesquisarProfissional(profissional.getNome(), profissional.getLoja());

		if (p.getNome() == null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nenhum profissional foi localizado!",
					"Verifique os dados e tente novamente"));

		} else {
			profissional = p;
			// listarEspecialidades();
			especSelecionadas = profDao.pesquisarEspecialidadesProf(profissional.getId());
			pagina = "excluirProfissional?faces-redirect=true";
		}

		return pagina;

	}

	public String alterarProfissional() {
		String pagina = "";
		// String id_esp = "";
		// IEspecialidadeDAO espDao = new EspecialidadeDAO();
		IProfissionalDao profDao = new ProfissionalDao();

		if (profDao.alterarProfissional(profissional)) {
			pagina = "areaRestrita?faces-redirect=true";
			for (String id_esp : especSelecionadas) {
				profDao.insertEspecialidadeProf(profissional.getId(), Integer.parseInt(id_esp));
			}
			profissional = new Profissional();
			especSelecionadas.clear();
			Inicializar();
			// listarEspecialidades();
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível alterar!",
					"Verifique os dados e tente novamente"));
		}

		return pagina;
	}

	public String excluirProfissional() {
		String pagina = "";
		// IEspecialidadeDAO espDao = new EspecialidadeDAO();
		IProfissionalDao profDao = new ProfissionalDao();

		if (profDao.excluirProfissional(profissional)) {
			pagina = "areaRestrita?faces-redirect=true";
			profissional = new Profissional();
			especSelecionadas.clear();
			Inicializar();
			// listarEspecialidades();
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível excluir!", ""));
		}

		return pagina;
	}

	public String deslogar() {
		String pagina = "";

		return pagina;
	}

	public String pesquisarProtocolo() {
		String pagina = "";
		IAgendamentoDAO agDao = new AgendamentoDAO();
		IProfissionalDao profDao = new ProfissionalDao();
		System.out.println("oi");

		agPesquisa = agDao.pesqProtocolo(agendamento.getId_agendamento(), agendamento.getCpf());

		if (agPesquisa.getNome() == null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nenhum agendamento encontrado",
					"Verifique os dados e tente novamente"));

		} else {

			profissional.setId(profDao.idProfissional(agendamento.getId_agendamento()));

			agPesquisa.setCpf(agendamento.getCpf());
			agPesquisa.setId_agendamento(agendamento.getId_agendamento());

			agendamento.setData(agPesquisa.getDataCons());
			agendamento.setHorario(agPesquisa.getHorario());
			agendamento.setDescServProf(agPesquisa.getProfissional());

			carregarHorarios();
			horariosDisponiveis.add(agPesquisa.getHorario());

			cli = true;

			pagina = "editarExcluirAgendamento?faces-redirect=true";
		}

		return pagina;
	}

	// GET E SET
	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	public List<Especialidade> getLstEspecialidade() {
		return lstEspecialidade;
	}

	public void setLstEspecialidade(List<Especialidade> lstEspecialidade) {
		this.lstEspecialidade = lstEspecialidade;
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	public List<String> getHorariosDisponiveis() {
		return horariosDisponiveis;
	}

	public void setHorariosDisponiveis(List<String> horariosDisponiveis) {
		this.horariosDisponiveis = horariosDisponiveis;
	}

	public List<AgPesquisa> getLstAgendamentos() {
		return lstAgendamentos;
	}

	public void setLstAgendamentos(List<AgPesquisa> lstAgendamentos) {
		this.lstAgendamentos = lstAgendamentos;
	}

	public AgPesquisa getAgEdit() {
		return agEdit;
	}

	public void setAgEdit(AgPesquisa agEdit) {
		this.agEdit = agEdit;
	}

	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}

	public List<Profissional> getLstProfissionais() {
		return lstProfissionais;
	}

	public void setLstProfissionais(List<Profissional> lstProfissionais) {
		this.lstProfissionais = lstProfissionais;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Profissional> getTodosProfissionais() {
		return todosProfissionais;
	}

	public void setTodosProfissionais(List<Profissional> todosProfissionais) {
		this.todosProfissionais = todosProfissionais;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public AgPesquisa getAgPesquisa() {
		return agPesquisa;
	}

	public void setAgPesquisa(AgPesquisa agPesquisa) {
		this.agPesquisa = agPesquisa;
	}

	public List<Especialidade> getLstTodasEspecialidades() {
		return lstTodasEspecialidades;
	}

	public void setLstTodasEspecialidades(List<Especialidade> lstTodasEspecialidades) {
		this.lstTodasEspecialidades = lstTodasEspecialidades;
	}

	public List<String> getEspecSelecionadas() {
		return especSelecionadas;
	}

	public void setEspecSelecionadas(List<String> especSelecionadas) {
		this.especSelecionadas = especSelecionadas;
	}

	public boolean isCli() {
		return cli;
	}

	public void setCli(boolean cli) {
		this.cli = cli;
	}

}
