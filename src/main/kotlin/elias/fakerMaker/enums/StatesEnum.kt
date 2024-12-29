package elias.fakerMaker.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class StatesEnum(val fullName: String) {
    @SerialName("AK") AK("Alaska"),
    @SerialName("AL") AL("Alabama"),
    @SerialName("AZ") AZ("Arizona"),
    @SerialName("AR") AR("Arkansas"),
    @SerialName("CA") CA("California"),
    @SerialName("CO") CO("Colorado"),
    @SerialName("CT") CT("Connecticut"),
    @SerialName("DE") DE("Delaware"),
    @SerialName("FL") FL("Florida"),
    @SerialName("GA") GA("Georgia"),
    @SerialName("HI") HI("Hawaii"),
    @SerialName("ID") ID("Idaho"),
    @SerialName("IL") IL("Illinois"),
    @SerialName("IN") IN("Indiana"),
    @SerialName("IA") IA("Iowa"),
    @SerialName("KS") KS("Kansas"),
    @SerialName("KY") KY("Kentucky"),
    @SerialName("LA") LA("Louisiana"),
    @SerialName("ME") ME("Maine"),
    @SerialName("MD") MD("Maryland"),
    @SerialName("MA") MA("Massachusetts"),
    @SerialName("MI") MI("Michigan"),
    @SerialName("MN") MN("Minnesota"),
    @SerialName("MS") MS("Mississippi"),
    @SerialName("MO") MO("Missouri"),
    @SerialName("MT") MT("Montana"),
    @SerialName("NE") NE("Nebraska"),
    @SerialName("NV") NV("Nevada"),
    @SerialName("NH") NH("New Hampshire"),
    @SerialName("NJ") NJ("New Jersey"),
    @SerialName("NM") NM("New Mexico"),
    @SerialName("NY") NY("New York"),
    @SerialName("NC") NC("North Carolina"),
    @SerialName("ND") ND("North Dakota"),
    @SerialName("OH") OH("Ohio"),
    @SerialName("OK") OK("Oklahoma"),
    @SerialName("OR") OR("Oregon"),
    @SerialName("PA") PA("Pennsylvania"),
    @SerialName("RI") RI("Rhode Island"),
    @SerialName("SC") SC("South Carolina"),
    @SerialName("SD") SD("South Dakota"),
    @SerialName("TN") TN("Tennessee"),
    @SerialName("TX") TX("Texas"),
    @SerialName("UT") UT("Utah"),
    @SerialName("VT") VT("Vermont"),
    @SerialName("VA") VA("Virginia"),
    @SerialName("WA") WA("Washington"),
    @SerialName("WV") WV("West Virginia"),
    @SerialName("WI") WI("Wisconsin"),
    @SerialName("WY") WY("Wyoming"),

    // Additional territories and special cases
    @SerialName("AA") AA("Armed Forces Americas"),
    @SerialName("AE") AE("Armed Forces Africa, Europe, Middle East and Canada"),
    @SerialName("AP") AP("Armed Forces Pacific"),
    @SerialName("AS") AS("American Samoa"),
    @SerialName("DC") DC("District of Columbia"),
    @SerialName("FM") FM("Federated States of Micronesia"),
    @SerialName("GU") GU("Guam"),
    @SerialName("MH") MH("Marshall Islands (Hawaii)"),
    @SerialName("MP") MP("Northern Mariana Islands (Marianas Pacific)"),
    @SerialName("PR") PR("Puerto Rico"),
    @SerialName("PW") PW("Palau"),
    @SerialName("VI") VI("Virgin Islands"),
    @SerialName("STATE") state("Weird edge case in postal data");

}