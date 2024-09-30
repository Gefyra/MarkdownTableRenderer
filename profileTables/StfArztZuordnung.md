| Variable | Value |
|----------|-------|
| Name     | StfArztZuordnung |
| URL      | http://gematik.de/fhir/oegd/stf/StructureDefinition/StfArztZuordnung |
| Type     | PractitionerRole |


| ID        | Type      | Min  | Max  | Pattern   | Fixed    | must-support| VS-Url      | Strength    | VS Concepts |
|-----------|-----------|------|------|-----------|----------|-------------|-------------|-------------|-------------|
| PractitionerRole.practitioner | Reference | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| PractitionerRole.organization | Reference | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| PractitionerRole.specialty | CodeableConcept | 0 | * | N/A | N/A | true | http://gematik.de/fhir/oegd/stf/ValueSet/StfFacharztgruppeVS | required | Notarzt/-ärztin, Sonstiges, diensthabender Arzt/Ärztin im Krankenhaus, Arzt/Ärztin des kassenärztlichen Notdienstes, Hausarzt/-ärztin, Rechtsmediziner/-in |
