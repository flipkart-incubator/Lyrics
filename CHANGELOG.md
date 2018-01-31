## 0.6.0 (January 31, 2018)
### CORE
- Allowing control over 'visible' property while using sub-types

## 0.5.0 (October 13, 2017)
### CORE
- Refactored JavaPoet out of core, created a new module for Java
- Added support for adding modules in other languages

## 0.4.0 (June 22, 2017)
### CORE
- Added support for user handling of additional properties in TypeModel
- Added support for replacing a Field that is being constructed based on user defined logic

## 0.3.0  (May 24, 2017)
### CORE
- Added support for user handling of additional properties in FieldModel

## 0.2.0  (May 18, 2017)
### CORE
- Added support for field initializations
- Added a deprecated rule for types and fields
- Added an option to generate constructor for required fields
- Divided enums into two entities, enums and enums-with-fields
- Added support for creating a constructor that initializes required plus defined fields
- Added support for using an existing property as the sub-types field
- Added support for defining a serialization order for class properties

### ANDROID
- Removed option to specify which enums to create StringDefs for
- Create StringDefs for all enums including enums with fields

## 0.1.0
- First release