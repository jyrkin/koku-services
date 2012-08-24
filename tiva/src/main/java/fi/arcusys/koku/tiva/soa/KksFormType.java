package fi.arcusys.koku.tiva.soa;

/**
 * Holds KKS form types (aka collection types, KKS codes)
 *
 * @author Mikhail Kapitonov (mikhail.kapitonov@arcusys.fi)
 *         08 24, 2012
 */
public class KksFormType {
    private String typeId;
    private String typeName;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
