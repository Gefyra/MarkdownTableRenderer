| Variable | Value |
|----------|-------|
| Name     | StfBasisObservation |
| URL      | http://gematik.de/fhir/oegd/stf/StructureDefinition/StfBasisObservation |
| Type     | Observation |


| ID        | Type      | Min  | Max  | Pattern   | Fixed    | must-support| VS-Url      | Strength    | VS Concepts |
|-----------|-----------|------|------|-----------|----------|-------------|-------------|-------------|-------------|
| Observation.status | code | 1 | 1 | N/A | N/A | true | http://hl7.org/fhir/ValueSet/observation-status | required | Final, Registered, Cancelled, Amended, Entered in Error, Preliminary, Corrected, Unknown |
| Observation.code | CodeableConcept | 1 | 1 | N/A | N/A | true | http://gematik.de/fhir/oegd/stf/ValueSet/StfObservationCodes | required | Tumor, Nicht natürlicher Tod, Klassifikation der Todesursache |
| Observation.subject | Reference | 1 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Observation.effective[x] | dateTime, Period, Timing, instant | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Observation.effective[x]:effectiveDateTime | dateTime | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Observation.value[x] | Quantity, CodeableConcept, string, boolean, integer, Range, Ratio, SampledData, time, dateTime, ... | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Observation.note | Annotation | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
| Observation.hasMember | Reference | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
