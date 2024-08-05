package com.beautysalon.beautysalonsystem.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity(name="addressEntity")
@Table(name="address_tbl")
public class Address extends Base{
    @Id
    @SequenceGenerator(name = "addressSeq", sequenceName = "address_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addressSeq")

    @Column(name = "id")
    private Long id;

    @Column(name = "country",length =30 ,nullable = false )
//    @Pattern(regexp = "^[a-zA-Z\\s]{5,30}$",message = "Invalid Country Name")
    private String countryName;

    @Column(name = "state",length =30,nullable = false )
//    @Pattern(regexp = "^[a-zA-Z\\s]{5,30}$",message = "Invalid State Name")
    private String stateName;

    @Column(name = "city",length = 30)
//    @Pattern(regexp = "^[a-zA-Z\\s]{5,30}$",message = "Invalid City Name")
    private String cityName;

    @Column(name = "village",length = 30)
//    @Pattern(regexp = "^[a-zA-Z\\s]{5,30}$",message = "Invalid Village Name")
    private String villageName;

    @Column(name = "region",length = 30)
//    @Pattern(regexp = "^[a-zA-Z0-9\\s]{5,30}$",message = "Invalid Region Name")
    private String regionName;

    @Column(name = "street",length =150)
//    @Pattern(regexp = "^[a-zA-Z0-9()&@$_\\-\\s]{5,150}$",message = "Invalid Street Name")
    private String streetName;

    @Column(name = "plates",length =5)
//    @Pattern(regexp = "^[A-Z0-9&_\\-]{5}$",message = "Invalid Plates Number")
    private String platesNumber;

    @Column(name = "floor",length =5)
//    @Pattern(regexp = "^[0-9]{5}$",message = "Invalid Floor Number")
    private String floorNumber;

    @Column(name = "home_unit",length =5)
//    @Pattern(regexp = "^[0-9]{5}$",message = "Invalid Home Unit")
    private String homeUnit;

    @Column(name = "postalCode",length = 10, unique = true,nullable = false)
//    @Pattern(regexp = "^[0-9]{10}$",message = "Invalid Postal Code")
    private String postalCode;

    public String getFullAddress (){
        return String.format("%s - %s - %s - %s - %s - %s - %s - %s - %s - %s",  countryName,stateName,cityName,villageName,regionName,streetName,platesNumber,floorNumber,homeUnit,postalCode);
    }
}
