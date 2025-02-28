package il.cshaifasweng.OCSFMediatorExample.entities;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Request.class)
public abstract class Request_ {
    public static volatile SingularAttribute<Request, RequestStatus> status;
}
