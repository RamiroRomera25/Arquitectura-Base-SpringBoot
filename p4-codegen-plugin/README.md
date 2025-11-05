# p4-codegen-plugin v0.2.0

Generador local de capas (Entity, DTO, Repository, Service, Controller, Translator) con convenciones de Arquitectura Base.

## Uso

Desde el proyecto consumidor:

```bash
mvn ar.edu.utn.frc.tup.p4:p4-codegen-plugin:0.2.0:generate-layer   -Dname=Person   -Dtype=Named   -DbasePackage=ar.edu.utn.frc.tup.p4   -DwithTests=true
```
