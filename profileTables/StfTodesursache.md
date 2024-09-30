| Variable | Value |
|----------|-------|
| Name     | StfTodesursache |
| URL      | http://gematik.de/fhir/oegd/stf/StructureDefinition/StfTodesursache |
| Type     | Observation |


| ID        | Type      | Min  | Max  | Pattern   | Fixed    | must-support| VS-Url      | Strength    | VS Concepts |
|-----------|-----------|------|------|-----------|----------|-------------|-------------|-------------|-------------|
| Observation.code.coding | Coding | 0 | * | {"system":"http://snomed.info/sct","code":"184305005"} | N/A | false | N/A | N/A | N/A |
| Observation.effective[x] | dateTime | 0 | 1 | N/A | N/A | false | N/A | N/A | N/A |
| Observation.effective[x]:effectiveDateTime | dateTime | 0 | 1 | N/A | N/A | false | N/A | N/A | N/A |
| Observation.value[x] | CodeableConcept | 0 | 1 | N/A | N/A | false | http://hl7.org/fhir/sid/icd-10 | required |  |
| Observation.value[x].coding.system | uri | 1 | 1 | http://fhir.de/CodeSystem/bfarm/icd-10-gm | N/A | true | N/A | N/A | N/A |
| Observation.value[x].coding.version | string | 1 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Observation.value[x].coding.code | code | 1 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Observation.value[x].coding.display | string | 1 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Observation.dataAbsentReason | CodeableConcept | 0 | 1 | N/A | N/A | true | http://hl7.org/fhir/ValueSet/data-absent-reason | extensible | Temporarily Unknown, Positive Infinity (PINF), Unknown, Error, Unsupported, Not a Number (NaN), Not Permitted, Not Applicable, Asked But Unknown, Asked But Declined, ... |
| Observation.note | Annotation | 0 | * | N/A | N/A | false | N/A | N/A | N/A |
| Observation.hasMember | Reference | 0 | * | N/A | N/A | false | N/A | N/A | N/A |
| Observation.hasMember.extension | Extension | 1 | * | N/A | N/A | false | N/A | N/A | N/A |
| Observation.hasMember.extension:TodesursacheTyp | Extension | 1 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Observation.hasMember.reference | string | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Observation.component | BackboneElement | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
| Observation.component.code | CodeableConcept | 1 | 1 | N/A | N/A | true | http://gematik.de/fhir/oegd/stf/ValueSet/StfObservationCodes | extensible | Tumor, Nicht natürlicher Tod, Klassifikation der Todesursache |
| Observation.component.value[x] | Quantity, CodeableConcept, string, boolean, integer, Range, Ratio, SampledData, time, dateTime, ... | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Observation.component.value[x]:valueCodeableConcept | CodeableConcept | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Observation.component:NichtNatuerlicherTod | BackboneElement | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Observation.component:NichtNatuerlicherTod.code | CodeableConcept | 1 | 1 | {"coding":[{"system":"http://gematik.de/fhir/oegd/stf/CodeSystem/StfObservationCodesErweiterungCS","code":"nichtNatuerlicherTod"}]} | N/A | false | http://gematik.de/fhir/oegd/stf/ValueSet/StfObservationCodes | extensible | Tumor, Nicht natürlicher Tod, Klassifikation der Todesursache |
| Observation.component:NichtNatuerlicherTod.value[x]:valueCodeableConcept | CodeableConcept | 0 | 1 | N/A | N/A | false | http://gematik.de/fhir/oegd/stf/ValueSet/StfJaNeinUnbekanntVS | required | Unknown, Ja, Nein |
| Observation.component:Details | BackboneElement | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Observation.component:Details.code | CodeableConcept | 1 | 1 | {"coding":[{"system":"http://gematik.de/fhir/oegd/stf/CodeSystem/StfObservationCodesErweiterungCS","code":"todesursacheKlassifikation"}]} | N/A | false | http://gematik.de/fhir/oegd/stf/ValueSet/StfObservationCodes | extensible | Tumor, Nicht natürlicher Tod, Klassifikation der Todesursache |
| Observation.component:Details.value[x]:valueCodeableConcept | CodeableConcept | 0 | 1 | N/A | N/A | false | http://hl7.org/fhir/sid/icd-10 | required |  |
