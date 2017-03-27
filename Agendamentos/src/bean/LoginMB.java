package bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
//import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
//import javax.faces.context.FacesContext;
import javax.faces.context.FacesContext;

import dao.ILoginDAO;
import dao.LoginDAO;

@ManagedBean
@SessionScoped
public class LoginMB implements Serializable {
	private static final long serialVersionUID = 1L;

	private String usuario;
	private String senha;
	private boolean logado = false;
	private boolean administrador = false;

	public String logar() {
		String pagina = "login";
		ILoginDAO dao = new LoginDAO();

		if (dao.autenticarLogin(usuario, senha)) {
			pagina = "areaRestrita?faces-redirect=true";
			logado = true;
		} else {
			logado = false;

			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_FATAL, "Falha de autenticação!", "Usuário ou senha incorretos!"));
//			fc.addMessage("formBody:txtSenha", new FacesMessage(FacesMessage.SEVERITY_WARN,"",""));
			
			

		}
		
		usuario = "";
		senha = "";

		return pagina;
	}

	public String deslogar(){
		logado = false;
		
		return "login?faces-redirect=true";
		
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

	public boolean isAdmin() {
		return administrador;
	}

	public void setAdmin(boolean admin) {
		this.administrador = admin;
	}

}
