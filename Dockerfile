# Étape 1: Base JDK 19 (utiliser une image basée sur Debian)
FROM openjdk:19-slim-buster AS jdk-base

# Installer wget et unzip
RUN apt-get update \
    && apt-get install -y wget unzip \
    && rm -rf /var/lib/apt/lists/*

# Installer Gradle
ENV GRADLE_VERSION 8.2
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /tmp \
    && unzip -d /opt/gradle /tmp/gradle-${GRADLE_VERSION}-bin.zip \
    && ln -s /opt/gradle/gradle-${GRADLE_VERSION} /opt/gradle/latest \
    && rm /tmp/gradle-${GRADLE_VERSION}-bin.zip
ENV GRADLE_HOME /opt/gradle/latest
ENV PATH $PATH:$GRADLE_HOME/bin

# Copier le code source dans le conteneur et construire
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

# Étape 2: Exécution de l'application
FROM openjdk:19-slim-buster
COPY --from=jdk-base /home/gradle/src/build/libs/*.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
