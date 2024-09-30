| Variable | Value |
|----------|-------|
| Name     | StfArzt |
| URL      | http://gematik.de/fhir/oegd/stf/StructureDefinition/StfArzt |
| Type     | Practitioner |


| ID        | Type      | Min  | Max  | Pattern   | Fixed    | must-support| VS-Url      | Strength    | VS Concepts |
|-----------|-----------|------|------|-----------|----------|-------------|-------------|-------------|-------------|
| Practitioner.identifier | Identifier | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.identifier:ID | Identifier | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.identifier:ID.system | uri | 1 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.identifier:ID.value | string | 1 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.identifier:ANR | Identifier | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.identifier:ANR.type | CodeableConcept | 1 | 1 | N/A | N/A | true | http://fhir.de/ValueSet/identifier-type-de-basis | extensible |  |
| Practitioner.identifier:ANR.type.coding | Coding | 1 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.identifier:ANR.type.coding.system | uri | 1 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.identifier:ANR.type.coding.code | code | 1 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.identifier:Zulassungsnummer | Identifier | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.identifier:Zulassungsnummer.system | uri | 1 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.identifier:Zulassungsnummer.value | string | 1 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.name | HumanName | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.name:name | HumanName | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.name:name.use | code | 1 | 1 | official | N/A | false | http://hl7.org/fhir/ValueSet/name-use | required | Temp, Name changed for Marriage, Usual, Nickname, Official, Anonymous, Old |
| Practitioner.name:name.text | string | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.name:name.family | string | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.name:name.given | string | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.name:name.prefix | string | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.telecom | ContactPoint | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.telecom:Telefon | ContactPoint | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.telecom:Telefon.system | code | 1 | 1 | phone | N/A | false | http://hl7.org/fhir/ValueSet/contact-point-system | required | SMS, Phone, Email, Fax, URL, Other, Pager |
| Practitioner.telecom:Telefon.value | string | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.telecom:Telefax | ContactPoint | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.telecom:Telefax.system | code | 1 | 1 | fax | N/A | false | http://hl7.org/fhir/ValueSet/contact-point-system | required | SMS, Phone, Email, Fax, URL, Other, Pager |
| Practitioner.telecom:Telefax.value | string | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.address | Address | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.address:Strassenanschrift | Address | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.address:Strassenanschrift.type | code | 1 | 1 | both | N/A | false | http://hl7.org/fhir/ValueSet/address-type | required | Postal & Physical, Postal, Physical |
| Practitioner.address:Strassenanschrift.line | string | 0 | 3 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.address:Strassenanschrift.line.extension:Strasse | Extension | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.address:Strassenanschrift.line.extension:Hausnummer | Extension | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.address:Strassenanschrift.line.extension:Adresszusatz | Extension | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.address:Strassenanschrift.line.extension:Postfach | N/A | 0 | 0 | N/A | N/A | false | N/A | N/A | N/A |
| Practitioner.address:Strassenanschrift.city | string | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.address:Strassenanschrift.postalCode | string | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.address:Strassenanschrift.country | string | 0 | 1 | N/A | N/A | true | http://hl7.org/fhir/ValueSet/iso3166-1-2 | preferred | Heard Island and McDonald Islands, Honduras, Hong Kong, Hungary, Haiti, Yemen, Croatia, Guyana, Guinea-Bissau, Greenland, ... |
| Practitioner.address:Postfach | Address | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.address:Postfach.type | code | 1 | 1 | postal | N/A | false | http://hl7.org/fhir/ValueSet/address-type | required | Postal & Physical, Postal, Physical |
| Practitioner.address:Postfach.line | string | 0 | 3 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.address:Postfach.line.extension:Strasse | N/A | 0 | 0 | N/A | N/A | false | N/A | N/A | N/A |
| Practitioner.address:Postfach.line.extension:Hausnummer | N/A | 0 | 0 | N/A | N/A | false | N/A | N/A | N/A |
| Practitioner.address:Postfach.line.extension:Adresszusatz | N/A | 0 | 0 | N/A | N/A | false | N/A | N/A | N/A |
| Practitioner.address:Postfach.line.extension:Postfach | Extension | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.address:Postfach.city | string | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.address:Postfach.postalCode | string | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.address:Postfach.country | string | 0 | 1 | N/A | N/A | true | http://hl7.org/fhir/ValueSet/iso3166-1-2 | preferred | Heard Island and McDonald Islands, Honduras, Hong Kong, Hungary, Haiti, Yemen, Croatia, Guyana, Guinea-Bissau, Greenland, ... |
| Practitioner.qualification | BackboneElement | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
| Practitioner.qualification.code | CodeableConcept | 1 | 1 | N/A | N/A | true | http://gematik.de/fhir/oegd/stf/ValueSet/StfBerufsgruppeVS | required | Sonstige, Niedergelassen, Rettungsdienst, ambulanter Notdienst, Krankenhaus |
