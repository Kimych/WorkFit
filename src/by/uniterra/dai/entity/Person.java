/**
 * Filename  : Person.java
 *
 * * ***************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 ***************************************************************************
 *
 * Author     : Anton Nedbailo
 *
 * last change by : $Author:$ 
 * last check in  : $Date: $
 */

package by.uniterra.dai.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import by.uniterra.dai.iface.IPerson;

/**
 * The persistent class for the person database table.
 * 
 */
@Entity
@Table(name = "person")
@NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p")
public class Person implements Serializable, IPerson
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERSON_ID", unique = true, nullable = false)
    private int personId;

    @Column(name = "`COMMENT`", length = 256)
    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_OF_BIRTH", nullable = false)
    private Date dateOfBirth;

    @Column(name = "FIRST_NAME", nullable = false, length = 45)
    private String firstName;

    @Column(name = "OWNER_UNIQUE_NUMBER", nullable = false, length = 45)
    private String ownerUniqueNumber;

    @Column(name = "SECOND_NAME", nullable = false, length = 45)
    private String secondName;

    @Column(name = "THIRD_NAME", length = 45)
    private String thirdName;
    
    @Column(name = "SEX", nullable = false)
    private int sex;

    public Person()
    {
        comment = "";
        dateOfBirth = new Date(0);
        firstName = "";
        secondName = "";
        thirdName = "";
        ownerUniqueNumber = "";
    }

    /** (non-Javadoc)
     * @see by.uniterra.armparis.dai.iface.IPerson#getPersonId()
     */
    @Override
    public int getPersonId()
    {
        return this.personId;
    }

    /** (non-Javadoc)
     * @see by.uniterra.armparis.dai.iface.IPerson#setPersonId(int)
     */
    @Override
    public void setPersonId(int personId)
    {
        this.personId = personId;
    }

    /** (non-Javadoc)
     * @see by.uniterra.armparis.dai.iface.IPerson#getComment()
     */
    @Override
    public String getComment()
    {
        return this.comment;
    }

    /** (non-Javadoc)
     * @see by.uniterra.armparis.dai.iface.IPerson#setComment(java.lang.String)
     */
    @Override
    public void setComment(String comment)
    {
        this.comment = comment;
    }

    /** (non-Javadoc)
     * @see by.uniterra.armparis.dai.iface.IPerson#getDateOfBirth()
     */
    @Override
    public Date getDateOfBirth()
    {
        return this.dateOfBirth;
    }

    /** (non-Javadoc)
     * @see by.uniterra.armparis.dai.iface.IPerson#setDateOfBirth(java.util.Date)
     */
    @Override
    public void setDateOfBirth(Date dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    /** (non-Javadoc)
     * @see by.uniterra.armparis.dai.iface.IPerson#getFirstName()
     */
    @Override
    public String getFirstName()
    {
        return this.firstName;
    }

    /** (non-Javadoc)
     * @see by.uniterra.armparis.dai.iface.IPerson#setFirstName(java.lang.String)
     */
    @Override
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /** (non-Javadoc)
     * @see by.uniterra.armparis.dai.iface.IPerson#getOwnerUniqueNumber()
     */
    @Override
    public String getOwnerUniqueNumber()
    {
        return this.ownerUniqueNumber;
    }

    /** (non-Javadoc)
     * @see by.uniterra.armparis.dai.iface.IPerson#setOwnerUniqueNumber(java.lang.String)
     */
    @Override
    public void setOwnerUniqueNumber(String ownerUniqueNumber)
    {
        this.ownerUniqueNumber = ownerUniqueNumber;
    }

    /** (non-Javadoc)
     * @see by.uniterra.armparis.dai.iface.IPerson#getSecondName()
     */
    @Override
    public String getSecondName()
    {
        return this.secondName;
    }

    /** (non-Javadoc)
     * @see by.uniterra.armparis.dai.iface.IPerson#setSecondName(java.lang.String)
     */
    @Override
    public void setSecondName(String secondName)
    {
        this.secondName = secondName;
    }

    /** (non-Javadoc)
     * @see by.uniterra.armparis.dai.iface.IPerson#getThirdName()
     */
    @Override
    public String getThirdName()
    {
        return this.thirdName;
    }

    /** (non-Javadoc)
     * @see by.uniterra.armparis.dai.iface.IPerson#setThirdName(java.lang.String)
     */
    @Override
    public void setThirdName(String thirdName)
    {
        this.thirdName = thirdName;
    }
    
    /**
     * @see by.uniterra.armparis.dai.iface.IPerson#getSex()
     */
    @Override
    public int getSex()
    {
        return this.sex;
    }

    /**
     * @see by.uniterra.armparis.dai.iface.IPerson#setSex(java.lang.String)
     */
    @Override
    public void setSex(int sex)
    {
        this.sex = sex;
    }
    
   
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.comment == null) ? 0 : this.comment.hashCode());
        result = prime * result + ((this.dateOfBirth == null) ? 0 : this.dateOfBirth.hashCode());
        result = prime * result + ((this.firstName == null) ? 0 : this.firstName.hashCode());
        result = prime * result + ((this.ownerUniqueNumber == null) ? 0 : this.ownerUniqueNumber.hashCode());
        result = prime * result + this.personId;
        result = prime * result + ((this.secondName == null) ? 0 : this.secondName.hashCode());
        result = prime * result + this.sex;
        result = prime * result + ((this.thirdName == null) ? 0 : this.thirdName.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Person))
        {
            return false;
        }
        Person other = (Person) obj;
        if (this.comment == null)
        {
            if (other.comment != null)
            {
                return false;
            }
        } else if (!this.comment.equals(other.comment))
        {
            return false;
        }
        if (this.dateOfBirth == null)
        {
            if (other.dateOfBirth != null)
            {
                return false;
            }
        } else if (!this.dateOfBirth.equals(other.dateOfBirth))
        {
            return false;
        }
        if (this.firstName == null)
        {
            if (other.firstName != null)
            {
                return false;
            }
        } else if (!this.firstName.equals(other.firstName))
        {
            return false;
        }
        if (this.ownerUniqueNumber == null)
        {
            if (other.ownerUniqueNumber != null)
            {
                return false;
            }
        } else if (!this.ownerUniqueNumber.equals(other.ownerUniqueNumber))
        {
            return false;
        }
        if (this.personId != other.personId)
        {
            return false;
        }
        if (this.secondName == null)
        {
            if (other.secondName != null)
            {
                return false;
            }
        } else if (!this.secondName.equals(other.secondName))
        {
            return false;
        }
        if (this.sex != other.sex)
        {
            return false;
        }
        if (this.thirdName == null)
        {
            if (other.thirdName != null)
            {
                return false;
            }
        } else if (!this.thirdName.equals(other.thirdName))
        {
            return false;
        }
        return true;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return getFullFIO();
    }
    
    /**
     * Get full FIO like "Ivanov Ivan Ivanovich"
     * @param pPerson - IPerson data
     * 
     * @return String object with full FIO
     *
     * @author Anton Nedbailo
     * @date Oct 8, 2013
     */
    public String getFullFIO()
    {
        String strResult = "";
        if (!secondName.isEmpty())
        {
            strResult += secondName;            
        }
        if (!firstName.isEmpty())
        {
            strResult += strResult.isEmpty() ? "" : " ";
            strResult += firstName;            
        }
        if (!thirdName.isEmpty())
        {
            strResult += strResult.isEmpty() ? "" : " ";
            strResult += thirdName;            
        }
        return strResult;
    }
}