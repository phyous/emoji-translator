cd ..
mvn install
cd scripts
java -jar ../target/training-1.0-SNAPSHOT.jar ../training/emoji/emoji_mapping.csv ../training/english/moby_thesaurus.csv ../training/english/moby_part_of_speech.csv ../../emoji-server/src/main/resources/word_to_emoji.csv