FROM amazoncorretto:23
COPY . /app
EXPOSE 10101
WORKDIR /app/out/artifacts/ServerChat_jar
CMD java -jar ServerChat.jar