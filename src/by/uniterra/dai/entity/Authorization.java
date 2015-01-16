package by.uniterra.dai.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import by.uniterra.udi.util.Cryptor;

/**
 * The persistent class for the authorization database table.
 * 
 */
@Entity
@NamedQueries({
        @NamedQuery(name = Authorization.NQ_FIND_ALL, query = "SELECT A FROM Authorization A"),
        @NamedQuery(name = Authorization.NQ_FIND_AUTHORIZATION_BY_LOGIN, query = "SELECT a FROM Authorization a where a.login = :"
                + Authorization.PARAMETER_LOGIN),
        @NamedQuery(name = Authorization.NQ_FIND_ROLE_BY_LOGIN_AND_PASSWORD, query = "SELECT a.roles FROM Authorization a where a.login = :"
                + Authorization.PARAMETER_LOGIN + " AND a.password = :" + Authorization.PARAMETER_PASSWORD),
        @NamedQuery(name = Authorization.NQ_FIND_AUTHORIZATION_BY_LOGIN_AND_PASSWORD, query = "SELECT a FROM Authorization a where a.login = :"
                + Authorization.PARAMETER_LOGIN + " AND a.password = :" + Authorization.PARAMETER_PASSWORD) })
public class Authorization implements Serializable
{
    private static final long serialVersionUID = 1L;

    public static final String NQ_FIND_ALL = "Authorization.findAll";
    public static final String NQ_FIND_AUTHORIZATION_BY_LOGIN = "Authorization.findAllRoleByLogin";
    public static final String NQ_FIND_ROLE_BY_LOGIN_AND_PASSWORD = "Authorization.findRoleByLoginAndPassword";
    public static final String NQ_FIND_AUTHORIZATION_BY_LOGIN_AND_PASSWORD = "Authorization.findAuthorazationByLoginAndPassword";

    public static final String PARAMETER_LOGIN = "login";
    public static final String PARAMETER_PASSWORD = "password";

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

    /*
     * bi-directional many-to-many association to Role
     * @ManyToMany(mappedBy = "authorizations") private List<Role> roles;
     */

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
        this.password = Cryptor.getSecureString(password);
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

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + authorizationId;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Authorization other = (Authorization) obj;
        if (authorizationId != other.authorizationId)
            return false;
        if (email == null)
        {
            if (other.email != null)
                return false;
        }
        else if (!email.equals(other.email))
            return false;
        if (login == null)
        {
            if (other.login != null)
                return false;
        }
        else if (!login.equals(other.login))
            return false;
        if (password == null)
        {
            if (other.password != null)
                return false;
        }
        else if (!password.equals(other.password))
            return false;
        if (roles == null)
        {
            if (other.roles != null)
                return false;
        }
        else if (!roles.equals(other.roles))
            return false;
        if (worker == null)
        {
            if (other.worker != null)
                return false;
        }
        else if (!worker.equals(other.worker))
            return false;
        return true;
    }

}