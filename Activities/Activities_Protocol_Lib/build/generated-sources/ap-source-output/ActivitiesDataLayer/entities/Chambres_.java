package ActivitiesDataLayer.entities;

import ActivitiesDataLayer.entities.ChambresPK;
import ActivitiesDataLayer.entities.Complexes;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-11-28T22:27:00")
@StaticMetamodel(Chambres.class)
public class Chambres_ { 

    public static volatile SingularAttribute<Chambres, ChambresPK> chambresPK;
    public static volatile SingularAttribute<Chambres, String> equipements;
    public static volatile SingularAttribute<Chambres, Float> prixHTVA;
    public static volatile SingularAttribute<Chambres, Integer> nombreLits;
    public static volatile SingularAttribute<Chambres, Complexes> complexes;

}