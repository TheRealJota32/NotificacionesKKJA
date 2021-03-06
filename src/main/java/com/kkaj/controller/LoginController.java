package com.kkaj.controller;

import com.kkaj.model.Usuario;
import com.kkaj.service.UsuarioDao;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author kenneth
 */
@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    private static final long serialVersionUID = 1L;

    private String user, pass;

    private Usuario usuario;

    private int id;

    private String errorMessage;

    //test
    public LoginController() {

    }

    public String login() throws SQLException, ClassNotFoundException {
        UsuarioDao users = new UsuarioDao();
        if (Credentials(users)) {
            return "/faces/front/correos?faces-redirect=true&idUsuario= " + this.usuario.getId();
        } else {
            System.out.println("hola grupo!!!!!!!!!!!");
            this.user = null;
            this.pass = null;
            this.errorMessage = "Failed to login!";
            return "/faces/front/errorLogin?faces-redirect=true";
        }
    }

    private boolean Credentials(UsuarioDao users) throws SQLException, ClassNotFoundException {
        FacesMessage msg;
        Usuario userLogin = users.login(this.user, this.pass);
        if (userLogin != null) {
            this.usuario = userLogin;
            this.id = this.usuario.getId();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "correos", this.user);
            return true;
        }
        return false;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        return "index?faces-redirect=true";
    }

    public void mostrar() {
        String t = "correos.xhtml?idUsuario=" + usuario.getId();
        this.setId(usuario.getId());
        redirect(t);
    }

    public void redirect(String url) {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext
                    .getCurrentInstance().getExternalContext().getRequest();
            FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .redirect(
                            request.getContextPath()
                            + "/front/" + url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
