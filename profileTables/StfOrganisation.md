| Variable | Value |
|----------|-------|
| Name     | StfOrganisation |
| URL      | http://gematik.de/fhir/oegd/stf/StructureDefinition/StfOrganisation |
| Type     | Organization |


| ID        | Type      | Min  | Max  | Pattern   | Fixed    | must-support| VS-Url      | Strength    | VS Concepts |
|-----------|-----------|------|------|-----------|----------|-------------|-------------|-------------|-------------|
| Organization.identifier | Identifier | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
| Organization.type | CodeableConcept | 0 | * | N/A | N/A | true | http://gematik.de/fhir/oegd/stf/ValueSet/StfOrganisationsTypVS | extensible | Praxis, Klinik, Institution, Gesundheitsamt, Meldende Stelle, Gerichtsmedizinisches Institut, Pathologisches Institut, Standesamt, Abteilung, Übermittelnde Stelle |
| Organization.name | string | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Organization.address | Address | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
| Organization.address.extension:Bundesland | Extension | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Organization.partOf | Reference | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Organization.contact | BackboneElement | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
| Organization.contact.name | HumanName | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
