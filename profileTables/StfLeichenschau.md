| Variable | Value |
|----------|-------|
| Name     | StfLeichenschau |
| URL      | http://gematik.de/fhir/oegd/stf/StructureDefinition/StfLeichenschau |
| Type     | Procedure |


| ID        | Type      | Min  | Max  | Pattern   | Fixed    | must-support| VS-Url      | Strength    | VS Concepts |
|-----------|-----------|------|------|-----------|----------|-------------|-------------|-------------|-------------|
| Procedure.status | code | 1 | 1 | N/A | N/A | true | http://hl7.org/fhir/ValueSet/event-status | required | Preparation, Entered in Error, Completed, In Progress, Not Done, Stopped, Unknown, On Hold |
| Procedure.code | CodeableConcept | 0 | 1 | {"coding":[{"system":"http://snomed.info/sct","code":"29240004"}]} | N/A | true | http://hl7.org/fhir/ValueSet/procedure-code | example |  |
| Procedure.performed[x] | dateTime | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Procedure.performer.actor | Reference | 1 | 1 | N/A | N/A | true | N/A | N/A | N/A |
