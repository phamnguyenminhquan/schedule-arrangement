# Notes

## Models

- Location: `./src/main/models`
- Description: Define main models of the project.
- `attributes` is defined with `final` keyword to guarantee immutability.
- `throws Exception` in `constructor` to validate input datas.

### Slot

- Represent a specific class time. E.g: Mon 7h30 - 9h30
- Attributes: `dayOfWeek`, `startTime`, `endTime`
- Main methods:
  - `conflictsWith(other)` check if `this` conflicts with `other` **Slot**.

### Section

- Represent a group of **Slot** of a **Subject**.
- Attributes: `id`, `List<Slot>`
- Main methods:
  - `conflictsWith(other)` check if `this` contains any **Slot** that conflicts with any **Slot** of `other` **Section**.
  - `addSlot(newSlot)` add **newSlot** into `List<Slot>` (Every **Slot** is non-conflicted with each other).

### Subject

- Attributes: `id`, `name`, `List<Section>`
- For each **Subject**, student is only allowed to choose 1 **Section**.
- Main methods:
  - `addSection(newSection)` add **newSection** into `List<Section` (**Sections** in **Subject** can be conflicted).