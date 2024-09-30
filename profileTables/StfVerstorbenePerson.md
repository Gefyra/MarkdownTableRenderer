| Variable | Value |
|----------|-------|
| Name     | StfVerstorbenePerson |
| URL      | http://gematik.de/fhir/oegd/stf/StructureDefinition/StfVerstorbenePerson |
| Type     | Patient |


| ID        | Type      | Min  | Max  | Pattern   | Fixed    | must-support| VS-Url      | Strength    | VS Concepts |
|-----------|-----------|------|------|-----------|----------|-------------|-------------|-------------|-------------|
| Patient.identifier | Identifier | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
| Patient.identifier:ID-Gesundheitsamt | Identifier | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Patient.name | HumanName | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
| Patient.name:name | HumanName | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Patient.name:name.use | code | 1 | 1 | official | N/A | false | http://hl7.org/fhir/ValueSet/name-use | required | Temp, Name changed for Marriage, Usual, Nickname, Official, Anonymous, Old |
| Patient.name:name.family | string | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Patient.name:name.given | string | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
| Patient.name:name.prefix | string | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
| Patient.name:geburtsname | HumanName | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Patient.name:geburtsname.use | code | 1 | 1 | maiden | N/A | false | http://hl7.org/fhir/ValueSet/name-use | required | Temp, Name changed for Marriage, Usual, Nickname, Official, Anonymous, Old |
| Patient.name:geburtsname.family | string | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Patient.name:geburtsname.given | N/A | 0 | 0 | N/A | N/A | false | N/A | N/A | N/A |
| Patient.name:geburtsname.prefix | N/A | 0 | 0 | N/A | N/A | false | N/A | N/A | N/A |
| Patient.gender | code | 0 | 1 | N/A | N/A | true | http://hl7.org/fhir/ValueSet/administrative-gender | required | Other, Male, Female, Unknown |
| Patient.birthDate | date | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Patient.deceased[x] | boolean, dateTime | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Patient.deceased[x]:deceasedDateTime | dateTime | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Patient.address | Address | 0 | * | N/A | N/A | false | N/A | N/A | N/A |
| Patient.address:Strassenanschrift | Address | 0 | * | N/A | N/A | true | N/A | N/A | N/A |
| Patient.address:Strassenanschrift.extension:GKZ | Extension | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Patient.address:Strassenanschrift.type | code | 1 | 1 | both | N/A | false | http://hl7.org/fhir/ValueSet/address-type | required | Postal & Physical, Postal, Physical |
| Patient.address:Strassenanschrift.line | string | 0 | 3 | N/A | N/A | true | N/A | N/A | N/A |
| Patient.address:Strassenanschrift.line.extension:Strasse | Extension | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Patient.address:Strassenanschrift.line.extension:Hausnummer | Extension | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Patient.address:Strassenanschrift.line.extension:Adresszusatz | Extension | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Patient.address:Strassenanschrift.line.extension:Postfach | N/A | 0 | 0 | N/A | N/A | false | N/A | N/A | N/A |
| Patient.address:Strassenanschrift.city | string | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Patient.address:Strassenanschrift.postalCode | string | 0 | 1 | N/A | N/A | true | N/A | N/A | N/A |
| Patient.address:Strassenanschrift.country | string | 0 | 1 | N/A | N/A | true | http://hl7.org/fhir/ValueSet/iso3166-1-2 | preferred | Heard Island and McDonald Islands, Honduras, Hong Kong, Hungary, Haiti, Yemen, Croatia, Guyana, Guinea-Bissau, Greenland, ... |
