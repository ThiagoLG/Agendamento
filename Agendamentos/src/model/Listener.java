package model;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import bean.LoginMB;

public class Listener implements PhaseListener {

	private static final long serialVersionUID = 8137086901172772454L;

	@Override
	public void afterPhase(PhaseEvent e) {
		System.out.println("After Phase sendo executado para a Fase : " + e.getPhaseId());
		FacesContext fc = FacesContext.getCurrentInstance();
		String nomePagina = fc.getViewRoot().getViewId();
		System.out.println("Pagina acessada : " + nomePagina);

		List<String> lstPaginas = new ArrayList<String>();
		lstPaginas.add("/index.xhtml");
		lstPaginas.add("/adicionarAgendamento.xhtml");
		lstPaginas.add("/login.xhtml");
		lstPaginas.add("/contato.xhtml");
		lstPaginas.add("/posAgendamento.xhtml");
		lstPaginas.add("/posAnularDia.xhtml");
		lstPaginas.add("/manual.xhtml");
		lstPaginas.add("/manual2.xhtml");
		lstPaginas.add("/cadastrarCliente.xhtml");
		lstPaginas.add("/areaCliente.xhtml");
		lstPaginas.add("/editarExcluirAgendamento.xhtml");
		lstPaginas.add("/posAlterarAgendamento.xhtml");
		lstPaginas.add("/posExcluirAgendamento.xhtml");
		
		
		if (!lstPaginas.contains(nomePagina)) {
			Application app = fc.getApplication();
			LoginMB login = app.evaluateExpressionGet(fc, "#{loginMB}", LoginMB.class);

			if (login.isLogado()) {
				System.out.println("Usuario logado pode prosseguir");

			} else {
				System.out.println("Usuario não logado direcionando para a pagina ./login.xhtml");
				NavigationHandler nav = app.getNavigationHandler();
				nav.handleNavigation(fc, "", "login?faces-redirect=true");
				fc.renderResponse();
			}
		}

	}

	@Override
	public void beforePhase(PhaseEvent e) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
