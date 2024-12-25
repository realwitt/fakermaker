# FakerMaker 🚀
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/badge/kotlin-2.0.20-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Build Status](https://img.shields.io/github/actions/workflow/status/yourusername/faker-maker/main.yml?branch=main)](https://github.com/yourusername/faker-maker/actions)
[![GitHub release](https://img.shields.io/github/v/release/yourusername/faker-maker?include_prereleases)](https://github.com/yourusername/faker-maker/releases)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://makeapullrequest.com)

A blazingly fast fake data generator inspired by Java Faker, but supercharged with multi-threading to handle generating 1,000,000+ rows of realistic fake data in seconds.

Generate data using characters and locations from your favorite universes - including Pokemon trainers, Breaking Bad characters, Lord of the Rings locations, or Game of Thrones houses. Mix and match data from dozens of popular TV shows, movies, books, and video games to create the perfect test dataset.

---
> ### 🌟 Coming Soon: Web Interface
> A sleek, modern web app to generate and visualize your fake data with just a few clicks.
> Stay tuned for point-and-click fake data generation.
---

## ⚡️ Setup TL;DR

```bash
# One command to rule them all
curl -s "https://get.sdkman.io" | bash && source "$HOME/.sdkman/bin/sdkman-init.sh" && \
sdk install java 21-graal && sdk use java 21-graal && \
sdk install kotlin 2.0.20 && sdk use kotlin 2.0.20 && \
git clone https://github.com/yourusername/faker-maker.git && cd faker-maker && \
docker-compose up -d && \
./gradlew build && ./gradlew bootRun
```

Generate a million rows of fake data *(will generate ~80mb file):*
```bash
curl -X POST "http://localhost:8181/api/fakermaker/download/1000000" -o massive_fake_dataset.csv
```

<details>
<summary>📋 Step-by-Step Setup Instructions</summary>

## Prerequisites

- Java 21
- Kotlin 2.0.20
- Docker and Docker Compose
- Gradle

## Detailed Installation

### Using SDKMAN! (Recommended)

```bash
# Install SDKMAN!
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"

# Install Java
sdk install java 21-graal
sdk use java 21-graal

# Install Kotlin
sdk install kotlin 2.0.20
sdk use kotlin 2.0.20
```

### Project Setup

1. Clone and enter the repository:
```bash
git clone https://github.com/yourusername/faker-maker.git
cd faker-maker
```

2. Start PostgreSQL:
```bash
docker-compose up -d
```

3. Build and run:
```bash
./gradlew build
./gradlew bootRun
```
</details>

## 🚀 Features

- **Blazing Fast**: Generate millions of rows in seconds using Kotlin coroutines
- **Media-Rich Data**: Characters and locations from popular franchises like Harry Potter, Breaking Bad, Pokemon, and more

## 🎮 Usage

### Generate CSV Data
```bash
# 1000 records
curl -X POST "http://localhost:8181/api/fakermaker/download/1000" -o fake_data.csv

# A million records
curl -X POST "http://localhost:8181/api/fakermaker/download/1000000" -o massive_dataset.csv
```

### Generate Table Data
```bash
curl -X POST "http://localhost:8181/api/fakermaker/dataTable/100"
```

## 🔧 Configuration

Edit `src/main/resources/application.properties`:
```properties
server.port=8181
spring.application.name=fakerMaker
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
```

## 🧪 Development

### Testing in IntelliJ IDEA

Use the included `.idea/httpRequests/faker-maker.http` file for easy API testing.

## 🤝 Contributing

1. Fork it
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

---
Made with ☕️ and Kotlin