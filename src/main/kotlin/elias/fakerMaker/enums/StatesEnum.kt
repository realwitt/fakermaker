package elias.fakerMaker.enums

enum class StatesEnum(val fullName: String) {
    AK("Alaska"),
    AL("Alabama"),
    AZ("Arizona"),
    AR("Arkansas"),
    CA("California"),
    CO("Colorado"),
    CT("Connecticut"),
    DE("Delaware"),
    FL("Florida"),
    GA("Georgia"),
    HI("Hawaii"),
    ID("Idaho"),
    IL("Illinois"),
    IN("Indiana"),
    IA("Iowa"),
    KS("Kansas"),
    KY("Kentucky"),
    LA("Louisiana"),
    ME("Maine"),
    MD("Maryland"),
    MA("Massachusetts"),
    MI("Michigan"),
    MN("Minnesota"),
    MS("Mississippi"),
    MO("Missouri"),
    MT("Montana"),
    NE("Nebraska"),
    NV("Nevada"),
    NH("New Hampshire"),
    NJ("New Jersey"),
    NM("New Mexico"),
    NY("New York"),
    NC("North Carolina"),
    ND("North Dakota"),
    OH("Ohio"),
    OK("Oklahoma"),
    OR("Oregon"),
    PA("Pennsylvania"),
    RI("Rhode Island"),
    SC("South Carolina"),
    SD("South Dakota"),
    TN("Tennessee"),
    TX("Texas"),
    UT("Utah"),
    VT("Vermont"),
    VA("Virginia"),
    WA("Washington"),
    WV("West Virginia"),
    WI("Wisconsin"),
    WY("Wyoming"),

    // weird ones
    AA("Armed Forces Americas"),
    AE("Armed Forces Africa, Europe, Middle East and Canada"),
    AP("Armed Forces Pacific"),
    AS("American Samoa"),
    DC("District of Columbia"),
    FM("Federated States of Micronesia"),
    GU("Guam"),
    MH("Marshall Islands (Hawaii)"),
    MP("Northern Mariana Islands (Marianas Pacific)"),
    PR("Puerto Rico"),
    PW("Palau"),
    VI("Virgin Islands"),
    state("weird edge case present in postal data... it's filtered out")
}