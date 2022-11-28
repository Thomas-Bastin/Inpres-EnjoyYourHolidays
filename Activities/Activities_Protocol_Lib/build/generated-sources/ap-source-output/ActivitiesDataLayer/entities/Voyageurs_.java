package ActivitiesDataLayer.entities;

import ActivitiesDataLayer.entities.Reservationactivite;
import ActivitiesDataLayer.entities.Voyageurs;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-11-28T22:27:00")
@StaticMetamodel(Voyageurs.class)
public class Voyageurs_ { 

    public static volatile SingularAttribute<Voyageurs, String> nationalite;
    public static volatile SingularAttribute<Voyageurs, Date> dateNaissance;
    public static volatile SingularAttribute<Voyageurs, String> commune;
    public static volatile SingularAttribute<Voyageurs, Integer> codePostal;
    public static volatile SingularAttribute<Voyageurs, String> nomVoyageur;
    public static volatile SingularAttribute<Voyageurs, String> nomRue;
    public static volatile SingularAttribute<Voyageurs, Integer> numHabitation;
    public static volatile SingularAttribute<Voyageurs, String> prenomVoyageur;
    public static volatile SingularAttribute<Voyageurs, Integer> numeroClient;
    public static volatile CollectionAttribute<Voyageurs, Voyageurs> voyageursCollection;
    public static volatile SingularAttribute<Voyageurs, Voyageurs> voyageurReferent;
    public static volatile CollectionAttribute<Voyageurs, Reservationactivite> reservationactiviteCollection;
    public static volatile SingularAttribute<Voyageurs, Integer> numBoiteHabitation;
    public static volatile SingularAttribute<Voyageurs, String> email;

}