package br.com.manell.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.manell.livraria.dao.DAO;
import br.com.manell.livraria.modelo.Autor;
import br.com.manell.livraria.modelo.Livro;

@ManagedBean
@ViewScoped
public class LivroBean implements Serializable {
    private static final long serialVersionUID = 1L;

	private Livro livro = new Livro();
	private Integer autorId;

	public Livro getLivro() {
		return livro;
	}
	
	public List<Livro> getLivros() {
		return new DAO<Livro>(Livro.class).listaTodos();
	}
	
	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
//			throw new RuntimeException("Livro deve ter pelo menos um Autor.");
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("Livro deve ter pelo menos um Autor"));
			return;
		}

		if(this.livro.getId() == null) {
			new DAO<Livro>(Livro.class).adiciona(this.livro);
		} else {
			new DAO<Livro>(Livro.class).atualiza(this.livro);
		}
		
		this.livro = new Livro();
		
	}
	
	public void remover(Livro livro) {
		System.out.println("Removendo livro");
		new DAO<Livro>(Livro.class).remove(livro);
	}
	
	public void carregar(Livro livro) {
		System.out.println("Carregando livro");
		this.livro = livro;
	}
	
	public void gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
		System.out.println("Escrito por: " + autor.getNome());
	}
	
	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}
	
	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
	    String valor = value.toString();
	    if (!valor.startsWith("1")) {
	        throw new ValidatorException(new FacesMessage("ISBN deveria começar com 1"));
	    }
	}
	
	public String formAutor() {
		System.out.println("Chamando o formulário de autor");
		return "autor?faces-redirect=true";
	}

}
