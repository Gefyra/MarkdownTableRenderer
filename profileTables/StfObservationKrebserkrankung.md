| Variable | Value |
|----------|-------|
| Name     | StfObservationKrebserkrankung |
| URL      | http://gematik.de/fhir/oegd/stf/StructureDefinition/StfObservationKrebserkrankung |
| Type     | Observation |


| ID        | Type      | Min  | Max  | Pattern   | Fixed    | must-support| VS-Url      | Strength    | VS Concepts |
|-----------|-----------|------|------|-----------|----------|-------------|-------------|-------------|-------------|
| Observation.code | CodeableConcept | 1 | 1 | {"coding":[{"system":"http://snomed.info/sct","code":"108369006"}]} | N/A | false | http://gematik.de/fhir/oegd/stf/ValueSet/StfObservationCodes | required | Tumor, Nicht natürlicher Tod, Klassifikation der Todesursache |
| Observation.value[x] | CodeableConcept | 0 | 1 | N/A | N/A | false | http://gematik.de/fhir/oegd/stf/ValueSet/StfJaNeinUnbekanntVS | extensible | Unknown, Ja, Nein |
