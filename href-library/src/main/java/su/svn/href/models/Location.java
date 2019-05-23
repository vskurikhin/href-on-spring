package su.svn.href.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import su.svn.utils.StringHelper;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table("locations")
public class Location
{
    static final long serialVersionUID = -30L;

    @Id
    @Column("location_id")
    private Long id;

    @Column("street_address")
    private String streetAddress;

    @Column("postal_code")
    private String postalCode;

    @Column("city")
    private String city = "";

    @Column("state_province")
    private String stateProvince;

    @Column("country_id")
    private String countryId;

    public static boolean isValidId(Long id)
    {
        return null != id && id > 0;
    }

    public static boolean isValidFieldName(String name)
    {
        return StringHelper.isValidFieldName(name, Location.class);
    }

    public static boolean isValidStreetAddress(String name)
    {
        return StringHelper.isValidValue(name);
    }

    public static boolean isValidPostalCode(String name)
    {
        return StringHelper.isValidValue(name);
    }

    public static boolean isValidCity(String name)
    {
        return StringHelper.isValidValue(name);
    }

    public static boolean isValidStateProvince(String name)
    {
        return StringHelper.isValidValue(name);
    }

    public static class Builder
    {
        private Long id;
        private String streetAddress;
        private String postalCode;
        private String city;
        private String stateProvince;
        private String countryId;

        private Builder() { /* None */ }

        public Builder setId(Long id)
        {
            this.id = id;
            return this;
        }

        public Builder setStreetAddress(String streetAddress)
        {
            this.streetAddress = streetAddress;
            return this;
        }

        public Builder setPostalCode(String postalCode)
        {
            this.postalCode = postalCode;
            return this;
        }

        public Builder setCity(String city)
        {
            this.city = city;
            return this;
        }

        public Builder setStateProvince(String stateProvince)
        {
            this.stateProvince = stateProvince;
            return this;
        }

        public Builder setCountryId(String countryId)
        {
            this.countryId = countryId;
            return this;
        }

        public Location build()
        {
            return new Location(id, streetAddress, postalCode, city, stateProvince, countryId);
        }
    }

    public static Builder builder()
    {
        return new Builder();
    }
}
