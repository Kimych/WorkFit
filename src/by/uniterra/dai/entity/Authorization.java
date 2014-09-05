package by.uniterra.dai.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the authorization database table.
 * 
 */
@Entity
@NamedQueries(
        { 
            @NamedQuery(name = Authorization.NQ_FIND_ALL, query = "SELECT A FROM Authorization A"),
            @NamedQuery(name = Authorization.NQ_FIND_AUTHORIZATION_BY_LOGIN, query = "SELECT a FROM Authorization a where a.login = :" + Authorization.PARAMETER_LOGIN ),
        })
public class Authorization implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public static final String NQ_FIND_ALL = "Authorization.findAll";
    public static final String NQ_FIND_AUTHORIZATION_BY_LOGIN = "Authorization.findAllRoleByLogin";

    public static final String PARAMETER_LOGIN = "login";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUTHORIZATION_ID")
    private int authorizationId;

    private String email;

    private String login;

    private String password;

    // bi-directional many-to-one association to Worker
    @ManyToOne
    @JoinColumn(name = "WORKER_ID")
    private Worker worker;
 // bi-directional many-to-many association to Authorization
    @ManyToMany
    @JoinTable(name = "authorization_to_role", joinColumns = { @JoinColumn(name = "AUTHORIZATION_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
    private List<Role> roles;;

/*    // bi-directional many-to-many association to Role
    @ManyToMany(mappedBy = "authorizations")
    private List<Role> roles;*/

    public Authorization()
    {
    }

    public int getAuthorizationId()
    {
        return this.authorizationId;
    }

    public void setAuthorizationId(int authorizationId)
    {
        this.authorizationId = authorizationId;
    }

    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getLogin()
    {
        return this.login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Worker getWorker()
    {
        return this.worker;
    }

    public void setWorker(Worker worker)
    {
        this.worker = worker;
    }

    public List<Role> getRoles()
    {
        return this.roles;
    }

    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }

    @Override
    public String toString()
    {
        return "Authorization [authorizationId=" + authorizationId + ", email=" + email + ", login=" + login + ", password=" + password + ", worker=" + worker
                + ", roles=" + roles + "]";
    }

}