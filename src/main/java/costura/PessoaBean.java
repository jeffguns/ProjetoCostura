package costura;

import dao.DaoGeneric;
import dao.DaoUsuario;
import entidades.Pessoa;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jeff_
 */
@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean {

    private Pessoa pessoa = new Pessoa();
    private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();
    private List<Pessoa> list = new ArrayList<Pessoa>();
    private DaoUsuario<Pessoa> daoUsuario = new DaoUsuario<Pessoa>();
    private String campoPesquisar;

    @PostConstruct
    public void init() {
        list = daoGeneric.listar(Pessoa.class);
    }


    public void setList(List<Pessoa> list) {
        this.list = list;
    }

    public String salvar() {
        pessoa = daoGeneric.merge(pessoa);
        list.add(pessoa);
        pessoa = new Pessoa();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação:", "Salvo com sucesso!"));
        return "";
    }

    public String novo() {
        pessoa = new Pessoa();
        return "";
    }

    public String remove() {
        daoGeneric.deletePorId(pessoa);
        list.remove(pessoa);
        pessoa = new Pessoa();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação:", "Removido com sucesso!"));
        return "";
    }

    public void pesquisar() {
      list = daoUsuario.pesquisar(campoPesquisar);

    }

    public List<Pessoa> getList() {
        return list;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public DaoGeneric<Pessoa> getDaoGeneric() {
        return daoGeneric;
    }

    public void setDaoGeneric(DaoGeneric<Pessoa> daoGeneric) {
        this.daoGeneric = daoGeneric;
    }

    public List<Pessoa> getPessoas() {
        return list;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.list = pessoas;
    }

    public String getCampoPesquisar() {
        return campoPesquisar;
    }

    public void setCampoPesquisar(String campoPesquisar) {
        this.campoPesquisar = campoPesquisar;
    }

    public String logar() {

        Pessoa pessoaUser = daoUsuario.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());

        if (pessoaUser != null) {// achou o usuário

            // adicionar o usuário na sessão usuarioLogado
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            externalContext.getSessionMap().put("usuarioLogado", pessoaUser);

            return "primeirapagina.xhtml";
        }

        return "index.xhtml";
    }

    public boolean permiteAcesso(String acesso) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");

        return pessoaUser.getPerfilUser().equals(acesso);
    }

}
