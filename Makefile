default: compile run

compile:
	mkdir -p ./target
	javac -cp .:./target:./lib/junit.jar -d target -sourcepath src src/*.java

run:
	java -cp .:./target/:./lib/junit.jar Backend

clean:
	rm -rf ./target
