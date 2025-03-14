# Questionnaire Processor

## Overview

The `QuestionnaireProcessor` class provides methods to load a FHIR Questionnaire from a file, collect questions from the questionnaire, and process various components of the questionnaire. This tool is useful for handling FHIR Questionnaires and extracting relevant information for further processing or analysis.

## Usage

### Building the Project

To build the project and generate a runnable JAR with all dependencies, use the following Maven command:

```sh
mvn clean package
```

### Running the Processor

To run the `QuestionnaireProcessor`, you need to provide the path to the FHIR Questionnaire file as a command-line argument.

```sh
java -cp target/<your-jar-file-with-dependencies>.jar questionnairerenderer.QuestionnaireProcessor <path_to_questionnaire_file>
```

### Example

```sh
java -cp target/QuestionnaireMarkdownTableRenderer-1.0-SNAPSHOT-jar-with-dependencies.jar questionnairerenderer.QuestionnaireProcessor SEU-Elternbefragung-BW.json
```

### Output

The processor will generate two outputs:
1. A Markdown table printed to the console.
2. A CSV file saved in the current directory with the extracted questions.

### CSV File

The CSV file will contain the following columns:
- Link-ID
- Text
- Typ
- Enable When
- Wertemenge
- Herkunft
- Nutzung

## License

This project is licensed under the Apache License 2.0. See the `LICENSE` file for details.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.

## Contact

For any questions or support, please contact pw@gefyra.de
