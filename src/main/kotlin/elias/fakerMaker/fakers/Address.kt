package elias.fakerMaker.fakers

object Address {
    data class AddressObj(
        val abbreviation: String,
        val fullName: String
    )

    val streetSuffixes = listOf(
        AddressObj("St.", "Street"),        // Most common for main roads
        AddressObj("Ave.", "Avenue"),       // Often larger/grander streets
        AddressObj("Rd.", "Road"),          // Common for longer routes
        AddressObj("Dr.", "Drive"),         // Popular in suburban areas
        AddressObj("Ln.", "Lane"),          // Usually smaller residential
        AddressObj("Ct.", "Court"),         // Typically dead-end/cul-de-sac
        AddressObj("Cir.", "Circle"),       // Roads that loop back
        AddressObj("Blvd.", "Boulevard"),   // Wide, major thoroughfares
        AddressObj("Way", "Way"),           // Short connecting roads
        AddressObj("Pl.", "Place"),         // Often short residential
        AddressObj("Trl.", "Trail"),        // Common in rural/scenic areas
        AddressObj("Pkwy.", "Parkway"),     // Wide roads, often with median
        AddressObj("Sq.", "Square"),        // Usually around plaza/park
        AddressObj("Ter.", "Terrace"),      // Often on hillsides
        AddressObj("Pt.", "Point"),         // Usually near water/vista
        AddressObj("Aly.", "Alley"),        // Narrow urban passages
        AddressObj("Arc.", "Arcade"),       // Shopping/commercial areas
        AddressObj("Bnd.", "Bend"),         // Curved roads
        AddressObj("Brg.", "Bridge"),       // Over water/ravines
        AddressObj("Byp.", "Bypass"),       // Routes around city centers
        AddressObj("Cswy.", "Causeway"),    // Raised roads, often over water
        AddressObj("Cres.", "Crescent"),    // Curved, usually upscale
        AddressObj("Xing.", "Crossing"),    // Often at intersections
        AddressObj("Cv.", "Cove"),          // Small, sheltered roads
        AddressObj("Est.", "Estate"),       // Upscale developments
        AddressObj("Expy.", "Expressway"),  // Major highway
        AddressObj("Ext.", "Extension"),    // Continuation of another road
        AddressObj("Fwy.", "Freeway"),      // Major highway
        AddressObj("Grn.", "Green"),        // Near parks/open spaces
        AddressObj("Hbr.", "Harbor"),       // Near water
        AddressObj("Hvn.", "Haven"),        // Quiet residential
        AddressObj("Hwy.", "Highway"),      // Major route
        AddressObj("Isle", "Isle"),         // Near water
        AddressObj("Mdw.", "Meadow"),       // Open areas
        AddressObj("Mall", "Mall"),         // Shopping areas
        AddressObj("Mnr.", "Manor"),        // Upscale areas
        AddressObj("Oval", "Oval"),         // Circular/egg-shaped
        AddressObj("Pass", "Pass"),         // Mountain/hill areas
        AddressObj("Path", "Path"),         // Walking/bike friendly
        AddressObj("Pike", "Pike"),         // Major route, often toll
        AddressObj("Plz.", "Plaza"),        // Commercial/urban squares
        AddressObj("Rdg.", "Ridge"),        // Along high ground
        AddressObj("Row", "Row"),           // Urban, historic areas
        AddressObj("Run", "Run"),           // Often near streams
        AddressObj("Spur", "Spur"),         // Branch off main road
        AddressObj("Tpke.", "Turnpike"),    // Major toll road
        AddressObj("Vale", "Vale"),         // Valley areas
        AddressObj("Vw.", "View"),          // Scenic areas
        AddressObj("Vlg.", "Village"),      // Small community areas
        AddressObj("Val.", "Valley"),      // Neighborhood roads
        AddressObj("Wkwy.", "Walkway"),     // Pedestrian areas
        AddressObj("Walk", "Walk"),         // Pedestrian areas
        AddressObj("Whrf.", "Wharf")        // Waterfront areas
    )

    val cardinalDirections = listOf(
        AddressObj("N.", "North"),
        AddressObj("S.", "South"),
        AddressObj("E.", "East"),
        AddressObj("W.", "West"),
        AddressObj("N.E.", "Northeast"),
        AddressObj("N.W.", "Northwest"),
        AddressObj("S.E.", "Southeast"),
        AddressObj("S.W.", "Southwest")
    )

    val address2 = listOf(
        // Basic unit designators
        AddressObj("Fl.", "Floor"),
        AddressObj("Unit", "Unit"),
        AddressObj("Apt.", "Apartment"),
        AddressObj("Ste.", "Suite"),
        AddressObj("Box", "Box"),
        // Numbered variations
        AddressObj("Bldg.", "Building"),
        AddressObj("Rm.", "Room"),
        AddressObj("Off.", "Office"),
        AddressObj("Sp.", "Space"),
        AddressObj("Bay", "Bay"),
        AddressObj("Lt.", "Lot"),
        // Residential specific
        AddressObj("Apt.", "Apartment"),
        AddressObj("Flat", "Flat"),
        AddressObj("Stu.", "Studio"),
        AddressObj("Condo.", "Condominium"),
        AddressObj("Res.", "Residence"),
        AddressObj("Bsmt.", "Basement"),
        AddressObj("Upr.", "Upper"),
        AddressObj("Lwr.", "Lower"),
        // Commercial specific
        AddressObj("Dept.", "Department"),
        AddressObj("Sec.", "Section"),
        AddressObj("Sta.", "Station"),
        AddressObj("Bth.", "Booth"),
        AddressObj("Ksk.", "Kiosk"),
        AddressObj("Whse.", "Warehouse"),
        AddressObj("Lab.", "Laboratory"),
        AddressObj("Wkshp.", "Workshop"),
        // Postal specific
        AddressObj("P.O. Box", "Post Office Box"),
        AddressObj("MS", "Mail Stop"),
        AddressObj("MB", "Mail Box"),
        AddressObj("MR", "Mailroom"),
        AddressObj("Lkr.", "Locker"),
        // Directional/Location specific
        AddressObj("Wng.", "Wing"),
        AddressObj("Blk.", "Block"),
        AddressObj("Twr.", "Tower"),
        AddressObj("Lvl.", "Level"),
        AddressObj("Gar.", "Garage"),
        AddressObj("Anx.", "Annex"),
        AddressObj("Term.", "Terminal"),
        AddressObj("Pr.", "Pier"),
        AddressObj("Dk.", "Dock"),
        // Academic/Institutional
        AddressObj("Hall", "Hall"),
        AddressObj("Drm.", "Dormitory"),
        AddressObj("Cmns.", "Commons"),
        AddressObj("Qtr.", "Quarter"),
        // Simple designators
        AddressObj("#", "Number"),
        AddressObj("No.", "Number"),
        AddressObj("Ste.", "Suite"),
        AddressObj("Rm.", "Room")
    )

    val treeNames = listOf(
        // Common Trees
        "Maple",
        "Oak",
        "Pine",
        "Elm",
        "Birch",
        "Willow",
        "Cedar",
        "Hickory",
        "Walnut",
        "Cypress",
        "Sycamore",
        "Aspen",
        "Chestnut",
        "Magnolia",
        "Poplar",
        "Redwood",
        "Spruce",
        "Cherry",
        "Beech",
        "Pecan",
        "Juniper",
        "Dogwood",
        "Sequoia",
        "Palm",
        "Alder",
        "Ash",
        "Catalpa",
        "Eucalyptus",
        "Fig",
        "Ginkgo",
        "Hawthorn",
        "Larch",
        "Linden",
        "Locust",
        "Mulberry",
        "Peach",
        "Pear",
        "Plum",
        "Sassafras",
        "Tamarack",
        "Teak",
        "Tulip",
        "Yew",
        "Acacia",
        "Banyan",
        "Cottonwood",
        "Ironwood",
        "Mahogany",
        "Bamboo",
        // Unusual & Exotic Trees
        "Baobab",
        "Rubber",
        "Mangrove",
        "Kapok",
        "Monkey Puzzle",
        "Dragon Blood",
        "Ghost Gum",
        "Rainbow Eucalyptus",
        "Bottlebrush",
        "Breadfruit",
        "Camphor",
        "Candlenut",
        "Coconut",
        "Coral",
        "Jacaranda",
        "Koa",
        "Mimosa",
        "Neem",
        "Pawpaw",
        "Persimmon",
        "Quandong",
        "Sandbox",
        "Soursop",
        "Thunder God",
        "Umbrella",
        "Zelkova",
        "Buckeye",
        "Butternut",
        "Jackfruit",
        "Katsura",
        "Balsa",
        "Cacao",
        "Calabash",
        "Durian",
        "Floss Silk",
        "Franklinia",
        "Maidenhair",
        "Paper Bark",
        "Sausage",
        "Silk Cotton",
        "Sugar Apple",
        "Wollemi",
        "Yellow Wood",
        "Zebrawood"
    )

    val landmarkTerms = listOf(
        AddressObj("Mill", "Mill"),              // Mills typically don't get abbreviated
        AddressObj("Brg.", "Bridge"),
        AddressObj("Sta.", "Station"),
        AddressObj("Mkt.", "Market"),
        AddressObj("Ch.", "Church"),
        AddressObj("Pk.", "Park"),
        AddressObj("Plz.", "Plaza"),
        AddressObj("Sq.", "Square"),
        AddressObj("Ft.", "Fort"),
        AddressObj("Fty.", "Factory"),
        AddressObj("Lh.", "Lighthouse"),
        AddressObj("Ct.", "Courthouse"),
        AddressObj("Lib.", "Library"),
        AddressObj("Acad.", "Academy"),
        AddressObj("Arc.", "Arcade"),
        AddressObj("Aud.", "Auditorium"),
        AddressObj("Bank", "Bank"),              // Banks typically don't get abbreviated
        AddressObj("Bwy.", "Broadway"),
        AddressObj("Cap.", "Capitol"),
        AddressObj("Ctr.", "Center"),
        AddressObj("City H.", "City Hall"),
        AddressObj("Clg.", "College"),
        AddressObj("Comm. Ctr.", "Community Center"),
        AddressObj("Depot", "Depot"),            // Depot typically isn't abbreviated
        AddressObj("Docks", "Docks"),            // Docks typically isn't abbreviated
        AddressObj("Exch.", "Exchange"),
        AddressObj("Fld.", "Field"),
        AddressObj("Fndry.", "Foundry"),
        AddressObj("Gate", "Gate"),              // Gate typically isn't abbreviated
        AddressObj("Gdn.", "Garden"),
        AddressObj("Gym", "Gymnasium"),
        AddressObj("Hall", "Hall"),              // Hall typically isn't abbreviated
        AddressObj("Hosp.", "Hospital"),
        AddressObj("Inn", "Inn"),                // Inn typically isn't abbreviated
        AddressObj("Inst.", "Institute"),
        AddressObj("Jct.", "Junction"),
        AddressObj("Lab.", "Laboratory"),
        AddressObj("Lndg.", "Landing"),
        AddressObj("Mall", "Mall"),              // Mall typically isn't abbreviated
        AddressObj("Msm.", "Museum"),
        AddressObj("Ofc.", "Office"),
        AddressObj("Pav.", "Pavilion"),
        AddressObj("Pier", "Pier"),              // Pier typically isn't abbreviated
        AddressObj("Port", "Port"),              // Port typically isn't abbreviated
        AddressObj("Rec. Ctr.", "Recreation Center"),
        AddressObj("Sem.", "Seminary"),
        AddressObj("Shp. Ctr.", "Shopping Center"),
        AddressObj("Std.", "Stadium"),
        AddressObj("Tav.", "Tavern"),
        AddressObj("Term.", "Terminal"),
        AddressObj("Thtr.", "Theater"),
        AddressObj("Twr.", "Tower"),
        AddressObj("Univ.", "University"),
        AddressObj("Whf.", "Wharf"),
    )


    val terrainFeatures = listOf(
        AddressObj("Clf.", "Cliff"),
        AddressObj("Mesa", "Mesa"),
        AddressObj("Rdg.", "Ridge"),
        AddressObj("Rck.", "Rock"),
        AddressObj("Spg.", "Spring"),
        AddressObj("Vly.", "Valley"),
        AddressObj("Mtn.", "Mountain"),
        AddressObj("Peak", "Peak"),
        AddressObj("Cyn.", "Canyon"),
        AddressObj("Hl.", "Hill"),
        AddressObj("Knl.", "Knoll"),
        AddressObj("Mdw.", "Meadow"),
        AddressObj("Plt.", "Plateau"),
        AddressObj("Rpr.", "Rapids"),
        AddressObj("Gln.", "Glen"),
        AddressObj("Grg.", "Gorge"),
        AddressObj("Pln.", "Plain"),
        AddressObj("Blf.", "Bluff"),
        AddressObj("Crst.", "Crest"),
        AddressObj("Dell", "Dell"),
        AddressObj("Dne.", "Dune"),
        AddressObj("Fld.", "Field"),
        AddressObj("Flat", "Flat"),
        AddressObj("Frk.", "Fork"),
        AddressObj("Gap", "Gap"),
        AddressObj("Glch.", "Gulch"),
        AddressObj("Hgld.", "Highland"),
        AddressObj("Hlnd.", "Highlands"),
        AddressObj("Hllw.", "Hollow"),
        AddressObj("Isle", "Isle"),
        AddressObj("Ldg.", "Landing"),
        AddressObj("Lk.", "Lake"),
        AddressObj("Lagn.", "Lagoon"),
        AddressObj("Low.", "Lowland"),
        AddressObj("Mrsh.", "Marsh"),
        AddressObj("Oasis", "Oasis"),
        AddressObj("Pass", "Pass"),
        AddressObj("Pnt.", "Point"),
        AddressObj("Prt.", "Prairie"),
        AddressObj("Qry.", "Quarry"),
        AddressObj("Rvn.", "Ravine"),
        AddressObj("Rf.", "Reef"),
        AddressObj("Res.", "Reservoir"),
        AddressObj("Slp.", "Slope"),
        AddressObj("Smt.", "Summit"),
        AddressObj("Ter.", "Terrace"),
        AddressObj("Trch.", "Trench"),
        AddressObj("Vale", "Vale"),
        AddressObj("Vst.", "Vista"),
        AddressObj("Wsh.", "Wash"),
        AddressObj("Wtrfl.", "Waterfall"),
        AddressObj("Basin", "Basin"),
        AddressObj("Bay", "Bay"),
        AddressObj("Bch.", "Beach"),
        AddressObj("Bog", "Bog"),
        AddressObj("Cape", "Cape"),
        AddressObj("Cave", "Cave"),
        AddressObj("Cove", "Cove"),
        AddressObj("Delta", "Delta"),
        AddressObj("Dwns.", "Downs"),
        AddressObj("Grdn.", "Garden")
    )

    val gemTerms = listOf(
        "Ruby",
        "Jade",
        "Gold",
        "Silver",
        "Pearl",
        "Amber",
        "Diamond",
        "Emerald",
        "Sapphire",
        "Opal",
        "Topaz",
        "Garnet",
        "Onyx",
        "Quartz",
        "Agate",
        "Crystal",
        "Platinum",
        "Bronze",
        "Copper",
        "Iron",
        "Jasper",
        "Lapis",
        "Malachite",
        "Marble",
        "Obsidian",
        "Peridot",
        "Turquoise",
        "Amethyst",
        "Aquamarine",
        "Moonstone",
        "Granite",
        "Cobalt"
    )

    val pleasantRoadAdjectives = listOf(
        "Happy",
        "Pleasant",
        "Peaceful",
        "Sunny",
        "Rolling",
        "Golden",
        "Emerald",
        "Shady",
        "Crystal",
        "Gentle",
        "Quiet",
        "Sweet",
        "Tranquil",
        "Serene",
        "Meadow",
        "Green",
        "Silver",
        "Spring",
        "Summer",
        "Autumn",
        "Winter",
        "Hidden",
        "Misty",
        "Fair",
        "Graceful",
        "Bright",
        "Paradise",
        "Garden",
        "Harmony",
        "Royal",
        "Noble",
        "Grand",
        "Majestic",
        "Scenic",
        "Sunrise",
        "Sunset"
    )

}