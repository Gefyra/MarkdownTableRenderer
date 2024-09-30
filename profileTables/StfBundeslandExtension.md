| Variable | Value |
|----------|-------|
| Name     | StfBundeslandExtension |
| URL      | http://gematik.de/fhir/oegd/stf/StructureDefinition/StfBundeslandExtension |
| Type     | Extension |


| ID        | Type      | Min  | Max  | Pattern   | Fixed    | must-support| VS-Url      | Strength    | VS Concepts |
|-----------|-----------|------|------|-----------|----------|-------------|-------------|-------------|-------------|
| Extension |  | 0 | * | N/A | N/A | false | N/A | N/A | N/A |
| Extension.extension | N/A | 0 | 0 | N/A | N/A | false | N/A | N/A | N/A |
| Extension.url | http://hl7.org/fhirpath/System.String | 1 | 1 | N/A | http://gematik.de/fhir/oegd/stf/StructureDefinition/StfBundeslandExtension | false | N/A | N/A | N/A |
| Extension.value[x] | Coding | 0 | 1 | N/A | N/A | false | http://fhir.de/ValueSet/iso/bundeslaender | required | Thüringen, Mecklenburg-Vorpommern, Nordrhein-Westfalen, Hessen, Rheinland-Pfalz, Brandenburg, Hamburg, Sachsen-Anhalt, Berlin, Baden-Württemberg, ... |
