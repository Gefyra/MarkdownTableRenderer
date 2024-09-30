| Variable | Value |
|----------|-------|
| Name     | StfGemeindekennzahlExtension |
| URL      | http://gematik.de/fhir/oegd/stf/StructureDefinition/StfGemeindekennzahlExtension |
| Type     | Extension |


| ID        | Type      | Min  | Max  | Pattern   | Fixed    | must-support| VS-Url      | Strength    | VS Concepts |
|-----------|-----------|------|------|-----------|----------|-------------|-------------|-------------|-------------|
| Extension |  | 0 | * | N/A | N/A | false | N/A | N/A | N/A |
| Extension.extension | Extension | 4 | * | N/A | N/A | false | N/A | N/A | N/A |
| Extension.extension:Bundesland | Extension | 1 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Extension.extension:Bundesland.extension | N/A | 0 | 0 | N/A | N/A | false | N/A | N/A | N/A |
| Extension.extension:Bundesland.url | http://hl7.org/fhirpath/System.String | 1 | 1 | N/A | Bundesland | false | N/A | N/A | N/A |
| Extension.extension:Bundesland.value[x] | integer | 0 | 1 | N/A | N/A | false | N/A | N/A | N/A |
| Extension.extension:Regierungsbezirk | Extension | 1 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Extension.extension:Regierungsbezirk.extension | N/A | 0 | 0 | N/A | N/A | false | N/A | N/A | N/A |
| Extension.extension:Regierungsbezirk.url | http://hl7.org/fhirpath/System.String | 1 | 1 | N/A | Regierungsbezirk | false | N/A | N/A | N/A |
| Extension.extension:Regierungsbezirk.value[x] | integer | 0 | 1 | N/A | N/A | false | N/A | N/A | N/A |
| Extension.extension:Landkreis | Extension | 1 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Extension.extension:Landkreis.extension | N/A | 0 | 0 | N/A | N/A | false | N/A | N/A | N/A |
| Extension.extension:Landkreis.url | http://hl7.org/fhirpath/System.String | 1 | 1 | N/A | Landkreis | false | N/A | N/A | N/A |
| Extension.extension:Landkreis.value[x] | integer | 0 | 1 | N/A | N/A | false | N/A | N/A | N/A |
| Extension.extension:Gemeinde | Extension | 1 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Extension.extension:Gemeinde.extension | N/A | 0 | 0 | N/A | N/A | false | N/A | N/A | N/A |
| Extension.extension:Gemeinde.url | http://hl7.org/fhirpath/System.String | 1 | 1 | N/A | Gemeinde | false | N/A | N/A | N/A |
| Extension.extension:Gemeinde.value[x] | integer | 0 | 1 | N/A | N/A | false | N/A | N/A | N/A |
| Extension.url | http://hl7.org/fhirpath/System.String | 1 | 1 | N/A | http://gematik.de/fhir/oegd/stf/StructureDefinition/StfGemeindekennzahlExtension | false | N/A | N/A | N/A |
| Extension.value[x] | N/A | 0 | 0 | N/A | N/A | false | N/A | N/A | N/A |
