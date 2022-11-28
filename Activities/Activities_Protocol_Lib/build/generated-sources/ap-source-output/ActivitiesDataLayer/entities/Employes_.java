package ActivitiesDataLayer.entities;

import ActivitiesDataLayer.entities.Accessemployes;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-11-28T22:27:00")
@StaticMetamodel(Employes.class)
public class Employes_ { 

    public static volatile SingularAttribute<Employes, String> prenomEmploye;
    public static volatile SingularAttribute<Employes, String> password;
    public static volatile SingularAttribute<Employes, Integer> numeroEmploye;
    public static volatile CollectionAttribute<Employes, Accessemployes> accessemployesCollection;
    public static volatile SingularAttribute<Employes, String> nomEmploye;
    public static volatile SingularAttribute<Employes, String> email;

}