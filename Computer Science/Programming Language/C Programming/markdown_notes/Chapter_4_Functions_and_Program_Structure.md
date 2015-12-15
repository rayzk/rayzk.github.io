# Chapter 4 Functions and Program Structure

@(The C Programming Language)[C|Book Review]

--------------------------------------------------

[TOC]

--------------------------------------------------

### 4.1 Basic of Functions
If the return type of a function is omitted, `int` is assumed

**Multiple Program Compilation**
See [Makefile Tutorial][1]

### 4.2 Functions Returning Non-integer

* calling routine must know the return type of the function, `declaration` of the function explicitly in the calling routine can ensure this
* otherwise, if calling routine and function are compiled separately, the error will not be detected
* first appearance of a function `sum += atof(line)` is declared by context to be a function name, and assumed to return int, nothing is assumed about its arguments
* if a `function declaration` does not include arguments, nothing is assumed

**proper declaration**

	int atoi (char s[]){
		double atof(char s[]);
		return (int) atof(s);
	}
	
###4.3 External Variables

* `internal` arguments and vars defined inside functions
* `external var` are defined outside of any functions, and potentially available to many functions
* `function` are always external (C does not allow function defined inside function)
* `external linkage` external vars and functions are referenced by the same name
* `external var` are more convenient and efficient than long argument lists
* `internal var` is created when entering function and destroyed when leaving function

###4.4 Scope Rules

* `scope` of a name is the part of the program within which the name can be used
* scope of an `automatic var` declared at the beginning of a function is the function in which the name is used
* `parameter` of the function is local variables 
* `local var` are not related (although might have the same name)
* `declaration` announces the properties of a variable (primarily its type)
* `definition` causes storage to be set aside

**example of declare/define**

	// declare and define val 
	double val[MAXVAL]
	// size must be specific with the definition of array
	
	// declare, but no space allocated
	extern double val[]
	
* there must be only one `definition` of an external var among all the files that makes up the source program

###4.5 Header Files

* centralize the `definitions and declarations` shared among files in one header file

**examples of header file for calculator**

*

###4.6 Static Variables

**example of static prefix**

	static char buf[BUFSIZE];
	static int bufp = 0;
	
	int getch(void){ ... }
	int ungetch(int c){ ... }
	
* `static` declaration, applied to an `external var` or `function` limit the scope of that object to the rest of the source files being compiled
* `external` makes `buf` and `bump` shared among `getch()` and `ungetch()`
* `static` makes them only accessible for `getch()` and `ungetch()`
* `static` function is invisible outside of the file in which it is declared
* `internal static var` provide private, permanent storage within a single function

###4.7 Register Variables

**example of register declaration**

	register int x;
	register char c;
	fn(register unsigned m, register long n);
	
* compiler are free to ignore the advice
* `register` can only be applied to automatic variables and to the formal parameter of function
* not able to take the address of the `register var`

###4.8 Block Structure

**example of block parameter**

	int x;
	int y;
	
	f(double x){
		double y;
	}

* vars can be `declared` and `defined` in any compound statement. var declared in this way hide any identically named vars in `outer block` and remain existence until matching the right brace
* var declared in a block is `initialized` each time entering the block

###4.9 Initialization

* in the absence of explicit init, `external` and `static` vars are guaranteed to be init to `zero`; `automatic` and `register` vars have `undefined` init value
* for `external` and `static` vars, initializer must be a constant expression
* for `automatic` and `register` vars, initializer is not restricted to be constant

**example of initializer of array**

	// compiler will compute the length by counting the initializer
	int days[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }
	
	char pattern = "ould";
	
###4.10 Recursion

* each invocation of function gets a fresh set of all the `automatic` vars

###4.11 The C Preprocessor

**File inclusion**

* handle `define` and `include` 
* when an include file is changed, all file that depends on it must be recompiled 

**Macro substitution**

	// form :
	#define name replacement text
	
	// example :
	#define forever for (;;)	/* infinite loop */
	
	// macro with arguments :
	#define max(A, B) ((A)>(B) ? (A) : (B))
	
	// undefine :
	#undef getchar
	int getchar(void) { ... } 
	
* placing `\` at end of the line to continue `replacement text` to next line
* the `scope` of `#define` is from its point of definition to the end of source file being compiled 
* `max(i++, j++)` : the expression will be evaluated twice
* in `<stdio.h>`, `getchar()` and `putchar()` are defined as `macro`s to avoid the run-time overhead of function call
* `#undef` to ensure that a routine is really a function, not a macro
* if a parameter name is preceded by a `#` in the replacement text, the combination will be expanded into a quoted string with the parameter replaced by the actual argument, and the string is concatenated

**Example of #parameter** 

	#define debugprint(expire) printf(#expr " = %g\n", expr)
	
	// is expanded to :
	printf ("para = %g\n", para);
	
* operator `##` provides a way to concatenate `arguments' name` during macro expansion, to create new `token`

**Example of ##toke concatenation**
	
	// concatenated two parameter :
	#define paste(front, back) front ## back
	paste(name, 1) // gives new token name1
	
**Conditional Inclusion**

	#if !defined(HDR)
	#define HDR
	
	// content of HDR.h is here
	
	#endif
	
	/* tests system version : /
	#if SYSTEM == SYSV
		#define HDR "sysv.h"
	#elif SYSTEM == BSD
		#define HDR "bsd.h"
	#elif SYSTEM == MSDOS
		#deine HDR "msdos.h"
	#else 
		#define HDR "default.h"	
	#endif
	#include HDR
	
* `ifdef` and `ifndef` are specialized form to test whether a name is defined
* conditional statements are evaluated during `preprocessing`

<!-- link to makefile tutorial -->
[1]:http:






