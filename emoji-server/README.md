# emoji-server

To run:

1. Start server: java -jar target/server-1.0-SNAPSHOT.jar ./src/main/resources/word_to_emoji.csv
2. Get a translation: curl "http://0.0.0.0:8338/translate?text=foo"