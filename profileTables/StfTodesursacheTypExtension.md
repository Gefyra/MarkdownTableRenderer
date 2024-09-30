| Variable | Value |
|----------|-------|
| Name     | StfTodesursacheTypExtension |
| URL      | http://gematik.de/fhir/oegd/stf/StructureDefinition/StfTodesursacheTypExtension |
| Type     | Extension |


| ID        | Type      | Min  | Max  | Pattern   | Fixed    | must-support| VS-Url      | Strength    | VS Concepts |
|-----------|-----------|------|------|-----------|----------|-------------|-------------|-------------|-------------|
| Extension |  | 0 | * | N/A | N/A | false | N/A | N/A | N/A |
| Extension.extension | N/A | 0 | 0 | N/A | N/A | false | N/A | N/A | N/A |
| Extension.url | http://hl7.org/fhirpath/System.String | 1 | 1 | N/A | http://gematik.de/fhir/oegd/stf/StructureDefinition/StfTodesursacheTypExtension | false | N/A | N/A | N/A |
| Extension.value[x] | CodeableConcept | 0 | 1 | N/A | N/A | false | http://gematik.de/fhir/oegd/stf/ValueSet/StfTodesursacheCodeVS | extensible | Todesursache_4, Todesursache 3, Todesursache_5, Todesursache 2, Todesursache Gesundheitsamt |
