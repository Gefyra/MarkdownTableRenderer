| Variable | Value |
|----------|-------|
| Name     | StfDatei |
| URL      | http://gematik.de/fhir/oegd/stf/StructureDefinition/StfDateien |
| Type     | DocumentReference |


| ID        | Type      | Min  | Max  | Pattern   | Fixed    | must-support| VS-Url      | Strength    | VS Concepts |
|-----------|-----------|------|------|-----------|----------|-------------|-------------|-------------|-------------|
| DocumentReference.identifier | Identifier | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
| DocumentReference.status | code | 1 | 1 | N/A | N/A | true | http://hl7.org/fhir/ValueSet/document-reference-status | required | Current, Superseded, Entered in Error |
| DocumentReference.type | CodeableConcept | 0 | 1 | N/A | N/A | true | http://gematik.de/fhir/oegd/stf/ValueSet/StfDateiTypVS | extensible | Sterbeurkunde, Obduktionsschein |
| DocumentReference.subject | Reference | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| DocumentReference.date | instant | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| DocumentReference.author | Reference | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
| DocumentReference.context | BackboneElement | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| DocumentReference.context.related | Reference | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
