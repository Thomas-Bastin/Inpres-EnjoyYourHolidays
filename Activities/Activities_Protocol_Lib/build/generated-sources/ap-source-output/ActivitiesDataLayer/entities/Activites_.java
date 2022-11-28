package ActivitiesDataLayer.entities;

import ActivitiesDataLayer.entities.Reservationactivite;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-11-28T22:27:00")
@StaticMetamodel(Activites.class)
public class Activites_ { 

    public static volatile SingularAttribute<Activites, Integer> nombreMaxParticipants;
    public static volatile SingularAttribute<Activites, Date> dateDebut;
    public static volatile SingularAttribute<Activites, Integer> idActivite;
    public static volatile SingularAttribute<Activites, Integer> nombreParticipantsInscrits;
    public static volatile SingularAttribute<Activites, String> typeActivite;
    public static volatile SingularAttribute<Activites, Integer> dureeActivite;
    public static volatile SingularAttribute<Activites, Float> prixHTVA;
    public static volatile CollectionAttribute<Activites, Reservationactivite> reservationactiviteCollection;

}