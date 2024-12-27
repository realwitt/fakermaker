package elias.fakerMaker.fakers.videogames

object CallOfDuty {
    val guns = listOf(
        // Assault Rifles
        "AK-47", "M16", "M4A1", "FAMAS", "Galil", "SCAR-H", "FAL", "ACR", "AUG",
        "G36C", "M8A1", "Type 95", "Remington R5", "HBRa3", "BAL-27", "ICR-1",
        "KN-44", "Man-O-War", "STG44", "Volk", "NV4", "Kilo 141", "CR-56 AMAX",
        // SMGs
        "MP5", "UMP45", "P90", "Vector", "MP7", "Skorpion", "MAC-10", "AK-74u",
        "PPSh-41", "Thompson", "Type 100", "MP40", "Uzi", "Milano 821", "LC10",
        // Shotguns
        "KSG", "SPAS-12", "Model 1887", "M1014", "AA-12", "R9-0", "JAK-12",
        "Origin 12", "725", "Model 680", "Echo", "Streetsweeper",
        // LMGs
        "RPD", "M60", "PKM", "MG42", "M240", "L86 LSW", "LSAT", "HAMR",
        "Stoner 63", "RPK", "Bruen MK9", "FiNN LMG", "Holger-26",
        // Sniper Rifles
        "Intervention", "Barrett .50cal", "M40A3", "L96A1", "MSR", "DSR 50",
        "Ballista", "SVG-100", "HDR", "AX-50", "Pelington 703", "LW3-Tundra",
        // Pistols
        "Desert Eagle", "M1911", ".44 Magnum", "USP .45", "G18", "Python",
        "B23R", "RK5", "Makarov", "Sykov", "Renetti", "Diamatti",
        // Launchers
        "RPG-7", "JOKR", "Strela-P", "PILA", "M72 LAW", "Cigma 2",
        // Special
        "Crossbow", "Ballistic Knife", "Combat Knife", "Riot Shield",
        "China Lake", "M79 'Thumper'", "War Machine"
    )

    val maps = listOf(
        // Modern Warfare (2019) / Warzone Maps
        "Shoot House", "Shipment", "Verdansk", "Caldera", "Al-Raab Airbase",
        "Rust", "Crash", "Vacant", "Broadcast", "Hackney Yard", "Gun Runner",
        "Azhir Cave", "St. Petrograd", "Rammaza", "Piccadilly", "Atlas Superstore",
        // Black Ops Series - Multiplayer
        "Nuketown", "Firing Range", "Summit", "Jungle", "Slums",
        "Raid", "Standoff", "Hijacked", "Express", "Array",
        "Crisis", "Grid", "Launch", "Radiation", "Villa",
        "Havana", "Hotel", "Hanoi", "Cracked", "WMD",
        // Black Ops Series - Zombies
        "Nacht der Untoten", "Der Riese", "Kino der Toten", "Five",
        "Ascension", "Call of the Dead", "Shangri-La", "Moon",
        "TranZit", "Town", "Farm", "Bus Depot", "Die Rise",
        "Mob of the Dead", "Buried", "Origins", "Shadows of Evil",
        "Der Eisendrache", "Zetsubou No Shima", "Gorod Krovi",
        "Revelations", "IX", "Voyage of Despair", "Blood of the Dead",
        "Classified", "Dead of the Night", "Ancient Evil",
        // Modern Warfare 2 Maps
        "Afghan", "Derail", "Estate", "Favela", "Highrise",
        "Invasion", "Karachi", "Quarry", "Rundown", "Scrapyard",
        "Skidrow", "Sub Base", "Terminal", "Underpass", "Wasteland",
        // Modern Warfare 3 Maps
        "Arkaden", "Bakaara", "Bootleg", "Carbon", "Dome",
        "Downturn", "Fallen", "Hardhat", "Interchange", "Lockdown",
        "Mission", "Outpost", "Resistance", "Seatown", "Underground",
        "Village",
        // World at War Maps
        "Castle", "Courtyard", "Makin", "Roundhouse", "Seelow",
        "Upheaval", "Asylum", "Cliffside", "Dome", "Downfall",
        "Hangar", "Knee Deep", "Outskirts", "Station", "Airfield",
        // Call of Duty 4: Modern Warfare Maps
        "Ambush", "Backlot", "Bloc", "Bog", "Countdown",
        "Crash", "Crossfire", "District", "Downpour", "Overgrown",
        "Pipeline", "Shipment", "Showdown", "Strike", "Vacant",
        "Wet Work",
        // Advanced Warfare Maps
        "Ascend", "Bio Lab", "Comeback", "Defender", "Detroit",
        "Greenband", "Horizon", "Instinct", "Recovery", "Retreat",
        "Solar", "Terrace", "Atlas Gorge",
        // Infinite Warfare Maps
        "Frontier", "Frost", "Genesis", "Breakout", "Grounded",
        "Mayday", "Permafrost", "Precinct", "Retaliation", "Scorch",
        "Skydock", "Terminal", "Throwback",
        // WWII Maps
        "Ardennes Forest", "Carentan", "USS Texas", "Gibraltar",
        "London Docks", "Pointe du Hoc", "Saint Marie du Mont",
        "Sainte Marie du Mont", "Flak Tower", "Gustav Cannon",
        // Cold War Maps
        "Armada", "Cartel", "Checkmate", "Crossroads", "Garrison",
        "Miami", "Moscow", "Satellite", "The Pines", "Raid",
        "Express", "Apocalypse", "Diesel", "Yamantau", "Standoff",
        // Vanguard Maps
        "Berlin", "Bocage", "Castle", "Das Haus", "Decoy",
        "Desert Siege", "Dome", "Eagle's Nest", "Gavutu", "Hotel Royal",
        "Numa Numa", "Oasis", "Paradise", "Red Star", "Sub Pens",
        "Tuscan",
        // DLC Maps from various games
        "Creek", "Broadcast", "Chinatown", "Killhouse", "Storm",
        "Salvage", "Stimulus", "Bailout", "Carnival", "Fuel",
        "Storm", "Strike", "Trailer Park", "Liberation", "Piazza",
        "Overwatch", "Black Box", "Sanctuary", "Foundation", "Getaway",
        "Off Shore", "Decommission", "Offshore", "Parish", "Erosion",
        "Aground", "Boardwalk", "Gulch", "Decommission", "Departed"
    )

    val zombiesPerks = listOf(
        // Original Perks
        "Juggernog", "Quick Revive", "Speed Cola", "Double Tap Root Beer",
        "Stamin-Up", "PhD Flopper", "Deadshot Daiquiri", "Mule Kick",
        // Black Ops 2+ Perks
        "Tombstone Soda", "Who's Who", "Vulture Aid", "Widow's Wine",
        "Electric Cherry", "Der Wunderfizz",
        // Black Ops 4 Perks
        "Winter's Wail", "Dying Wish", "Victorious Tortoise", "Blood Wolf Bite",
        "Timeslip", "PhD Slider", "Ethereal Razor", "Bandolier Bandit",
        "Secret Sauce", "Death Perception",
        // Cold War Perks
        "Elemental Pop", "Death Perception", "Tombstone Soda",
        // Vanguard Perks
        "Diabolical Damage", "Fiendish Fortitude", "Demonic Frenzy",
        "Venomous Vigor", "Corrupted Creation"
    )

    val multiplayerPerks = listOf(
        // Tier 1 / Red Perks
        "Scavenger", "Double Time", "Kill Chain", "Quick Fix", "E.O.D.",
        "Marathon", "Lightweight", "Sleight of Hand", "Blind Eye",
        "Hardline", "Flak Jacket", "Tactical Mask", "Engineer",
        "Fast Hands", "Overkill", "Strong Arm", "Recon",
        // Tier 2 / Blue Perks
        "High Alert", "Ghost", "Restock", "Pointman", "Hardened",
        "Toughness", "Cold Blooded", "Fast Recovery", "Assassin",
        "Gung-Ho", "Scavenger", "Tracker", "Spotter", "Overclock",
        "Peripherals", "Forward Intel", "Focus",
        // Tier 3 / Yellow Perks
        "Amped", "Battle Hardened", "Tune Up", "Shrapnel", "Ninja",
        "Commando", "Steady Aim", "Sitrep", "Dead Silence",
        "Awareness", "Combat Scout", "Scavenger", "Stalker",
        "Marksman", "Strategist", "Scrambler", "Hacker",
        // Pro Perks (MW2/MW3 era)
        "Sleight of Hand Pro", "Stopping Power Pro", "Marathon Pro",
        "Lightweight Pro", "Hardline Pro", "Cold-Blooded Pro",
        "Ninja Pro", "Commando Pro", "Steady Aim Pro",
        "Scavenger Pro", "Danger Close Pro", "One Man Army Pro",
        // Wildcards & Upgrades
        "Perk Greed", "Perk 1 Greed", "Perk 2 Greed", "Perk 3 Greed",
        "Law Breaker", "Gunfighter", "Danger Close"
    )

    val killstreaks = listOf(
        // Classic Streaks
        "UAV", "Counter UAV", "Care Package", "Sentry Gun",
        "Predator Missile", "Precision Airstrike", "Harrier Strike",
        "Attack Helicopter", "Pave Low", "AC130", "Chopper Gunner",
        "Attack Dogs", "Tactical Nuke", "Emergency Airdrop",

        // Modern Streaks
        "VTOL Jet", "White Phosphorus", "Juggernaut", "Gunship",
        "Advanced UAV", "EMP Systems", "Support Helo", "Wheelson",
        "Cruise Missile", "Cluster Strike", "Wilson", "Shield Turret",

        // Scorestreaks
        "Hellstorm Missile", "Lightning Strike", "Death Machine",
        "War Machine", "RCXD", "Dragon Fire", "Swarm", "Lodestar",
        "K9 Unit", "RAPS", "H.A.T.R.", "Power Core", "Mothership",
        "Thresher", "Warthog", "Rolling Thunder"
    )

    val equipment = listOf(
        // Lethals
        "Frag Grenade", "Semtex", "C4", "Claymore", "Bouncing Betty",
        "Thermite", "Molotov Cocktail", "Throwing Knife", "Combat Axe",
        "Proximity Mine", "Trip Mine", "Sticky Grenade",

        // Tacticals
        "Flash Grenade", "Stun Grenade", "Smoke Grenade", "EMP Grenade",
        "Concussion Grenade", "Gas Grenade", "Snapshot Grenade",
        "Heartbeat Sensor", "Decoy Grenade", "Trophy System",
        "Tactical Insertion", "Nova Gas", "Scrambler",

        // Field Equipment
        "Ammo Box", "Dead Silence", "Trophy System", "Deployable Cover",
        "Tactical Insert", "Field Mic", "Jammer", "SAM Turret"
    )

    val wonderWeapons = listOf(
        "Ray Gun", "Ray Gun Mark II", "Ray Gun Mark III",
        "Thundergun", "Winter's Howl", "Wunderwaffe DG-2",
        "Wave Gun", "Scavenger", "V-R11", "31-79 JGb215",
        "Blundergat", "Paralyzer", "Staff of Ice", "Staff of Fire",
        "Staff of Lightning", "Staff of Wind", "Apothicon Servant",
        "Wrath of the Ancients", "KT-4", "GKZ-45 Mk3",
        "Alistair's Folly", "Kraken", "Tundragun", "D.I.E. Shockwave",
        "R.A.I. K-84", "CRBR-S"
    )

    val fieldUpgrades = listOf(
        "Dead Silence", "Trophy System", "Tactical Insert",
        "Munitions Box", "Deployable Cover", "Stopping Power Rounds",
        "Recon Drone", "Field Mic", "Assault Pack", "Armor Plates",
        "Tactical Camera", "Supply Box", "Battle Rage", "Anti-Armor Rounds",
        "Portable Radar", "Weapon Drop", "Field Mic", "Jammer"
    )

    val gameModes = listOf(
        // Standard Modes
        "Team Deathmatch", "Free-For-All", "Domination", "Search and Destroy",
        "Hardpoint", "Headquarters", "Kill Confirmed", "Control",
        "Capture the Flag", "Ground War", "Cyber Attack", "Sabotage",
        // Party Modes
        "Gun Game", "One in the Chamber", "Sticks and Stones", "Prop Hunt",
        "Infected", "All or Nothing", "Sharp Shooter",
        // Zombies Modes
        "Survival", "Grief", "TranZit", "Turned", "Onslaught",
        "Outbreak", "Cranked", "Jingle Hells",
        // Limited Time Modes
        "Warzone Rumble", "Plunder", "Blood Money", "Payload",
        "Clash", "Rebirth Resurgence", "Iron Trials"
    )

    val camos = listOf(
        // Base Camos
        "Gold", "Diamond", "Damascus", "Dark Matter", "Atomic",
        "Chrome", "Exclusion Zone", "Royalty", "Dark Aether",
        "Obsidian", "Platinum", "Solar", "Black Sky", "Orion",
        // Challenge Camos
        "Tiger", "Dragon", "Splinter", "Topo", "Stripes",
        "Reptile", "Skulls", "Flora", "Digital", "Geometric",
        // Seasonal/Special Camos
        "Cherry Blossom", "Art of War", "Mastery", "Completionist",
        "Pan-Asian", "Dragon Scale", "Golden Viper", "Plague Diamond",
        "Zombie Gold", "Red Tiger", "Blue Tiger", "Fall", "Ice"
    )

    val operators = listOf(
        // Task Force 141
        "Captain Price", "Soap MacTavish", "Ghost", "Gaz", "Roach",
        // Other Notable Characters
        "Alex Mason", "Frank Woods", "Jason Hudson", "Viktor Reznov",
        "Raul Menendez", "Imran Zakhaev", "Vladimir Makarov",
        // Modern Warfare Operators
        "Mara", "Yegor", "Thorne", "Minotaur", "Krueger",
        "Domino", "Raines", "Syd", "Otter", "Wyatt", "Alice",
        "Rodion", "Golem", "Grinch", "Zane", "Bale",
        // Black Ops Operators
        "Adler", "Park", "Woods", "Song", "Garcia", "Baker",
        "Powers", "Hunter", "Stone", "Vargas", "Portnova",
    )

    val factions = listOf(
        "Marines", "SAS", "Spetsnaz", "OpFor", "Militia",
        "CIA", "MI6", "Delta Force", "Shadow Company", "Allegiance",
        "Coalition", "Warsaw Pact", "NATO"
    )

    val attachments = listOf(
        // Optics
        "Red Dot Sight", "Holographic Sight", "ACOG Scope",
        "Thermal Scope", "Sniper Scope", "Hybrid Sight",
        "Viper Reflex Sight", "Scout Combat Optic",
        // Barrels
        "Long Barrel", "Heavy Barrel", "Lightweight Barrel",
        "Monolithic Suppressor", "Tactical Suppressor",
        "Compensator", "Muzzle Brake", "Flash Guard",
        // Stocks
        "No Stock", "CQB Stock", "Tactical Stock", "Heavy Stock",
        "Skeleton Stock", "FSS Close Quarters Stock",
        "FORGE TAC Stalker", "FTAC Hunter",
        // Underbarrel
        "Foregrip", "Angled Grip", "Vertical Grip", "Commando Foregrip",
        "Merc Foregrip", "Ranger Foregrip", "Operator Foregrip",
        "M203 Grenade Launcher", "Shotgun", "Bipod",
        // Magazines
        "Extended Mags", "Fast Mags", "Drum Mags", "Dual Mags",
        "STANAG Mags", "Sleight of Hand", "FMJ", "Hollow Point",
        // Rear Grip
        "Stippled Grip Tape", "Granulated Grip Tape",
        "Rubberized Grip Tape", "XTEN Grip"
    )

}