package fi.arcusys.koku.tiva.soa;

/**
 * Status of the consent.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
public enum ConsentStatus {
    Open, PartiallyGiven, Valid, Expired, Revoked, Declined;
}
