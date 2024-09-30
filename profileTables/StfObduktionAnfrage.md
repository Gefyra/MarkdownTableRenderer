| Variable | Value |
|----------|-------|
| Name     | StfObduktionAnfrage |
| URL      | http://gematik.de/fhir/oegd/stf/StructureDefinition/StfObduktionAnfrage |
| Type     | ServiceRequest |


| ID        | Type      | Min  | Max  | Pattern   | Fixed    | must-support| VS-Url      | Strength    | VS Concepts |
|-----------|-----------|------|------|-----------|----------|-------------|-------------|-------------|-------------|
| ServiceRequest.status | code | 1 | 1 | N/A | N/A | true | http://hl7.org/fhir/ValueSet/request-status | required | Revoked, Entered in Error, Draft, Completed, On Hold, Unknown, Active |
| ServiceRequest.intent | code | 1 | 1 | N/A | N/A | true | http://hl7.org/fhir/ValueSet/request-intent | required | Reflex Order, Original Order, Plan, Option, Directive, Proposal, Order, Filler Order, Instance Order |
| ServiceRequest.code | CodeableConcept | 0 | 1 | {"coding":[{"system":"http://snomed.info/sct","code":"29240004"}]} | N/A | true | http://hl7.org/fhir/ValueSet/procedure-code | example |  |
| ServiceRequest.subject | Reference | 1 | 1 | N/A | N/A | true | N/A | N/A | N/A |
