package miyu.kms.utils;


/**
 * @author Oliver
 * @date 2022/4/15
 */
public class X500NameInfo {
    private String commonName;

    private String localityName;

    private String stateOrProvinceName;

    private String organizationName;

    private String organizationalUnitName;

    private String countryName;

    private String streetAddress;

    private String emailAddress;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (commonName != null) {
            builder.append("CN=").append(commonName);
        }
        if (organizationName != null) {
            builder.append(",O=").append(organizationName);
        }
        if (organizationalUnitName != null) {
            builder.append(",OU=").append(organizationalUnitName);
        }
        if (localityName != null) {
            builder.append(",L=").append(localityName);
        }
        if (stateOrProvinceName != null) {
            builder.append(",ST=").append(stateOrProvinceName);
        }
        if (countryName != null) {
            builder.append(",C=").append(countryName);
        }
        if (streetAddress != null) {
            builder.append(",STREET=").append(streetAddress);
        }
        if (streetAddress != null) {
            builder.append(",emailAddress=").append(emailAddress);
        }
        return builder.toString();
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getLocalityName() {
        return localityName;
    }

    public void setLocalityName(String localityName) {
        this.localityName = localityName;
    }

    public String getStateOrProvinceName() {
        return stateOrProvinceName;
    }

    public void setStateOrProvinceName(String stateOrProvinceName) {
        this.stateOrProvinceName = stateOrProvinceName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationalUnitName() {
        return organizationalUnitName;
    }

    public void setOrganizationalUnitName(String organizationalUnitName) {
        this.organizationalUnitName = organizationalUnitName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
