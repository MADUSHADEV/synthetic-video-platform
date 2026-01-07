# Use the official Liquibase image (includes Java and PG drivers)
FROM liquibase/liquibase:4.27
LABEL authors="MadushaDev"
# Set the working directory inside the container
WORKDIR /liquibase/changelog

# Copy your local changelog files into the image
# This assumes your changelogs are in the same folder as this Dockerfile
COPY . .

# The official image uses a non-root user 'liquibase' by default.
# If you need to perform root actions, you can switch, but usually not needed.
# USER root