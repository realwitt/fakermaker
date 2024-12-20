package elias.fakerMaker.fakers

object Murica {
    // todo: figure out how to use these to make valid addresses with zip codes...
    val states = listOf(
        "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
        "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD",
        "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
        "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
        "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"
    )

    val cities = listOf(
        // Alabama
        "Birmingham", "Montgomery", "Huntsville", "Mobile", "Tuscaloosa", "Hoover", "Dothan", "Auburn",
        "Decatur", "Madison", "Florence", "Vestavia Hills", "Phenix City", "Prattville", "Gadsden",
        "Alabaster", "Opelika", "Enterprise", "Bessemer", "Homewood",
        // Alaska
        "Anchorage", "Fairbanks", "Juneau", "Wasilla", "Sitka", "Kenai", "Kodiak", "Bethel",
        "Palmer", "Homer", "Unalaska", "Barrow", "Soldotna", "Valdez", "Nome", "Kotzebue",
        "Seward", "Wrangell", "Dillingham", "Petersburg",
        // Arizona
        "Phoenix", "Tucson", "Mesa", "Chandler", "Scottsdale", "Gilbert", "Glendale", "Tempe",
        "Peoria", "Surprise", "Yuma", "Flagstaff", "Goodyear", "Buckeye", "Casa Grande",
        "Lake Havasu City", "Maricopa", "Prescott", "Prescott Valley", "Sierra Vista",
        // Arkansas
        "Little Rock", "Fayetteville", "Fort Smith", "Springdale", "Jonesboro", "North Little Rock",
        "Conway", "Rogers", "Pine Bluff", "Bentonville", "Hot Springs", "Benton", "Texarkana",
        "Sherwood", "Jacksonville", "Russellville", "Bella Vista", "Cabot", "Van Buren", "Maumelle",
        // California
        "Los Angeles", "San Francisco", "San Diego", "Sacramento", "San Jose", "Fresno", "Oakland",
        "Long Beach", "Bakersfield", "Anaheim", "Santa Ana", "Riverside", "Stockton", "Irvine",
        "Modesto", "San Bernardino", "Berkeley", "Santa Clara", "Santa Barbara", "Pasadena",
        // Colorado
        "Denver", "Colorado Springs", "Aurora", "Fort Collins", "Lakewood", "Thornton", "Arvada",
        "Westminster", "Pueblo", "Centennial", "Boulder", "Greeley", "Longmont", "Loveland",
        "Grand Junction", "Broomfield", "Castle Rock", "Commerce City", "Parker", "Littleton",
        // Connecticut
        "Hartford", "New Haven", "Stamford", "Bridgeport", "Waterbury", "Norwalk", "Danbury",
        "New Britain", "West Hartford", "Greenwich", "Hamden", "Meriden", "Bristol", "Manchester",
        "West Haven", "Milford", "East Hartford", "Middletown", "Fairfield", "Stratford",
        // Delaware
        "Wilmington", "Dover", "Newark", "Middletown", "Smyrna", "Milford", "Seaford", "Georgetown",
        "Elsmere", "New Castle", "Camden", "Rehoboth Beach", "Lewes", "Laurel", "Harrington",
        "Milton", "Delaware City", "Newport", "Ocean View", "Bridgeville",
        // Florida
        "Miami", "Orlando", "Tampa", "Jacksonville", "St. Petersburg", "Hialeah", "Tallahassee",
        "Fort Lauderdale", "Port St. Lucie", "Cape Coral", "Pembroke Pines", "Hollywood",
        "Gainesville", "Miramar", "Coral Springs", "Clearwater", "Palm Bay", "Pompano Beach",
        "West Palm Beach", "Lakeland",
        // Georgia
        "Atlanta", "Augusta", "Columbus", "Macon", "Savannah", "Athens", "Sandy Springs", "Roswell",
        "Johns Creek", "Albany", "Warner Robins", "Alpharetta", "Marietta", "Valdosta", "Smyrna",
        "Dunwoody", "Rome", "East Point", "Milton", "Gainesville",
        // Hawaii
        "Honolulu", "East Honolulu", "Pearl City", "Hilo", "Kailua", "Waipahu", "Kaneohe", "Mililani Town",
        "Kahului", "Ewa Gentry", "Kihei", "Schofield Barracks", "Wahiawa", "Makakilo", "Wailuku",
        "Hawaii Kai", "Royal Kunia", "Halawa", "Ewa Beach", "Waianae",
        // Idaho
        "Boise", "Meridian", "Nampa", "Idaho Falls", "Pocatello", "Caldwell", "Coeur d'Alene", "Twin Falls",
        "Lewiston", "Post Falls", "Rexburg", "Moscow", "Eagle", "Mountain Home", "Ammon",
        "Kuna", "Chubbuck", "Garden City", "Hayden", "Jerome",
        // Illinois
        "Chicago", "Aurora", "Rockford", "Joliet", "Naperville", "Springfield", "Peoria", "Elgin",
        "Waukegan", "Cicero", "Champaign", "Bloomington", "Arlington Heights", "Evanston", "Decatur",
        "Schaumburg", "Bolingbrook", "Palatine", "Skokie", "Des Plaines",
        // Indiana
        "Indianapolis", "Fort Wayne", "Evansville", "South Bend", "Carmel", "Fishers", "Bloomington",
        "Hammond", "Gary", "Lafayette", "Muncie", "Terre Haute", "Kokomo", "Anderson", "Noblesville",
        "Greenwood", "Elkhart", "Mishawaka", "Lawrence", "Jeffersonville",
        // Iowa
        "Des Moines", "Cedar Rapids", "Davenport", "Sioux City", "Iowa City", "Waterloo", "Ames",
        "West Des Moines", "Council Bluffs", "Dubuque", "Ankeny", "Urbandale", "Cedar Falls",
        "Marion", "Bettendorf", "Mason City", "Clinton", "Burlington", "Fort Dodge", "Ottumwa",
        // Kansas
        "Wichita", "Overland Park", "Kansas City", "Olathe", "Shawnee", "Manhattan", "Lawrence",
        "Lenexa", "Salina", "Topeka", "Leawood", "Dodge City", "Garden City", "Junction City",
        "Emporia", "Prairie Village", "Gardner", "Liberal", "Derby", "Hays",
        // Kentucky
        "Louisville", "Lexington", "Bowling Green", "Owensboro", "Covington", "Richmond", "Georgetown",
        "Florence", "Henderson", "Hopkinsville", "Nicholasville", "Frankfort", "Jeffersontown",
        "Independence", "Paducah", "Radcliff", "Ashland", "Madisonville", "Winchester", "Erlanger",
        // Louisiana
        "New Orleans", "Baton Rouge", "Shreveport", "Lafayette", "Lake Charles", "Kenner", "Bossier City",
        "Monroe", "Alexandria", "New Iberia", "Houma", "Zachary", "Natchitoches", "Gretna",
        "Ruston", "Pineville", "Hammond", "Slidell", "Opelousas", "Sulphur",
        // Maine
        "Portland", "Lewiston", "Bangor", "South Portland", "Auburn", "Biddeford", "Sanford",
        "Brunswick", "Augusta", "Scarborough", "Saco", "Westbrook", "Waterville", "Windham",
        "Gorham", "York", "Falmouth", "Kennebunk", "Orono", "Presque Isle",
        // Maryland
        "Baltimore", "Frederick", "Rockville", "Gaithersburg", "Bowie", "Hagerstown", "Annapolis",
        "College Park", "Salisbury", "Laurel", "Greenbelt", "Cumberland", "Westminster", "Hyattsville",
        "Takoma Park", "Easton", "Cambridge", "Aberdeen", "Elkton", "Ocean City",
        // Massachusetts
        "Boston", "Worcester", "Springfield", "Lowell", "Cambridge", "New Bedford", "Brockton",
        "Quincy", "Lynn", "Fall River", "Newton", "Lawrence", "Somerville", "Framingham",
        "Haverhill", "Waltham", "Malden", "Brookline", "Plymouth", "Medford",
        // Michigan
        "Detroit", "Grand Rapids", "Warren", "Sterling Heights", "Ann Arbor", "Lansing", "Flint",
        "Dearborn", "Livonia", "Troy", "Westland", "Farmington Hills", "Kalamazoo", "Wyoming",
        "Rochester Hills", "Southfield", "Taylor", "Pontiac", "St. Clair Shores", "Royal Oak",
        // Minnesota
        "Minneapolis", "St. Paul", "Rochester", "Bloomington", "Duluth", "Brooklyn Park", "Plymouth",
        "Maple Grove", "Woodbury", "St. Cloud", "Eagan", "Eden Prairie", "Coon Rapids", "Burnsville",
        "Blaine", "Lakeville", "Minnetonka", "Apple Valley", "Edina", "St. Louis Park",
        // Mississippi
        "Jackson", "Gulfport", "Southaven", "Hattiesburg", "Biloxi", "Meridian", "Tupelo",
        "Greenville", "Olive Branch", "Horn Lake", "Clinton", "Pearl", "Madison", "Starkville",
        "Oxford", "Pascagoula", "Brandon", "Ridgeland", "Vicksburg", "Columbus",
        // Missouri
        "Kansas City", "St. Louis", "Springfield", "Columbia", "Independence", "Lee's Summit",
        "O'Fallon", "St. Joseph", "St. Charles", "St. Peters", "Blue Springs", "Florissant",
        "Joplin", "Chesterfield", "Jefferson City", "Cape Girardeau", "Wentzville", "Wildwood",
        "University City", "Liberty",
        // Montana
        "Billings", "Missoula", "Great Falls", "Bozeman", "Butte", "Helena", "Kalispell",
        "Havre", "Anaconda", "Miles City", "Belgrade", "Livingston", "Laurel", "Whitefish",
        "Lewistown", "Sidney", "Glendive", "Columbia Falls", "Polson", "Hamilton",
        // Nebraska
        "Omaha", "Lincoln", "Bellevue", "Grand Island", "Kearney", "Fremont", "Hastings",
        "North Platte", "Norfolk", "Columbus", "Papillion", "La Vista", "Scottsbluff", "South Sioux City",
        "Beatrice", "Lexington", "Gering", "Alliance", "Seward", "York",
        // Nevada
        "Las Vegas", "Henderson", "Reno", "North Las Vegas", "Sparks", "Carson City", "Fernley",
        "Elko", "Mesquite", "Boulder City", "Fallon", "Winnemucca", "West Wendover", "Ely",
        "Yerington", "Carlin", "Lovelock", "Wells", "Caliente", "Virginia City",
        // New Hampshire
        "Manchester", "Nashua", "Concord", "Dover", "Rochester", "Keene", "Portsmouth", "Laconia",
        "Lebanon", "Claremont", "Berlin", "Somersworth", "Franklin", "Exeter", "Newport",
        "Londonderry", "Durham", "Hampton", "Derry", "Salem",
        // New Jersey
        "Newark", "Jersey City", "Paterson", "Elizabeth", "Edison", "Woodbridge", "Lakewood",
        "Toms River", "Hamilton", "Trenton", "Clifton", "Camden", "Brick", "Cherry Hill",
        "Passaic", "Middletown", "Union City", "Old Bridge", "Gloucester Township", "East Orange",
        // New Mexico
        "Albuquerque", "Las Cruces", "Rio Rancho", "Santa Fe", "Roswell", "Farmington", "Clovis",
        "Hobbs", "Alamogordo", "Carlsbad", "Gallup", "Deming", "Los Lunas", "Chaparral",
        "Sunland Park", "Los Alamos", "Portales", "Artesia", "Silver City", "Lovington",
        // New York
        "New York City", "Buffalo", "Rochester", "Yonkers", "Syracuse", "Albany", "New Rochelle",
        "Mount Vernon", "Schenectady", "Utica", "White Plains", "Hempstead", "Troy", "Niagara Falls",
        "Binghamton", "Freeport", "Valley Stream", "Long Beach", "Rome", "North Tonawanda",
        // North Carolina
        "Charlotte", "Raleigh", "Greensboro", "Durham", "Winston-Salem", "Fayetteville", "Cary",
        "Wilmington", "High Point", "Greenville", "Asheville", "Concord", "Gastonia", "Jacksonville",
        "Chapel Hill", "Rocky Mount", "Burlington", "Wilson", "Huntersville", "Kannapolis",
        // North Dakota
        "Fargo", "Bismarck", "Grand Forks", "Minot", "West Fargo", "Williston", "Dickinson",
        "Mandan", "Jamestown", "Wahpeton", "Devils Lake", "Valley City", "Grafton", "Beulah",
        "Watford City", "Rugby", "Stanley", "New Town", "Casselton", "Lincoln",
        // Ohio
        "Columbus", "Cleveland", "Cincinnati", "Toledo", "Akron", "Dayton", "Parma", "Canton",
        "Youngstown", "Lorain", "Hamilton", "Springfield", "Kettering", "Elyria", "Lakewood",
        "Newark", "Cuyahoga Falls", "Dublin", "Euclid", "Middletown",
        // Oklahoma
        "Oklahoma City", "Tulsa", "Norman", "Broken Arrow", "Lawton", "Edmond", "Moore", "Midwest City",
        "Enid", "Stillwater", "Muskogee", "Bartlesville", "Owasso", "Ponca City", "Ardmore",
        "Duncan", "Del City", "Bixby", "Yukon", "Bethany",
        // Oregon
        "Portland", "Salem", "Eugene", "Gresham", "Hillsboro", "Beaverton", "Bend", "Medford",
        "Springfield", "Corvallis", "Albany", "Tigard", "Lake Oswego", "Keizer", "Grants Pass",
        "Oregon City", "McMinnville", "Redmond", "Tualatin", "West Linn",
        // Pennsylvania
        "Philadelphia", "Pittsburgh", "Allentown", "Erie", "Reading", "Scranton", "Bethlehem",
        "Lancaster", "Harrisburg", "Altoona", "York", "State College", "Wilkes-Barre", "Chester",
        "Norristown", "Bethel Park", "Williamsport", "Monroeville", "Plum", "Easton",
        // Rhode Island
        "Providence", "Warwick", "Cranston", "Pawtucket", "East Providence", "Woonsocket", "Newport",
        "Central Falls", "Westerly", "Cumberland", "West Warwick", "Johnston", "North Providence",
        "Bristol", "Portsmouth", "Narragansett", "Barrington", "Middletown", "Warren", "Lincoln",
        // South Carolina
        "Columbia", "Charleston", "North Charleston", "Mount Pleasant", "Rock Hill", "Greenville",
        "Summerville", "Sumter", "Hilton Head Island", "Florence", "Spartanburg", "Goose Creek",
        "Aiken", "Myrtle Beach", "Anderson", "Greer", "Greenwood", "North Augusta", "Easley", "Clemson",
        // South Dakota
        "Sioux Falls", "Rapid City", "Aberdeen", "Brookings", "Watertown", "Mitchell", "Yankton",
        "Pierre", "Huron", "Vermillion", "Brandon", "Box Elder", "Madison", "Sturgis", "Spearfish",
        "Dell Rapids", "Tea", "Harrisburg", "Belle Fourche", "Canton",
        // Tennessee
        "Nashville", "Memphis", "Knoxville", "Chattanooga", "Clarksville", "Murfreesboro", "Franklin",
        "Jackson", "Johnson City", "Bartlett", "Hendersonville", "Kingsport", "Collierville",
        "Cleveland", "Smyrna", "Germantown", "Brentwood", "Columbia", "Spring Hill", "Lebanon",
        // Texas
        "Houston", "San Antonio", "Dallas", "Austin", "Fort Worth", "El Paso", "Arlington", "Corpus Christi",
        "Plano", "Laredo", "Lubbock", "Garland", "Irving", "Amarillo", "Grand Prairie", "McKinney",
        "Frisco", "Brownsville", "Pasadena", "Mesquite",
        // Utah
        "Salt Lake City", "West Valley City", "Provo", "West Jordan", "Orem", "Sandy", "Ogden",
        "St. George", "Layton", "Taylorsville", "South Jordan", "Lehi", "Logan", "Murray",
        "Draper", "Bountiful", "Riverton", "Roy", "Spanish Fork", "Pleasant Grove",
        // Vermont
        "Burlington", "South Burlington", "Rutland", "Essex Junction", "Bennington", "Montpelier",
        "Barre", "Winooski", "St. Albans", "Newport", "Vergennes", "Middlebury", "Brattleboro",
        "Hartford", "Milton", "Essex", "Williston", "Stowe", "Springfield", "St. Johnsbury",
        // Virginia
        "Virginia Beach", "Norfolk", "Chesapeake", "Richmond", "Newport News", "Alexandria", "Hampton",
        "Roanoke", "Portsmouth", "Suffolk", "Lynchburg", "Harrisonburg", "Leesburg", "Charlottesville",
        "Danville", "Blacksburg", "Manassas", "Petersburg", "Winchester", "Salem",
        // Washington
        "Seattle", "Spokane", "Tacoma", "Vancouver", "Bellevue", "Kent", "Everett", "Renton",
        "Yakima", "Federal Way", "Spokane Valley", "Bellingham", "Kennewick", "Auburn", "Pasco",
        "Marysville", "Lakewood", "Redmond", "Shoreline", "Richland",
        // West Virginia
        "Charleston", "Huntington", "Morgantown", "Parkersburg", "Wheeling", "Weirton", "Fairmont",
        "Beckley", "Martinsburg", "Clarksburg", "South Charleston", "St. Albans", "Vienna",
        "Bluefield", "Bridgeport", "Dunbar", "Oak Hill", "Elkins", "Moundsville", "Princeton",
        // Wisconsin
        "Milwaukee", "Madison", "Green Bay", "Kenosha", "Racine", "Appleton", "Waukesha", "Oshkosh",
        "Eau Claire", "Janesville", "West Allis", "La Crosse", "Sheboygan", "Wauwatosa", "Fond du Lac",
        "New Berlin", "Wausau", "Brookfield", "Beloit", "Greenfield",
        // Wyoming
        "Cheyenne", "Casper", "Laramie", "Gillette", "Rock Springs", "Sheridan", "Green River",
        "Evanston", "Riverton", "Jackson", "Cody", "Rawlins", "Douglas", "Powell", "Torrington",
        "Lander", "Newcastle", "Worland", "Buffalo", "Thermopolis"
    )
}