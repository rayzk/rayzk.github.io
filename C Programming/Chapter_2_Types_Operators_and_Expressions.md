# Chapter 2 Types, Operators and Expressions

@(The C Programming Language)[C|Book Review]

--------------------------------------------------

[TOC]

--------------------------------------------------

### 2.1 Variable Names
**Restrictions on Variable Names**
- The first character must be a letter
- The first 31 characters of **internal names** are significant; **function names** and **external names** are less
- The standard guarantees **external names** for 6 characters

> The underscore `_` counts as a letter; library routines usually use the variables begin with underscore.
> **External names** and **Function names** has less then 31 significant characters since they may be used by **assemblers** and **loaders** which language has no control
> Tend to use short names for local variables, especially **loop indices** and longer names for external variables

<!---
More external links \ notes on `assemblers` and `loaders`.
-->

### 2.2 Data Types and Sizes
**Basic Data Types**
- `char` : single byte, hold one character in the [local character set](http://aboutc.weebly.com/c-character-set.html) 
- `int` : natural size of integers on the host machine
- `float` : single-precision floating point
- `double` : double-precision floating point

**Qualifiers**
- `short` and `long` : length of `short` < `int` < `long`
- `signed` and `unsigned` : applied to `char` and `int`. Unsigned numbers are always zero or positive and obey the laws of arithmetic modulo $2^n$. *(n is the number of bits in the type)*
- `long double`

> Each compiler is free to choose size for its own hardware
> Plain char are machine dependent; Printable chars are always positive
> [**<limits.h>**]() and [**<float.h>**]() contains these qualifiers 

<!---
Add links to the two libraries.
-->

### 2.3 Constants
**Representation in Constant Expression**
- Integer Constant :
	- Long constant : `L` or `l`
	- Unsigned constant : `U` or `u`

> The value of integer can be specified in **octal** or **decimal** or **hexadecimal**. **Octal** : leading `0`; **Hexadecimal** : leading `0X` or `0x`

- Floating point constant : 
	- Double constant : contains a decimal point(123.4) or exponent (le-2)
	- Float constant : `F` or `f`
	- Long Double constant : `L` or `l`
- Character Constant :
	- written as one character in single quotes
	- Character constants participate in numerical operations just as any other integers
- String Constant :
	- a sequence of zero or more character surrounded by double quotes

> **Constant Expression** (expression involves only constants) will be evaluated at compile time 
> String constant with double quotes has one more physical storage to hold `\0` in the end of the string.

**Escape Sequences**
`\ooo` : ooo is one to three octal digits (0-7)
`\xhh` : hh is one or more hexadecimal digit (0-9, a-f, A-F)

``` cpp
#define VTAB '\013'	/* ASCII vertical tab */
#define VTAB 'xb' 
```

**Complete Set of Escape Sequences**
| - | - | - | - |
| :-------- | :--------| :-------- | :--------|
|`\a`|Alert character|`\\`|Backslash|
|`\b`|Backspace|`\?`|Question mark|
|`\f`|Formfeed|`\'`|Single quote|
|`\n`|New line|`\"`|Double quote|
|`\r`|Carriage return|`\ooo`|Octal number|
|`\t`|Horizontal tab|`xhh`|Hexadecimal number|
|`\v`|Vertical tab|||

> `\0` represents the character with numerical value zero. the **null** character

**Enumeration Constant**
```cpp
enum boolean {NO, YES} /* NO has value 0; YES has value 1 */
enum escapes {BELL='\a', TAB='\t'}
enum months {JAN=1, FEB, MAR, APR} /* FEB has value 2 */
```

- if no values are specified, the first name in **enum** has value 0, the second has value 1 and so on
- if explicit values are specified, unspecified value continue the progression

### 2.4 Declaration
``` cpp
int lower, upper, step;
char c, line[100];
```

**Initialization**
- if the variable is not `automatic`, the initialization is done once only; the initializer must be a constant expression
- `automatic` variable is initialized each time the function or block it is in is entered; initializer can be any expression

> **Automatic variable** is local variable which is allocated and deallocated automatically when program flow enter and exit the variable's scope. In C and Cpp, called `automatic variable`; In Java, called `local variable`; In Perl, called `lexical`
> External and static variables are initialized to zero by default; Automatic variables have undefined values by default

**Qualifier `const`**
- In declaration, `const` indicates the variable will not be changed
- In function parameter, `const` array indicate array will not be changed by the function

``` cpp
int strlen (const char[])
```

More information of [Order of Multi-qualifiers][1]


### 2.5 Arithmetic Operators
See [C Operator Precedence][2]


### 2.6 Relational and Logical Operators
- Evaluation of expressions connected by `&&` or `||` stops as the truth or falsehood of the result is known
- Precedence of `&&` is higher then `||`, and both are lower then relational and equality operators


### 2.7 Type Conversions
**Conversion of Character to Integer**
**C** guarantees that any `char` in the machine's standard printing character set will never be negative, these `char` will always be positive in expression. But arbitrary bit patterns stored in characters variables can be negative *(leftmost bit is 1)*. Conversion of these `char` to `int` varies from machine to machine

> For portability, specify `signed` or `unsigned` if non-character data is to be stored in `char` variables

#### Implicit Type Conversion
> Compiler will perform implicit type conversion in following situation : 
> - Operants have mismatched types
> - Arguments mismatch with function parameter type
> - Value mismatch with declared type

**Hierarchy of Types' Conversion Rank**
When arithmetic operands have different types, the implicit type conversion is governed by the types' **conversion rank**
- Integer types :
	- any two **unsigned integer types** have different conversion ranks, the wider one has higher rank
	- each **signed integer types** has the same rank as the corresponding unsigned type. e.g., `char` == `signed char` == `unsigned char`
	- **Standard integer type order** : `bool` < `char` < `short` < `int` < `long` < `long long`
	- every **enumerated types** has the same rank as its corresponding integer type
- Floating-point types :
	- **Floating-point type order** : `float` < `double` < `long double`
	- `float` has higher rank than any integer type

**Integer Promotion**
Compiler applies integer promotion : any operand whose type ranks lower than `int` is automatically converted to `int` or `unsigned int`

``` cpp
// Example of Integer Promotion (to int)
char c = '?';
if (c < 'A')
// 'A' as a const char has type int, thus c is implicitly promoted to int for the comparison

// Example of Integer Promotion (to unsigned int)
unsigned short var = 100;
var = var + 1;
// if the machine is 32-bit, before the addition, the value of var is promoted to int which is wider than unsigned short
// if the machine is 16-bit, hence the signed type int is not wide enough to hold all possible values of unsigned short. Therefore, var is promoted to unsigned int
```

**Arithmetic Conversion**
- Conversion performed for operators :
	- **Arithmetic operator** : `*`, `/`, `%`, `+`, `-`
	- **Relational/Equality operator** : `<`, `<=`, `!=`, ...
	- **Bitwise operator** : `&`, `|`, `^`
	- **Conditional operator** : `?`, `:`
- If either operand has **floating-point type** :
	- lower **conversion rank** is converted to the higher one
- If both have **Integer type** :
	- **Integer promotion** is first performed on both operands, if still have different types, continue ...
	- If the higher one has `unsigned type` `T`, then the other one is converted to type `T`
	- If the higher one has `signed type` `T`, then the other one is converted to type `T` only if `T` is capable of representing all values of its original type. If not, both are converted to `unsigned T`

**Other Implicit Type Conversion**
- In **assignment/initialization**, right operand is always converted to left operand
- In **function call**, arguments are converted to types of parameters
- In **return statement**, return expression is converted to function return type

``` cpp
// Example of Implicit Type Conversion
float x = 0.5; // constant value is converted from double to float
int i = 7;
i = x; // the value of x is converted from float to int
x += 2.5; // before the addition, the value of x is converted to double; after the addition, the sum is converted to float and assigned to float x
long my_func (){
	return 0; // constant 0 is converted to long
}
```

#### Non-arithmetic Type Conversion
See [Nonarithmetic Type Conversion][1]

#### Explicit Type Conversion
``` cpp
sqrt ((double) n)
```
See [C Operator Precedence][2]


### 2.8 Increment and Decrement Operators
`++n` : increment n before the value is used
`n++` : increment n after the value is used


### 2.9 Bitwise Operators 
- AND operator `&` : used to mask off some bits
- inclusive OR `|` : used to turn bits on
- exclusive OR `^` : indicate different bits
- shift operator `<<`, `>>` : 
	- left shift fit vacate with 0
	- right shift a unsigned type fit vacate with 0
	- right shift a signed type fit vacate with sign-bit (in **arithmetic shift**), 0-bit (in **logical shift**)
- one's complement operator `~` : 1-bit to 0-bit and vice versa



--------------------------------------------------
<!-- const_in_declaration_and_function_parameter -->
[1]: http://

<!-- c_operator_precedence -->
[2]: http://

<!-- nonarithmetic_type_conversion -->
[3]: http://


- [ ] Finished
<!-- only the link is left -->