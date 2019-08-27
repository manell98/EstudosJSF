package br.com.manell.livraria.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.manell.livraria.dao.UsuarioDao;
import br.com.manell.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class LoginBean {

	private Usuario usuario = new Usuario();

	public Usuario getUsuario() {
		return usuario;
	}
	
	public String efetuarLogin() {
		System.out.println("Fazendo o login do usuario " + this.usuario.getEmail());
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		boolean existe = new UsuarioDao().existe(this.usuario);
		
		if(existe) {			
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);		
			return "livro?faces-redirect=true";			
		}
		
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usuário não econtrado!"));
		
		return "login?faces-redirect=true";	
	}
	
	public String deslogar() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		
		return "login?faces-redirect=true";
	}

}
