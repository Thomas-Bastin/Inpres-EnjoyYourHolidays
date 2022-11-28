package ActivitiesDataLayer.entities;

import ActivitiesDataLayer.entities.Activites;
import ActivitiesDataLayer.entities.Voyageurs;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-11-28T22:27:00")
@StaticMetamodel(Reservationactivite.class)
public class Reservationactivite_ { 

    public static volatile SingularAttribute<Reservationactivite, Activites> refActivite;
    public static volatile SingularAttribute<Reservationactivite, String> numReservation;
    public static volatile SingularAttribute<Reservationactivite, Character> paye;
    public static volatile SingularAttribute<Reservationactivite, Integer> nombreParticipant;
    public static volatile SingularAttribute<Reservationactivite, Voyageurs> voyageurTitulaire;

}