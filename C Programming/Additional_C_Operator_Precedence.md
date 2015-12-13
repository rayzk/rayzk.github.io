# C Operator Precedence

@(The C Programming Language)[Additional Material|C|Book Review]

--------------------------------------------------

[TOC]

--------------------------------------------------

### Precedence 1
- `::` : Scoping operator

### Precedence 2
- `()` : grouping operator
- `[]` : array access
- `_>` : member access from a pointer
- `.` : member access from an object
- `++` : post-increment
- `--` : post-decrement

### Precedence 3
- `!` : logical negation
- `~` : bitwise complement
- `++` : pre-increment
- `--` : pre-decrement
- `-` : unary minus
- `+` : unary plus
- `*` : dereference
- `&` : address of
- `(type)` : cast to a given type
- `sizeof` : return size in bytes
 
### Precedence 4
- `_>*` : member pointer selector
- `.*` : member object selector

### Precedence 5
- `*` : multiplication
- `/` : division
- `%` : modulus

### Precedence 6
- `+` : addition
- `-` : subtraction

### Precedence 7
- `<<` : bitwise shift left
- `>>` : bitwise shift right

### Precedence 8
- `<` : comparison less-then
- `<=` : comparison less-then-or-equal-to
- `>` : comparison greater-then
- `>=` : comparison greater-then-or-equal-to





### Reference
[C Reference](https://cs50.harvard.edu/resources/cppreference.com/operator_precedence.html)

- [x] Finished