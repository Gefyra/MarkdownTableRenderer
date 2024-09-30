| Variable | Value |
|----------|-------|
| Name     | StfSterbeurkundeAusstellung |
| URL      | http://gematik.de/fhir/oegd/stf/StructureDefinition/StfSterbeurkundeAusstellung |
| Type     | Provenance |


| ID        | Type      | Min  | Max  | Pattern   | Fixed    | must-support| VS-Url      | Strength    | VS Concepts |
|-----------|-----------|------|------|-----------|----------|-------------|-------------|-------------|-------------|
| Provenance.target | Reference | 1 | * | N/A | N/A | false | N/A | N/A | N/A |
| Provenance.recorded | instant | 1 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Provenance.reason | CodeableConcept | 0 | * | {"coding":[{"system":"http://terminology.hl7.org/CodeSystem/v3-ActReason","code":"DECSD"}]} | N/A | true | http://terminology.hl7.org/ValueSet/v3-PurposeOfUse | extensible | pharmacy supply request renewal refusal reason, admission to hospital, healthcare research, coverage authorization, legal, philosophical objection, entered in error, contraindication, disaster, ActCoverageReason, ... |
| Provenance.activity | CodeableConcept | 0 | 1 | {"coding":[{"system":"http://terminology.hl7.org/CodeSystem/v3-DocumentCompletion","code":"LA"}]} | N/A | true | http://hl7.org/fhir/ValueSet/provenance-activity-type | extensible | attender, analyte, primary performer, deidentify, admitter, callback contact, verifier, tracker, performer, distributor, ... |
| Provenance.agent | BackboneElement | 1 | * | N/A | N/A | true | N/A | N/A | N/A |
| Provenance.agent.who | Reference | 1 | 1 | N/A | N/A | true | N/A | N/A | N/A |
