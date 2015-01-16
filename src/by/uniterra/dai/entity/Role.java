package by.uniterra.dai.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = Role.NQ_FIND_ALL, query = "SELECT r FROM Role r"),
        @NamedQuery(name = Role.NQ_FIND_ROLE_BY_NAME, query = "SELECT r FROM Role r WHERE r.name = :" + Role.PARAMETER_ROLE_NAME) })
public class Role implements Serializable
{
    private static final long serialVersionUID = 1L;
    public static final String NQ_FIND_ALL = "Role.findAll";
    public static final String NQ_FIND_ROLE_BY_NAME = "Role.findRoleByName";

    public static final String PARAMETER_ROLE_NAME = "roleName";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "ROLE_ID", updatable = true, insertable = true)
    @Column(name = "ROLE_ID")
    private int roleId;

    private String description;

    private String name;

    // bi-directional many-to-many association to Role
    @ManyToMany(mappedBy = "roles")
    private List<Authorization> authorizations;;

    public Role()
    {
    }

    public int getRoleId()
    {
        return this.roleId;
    }

    public void setRoleId(int roleId)
    {
        this.roleId = roleId;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Authorization> getAuthorizations()
    {
        return this.authorizations;
    }

    public void setAuthorizations(List<Authorization> authorizations)
    {
        this.authorizations = authorizations;
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((authorizations == null) ? 0 : authorizations.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + roleId;
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
        Role other = (Role) obj;
        if (authorizations == null)
        {
            if (other.authorizations != null)
                return false;
        }
        else if (!authorizations.equals(other.authorizations))
            return false;
        if (description == null)
        {
            if (other.description != null)
                return false;
        }
        else if (!description.equals(other.description))
            return false;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (roleId != other.roleId)
            return false;
        return true;
    }

}