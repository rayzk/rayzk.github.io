# Nonarithmetic Type Conversion

@(The C Programming Language)[Chapter 2|Additional Material|C|Book Review]

--------------------------------------------------

[TOC]

--------------------------------------------------

**Pointer** and the **names of arrays** and **functions** are also subject to certain implicit/explicit type conversion. **Structures** and **unions** cannot be converted, although pointers to them can be converted to other pointer types

### Array and Function Designators
The array expression is not converted to a pointer in following cases :
- the array is operand of `sizeof` operator
- the array is operand of address `&` operator
- string literal is used to initialize an array of `char`

``` cpp
// Example of converting array designators into pointers
int *ptr = 0; // pointer to int, initialized with 0
int array[] = {0, 10, 20}; // array of int
int array_len = sizeof(array)/sizeof(int); // 3, sizeof yields the size of the whole array, not the pointer
printf ("the array starts at address %p.\n", array); // array designator is converted to pointer
*array = 5; // same as array[0] = 5, as operand of *, array designator is converted to pointer
```

``` cpp
// Example of converting function designator into pointers
void func0 (){puts("this is function 0");}
void func1 (){puts("this is function 1");}
void (*funcTable[2])(void) = {func0, func1}; // array of two pointers to functions returning void

```


- [x] Finished