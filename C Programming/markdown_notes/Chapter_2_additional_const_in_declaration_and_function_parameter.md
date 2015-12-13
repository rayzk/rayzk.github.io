# `const` in declaration and function parameter

@(The C Programming Language)[Chapter 2|Additional Material|C|Book Review]

--------------------------------------------------

[TOC]

--------------------------------------------------

### Example
``` cpp
int strlen (const char[])
```

### Reading Backward Trick
``` cpp
const int a = 1; // read as "a is an integer which is constant"
int const a = 1; // read as "a is a constant integer"
// They have the same meaning, thus
a = 2; // cannot work !
```

``` cpp
const char *s; // read as "s is a pointer to a char that is constant"
char c;
char *const t = &c; // read as "t is a constant pointer to a char"

*s = 'A'; // Can't do because the char is constant
s++;      // Can do because the pointer isn't constant
*t = 'A'; // Can do because the char isn't constant
t++;      // Can't do because the pointer is constant
```

- [x] Finished