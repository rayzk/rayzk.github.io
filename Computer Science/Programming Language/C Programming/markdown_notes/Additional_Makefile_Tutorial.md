# Makefile Tutorial

@(The C Programming Language)[Additional Material|C|Book Review]

--------------------------------------------------

[TOC]

--------------------------------------------------

### 1. Rule Introduction
**Sample Makefile**
```makefile
# comments
target ... : prerequisites
	recipe
	...
```
- `target` : name of file that generated
- `prerequisites` : file used as input to create the target file
- `recipe` : actions that make carries out

> Put a `tab` character at the beginning of every recipe line.
> Reset the `.RECIPEPREFIX` variable can allow you to prefix the recipe with a character other than tab. For more variables in makefile, refer to [Special Variables][1]
> Recipe will create a target file if any of the prerequisites changed


### 2. Makefile
**Simple Example**
```makefile
# one executable file : edit depends on eight object file
edit : main.o kbd.o command.o display.o \
       insert.o search.o files.o utils.o
        cc -o edit main.o kbd.o command.o display.o \
                   insert.o search.o files.o utils.o

main.o : main.c defs.h
        cc -c main.c
kbd.o : kbd.c defs.h command.h
        cc -c kbd.c
command.o : command.c defs.h command.h
        cc -c command.c
display.o : display.c defs.h buffer.h
        cc -c display.c
insert.o : insert.c defs.h buffer.h
        cc -c insert.c
search.o : search.c defs.h buffer.h
        cc -c search.c
files.o : files.c defs.h buffer.h command.h
        cc -c files.c
utils.o : utils.c defs.h
        cc -c utils.c
clean :
        rm edit main.o kbd.o command.o display.o \
           insert.o search.o files.o utils.o
```

**Comments**
- `#` and the rest of the line will be ignored. If you want a literal #, use `/` to escape it.
- `#` cannot be used within **variable reference** and **function calls**
- Comments within a recipe are passed to the shell
- Comments are not ignored within a `define` directive, during the definition of the variable. When the variable expanded, they will either be comments or recipe text.

**Splitting a Long Line**
- Use `/` to escape the newline character

**Name of Makefile**
- *make* command looks for names in order : `GNUmakefile`, `makefile`, `Makefile`
- `Makefile` is recommended since it near files like `README`

**`include` Directive**
```makefile
include *.md $(bar)
```
- The first character must not be `.RECIPEPREFIX`, otherwise it will be taken as a recipe line. (`recipe` has higher order then `include`)
- Variables will be expended in the include
- Files in the `include` line will be executed by make, when make process execute that `include` line
- If the file is not found, directories in `-I` option will be searched. Refer to [Make Options][5]
- `-include` will let the Make ignore a makefile which does not exist or cannot be remade



- Command *make* execute the recipe when the target file needs to be updated; **Make** does not know anything about the recipes
- Target `clean` is not a file (since it is not a prerequisite of any other rule). Thus *make* does not execute the recipe under `clean`, unless command *make clean* is called




### 3. Process a Makefile
- *make* start with the first `target` (not the target start with `.`)
- For each rule, *make* check whether the `target` is more recent then the `prerequisite` files. If not, *make* execute this rule. 
- If the rule is not depended on by the `goal`, it is not processed, unless with a make command
- 

> **`Default Goal`** : is the `target` that *make* strives to update (like `edit` in the previous example). Default goal can be override by setting the [special variable][1] `.DEFAULT_GOAL`. 


### 4. Variables
Variables allow a **text string** to be defined once and substituted in multiple places
```makefile
objects = main.o kbd.o command.o display.o \
          insert.o search.o files.o utils.o

edit : $(objects)
        cc -o edit $(objects)
main.o : main.c defs.h
        cc -c main.c
kbd.o : kbd.c defs.h command.h
        cc -c kbd.c
command.o : command.c defs.h command.h
        cc -c command.c
display.o : display.c defs.h buffer.h
        cc -c display.c
insert.o : insert.c defs.h buffer.h
        cc -c insert.c
search.o : search.c defs.h buffer.h
        cc -c search.c
files.o : files.c defs.h buffer.h command.h
        cc -c files.c
utils.o : utils.c defs.h
        cc -c utils.c
clean :
        rm edit $(objects)
```

> Refer to [Variables][2] 

**Make Deduces**
- Make has [implicit rules][3] to compile `.c` file into correspondingly named `.o` file. It will use *cc -c main.c -o main.o*
- When a `.c` file is compiled automatically, it is also automatically added to the list of prerequisites. 

```makefile
objects = main.o kbd.o command.o display.o \
          insert.o search.o files.o utils.o

edit : $(objects)
        cc -o edit $(objects)

main.o : defs.h
kbd.o : defs.h command.h
command.o : defs.h command.h
display.o : defs.h buffer.h
insert.o : defs.h buffer.h
search.o : defs.h buffer.h
files.o : defs.h buffer.h command.h
utils.o : defs.h

.PHONY : clean
clean :
        rm edit $(objects)
```

> **`clean`** is a phony target. It will not work well if a file named clean is created in the directory. Since it does not have prerequisite, it is always up to date. Thus *make clean* will not execute the recipes under clean. 
> Explicitly declare `clean` to be phony by making it a prerequisite of the [special target][3] `.PHONY`


### 5. Pattern Rules
**Pattern rule**
- `%` in the target, matches any nonempty substring. The substring that `%` matches is called `stem`
- `%` in one prerequisite rule stands for the same `stem`

**Examples**
```makefile
# compile all file end with .c
%.o : %.c
	$(cc) -c $(CFLAGES) $< -o $@
```

**Automatic Variables**




### Reference 
[GUN make *An Introduction to Makefiles*](http://www.gnu.org/software/make/manual/make.html#Introduction)

<!-- GUN make / special variables -->
[1]: http://www.gnu.org/software/make/manual/make.html#Special-Variables

<!-- GUN make / variables -->
[2]: http://www.gnu.org/software/make/manual/make.html#Using-Variables

<!-- GUN make / implicit rules -->
[3]: http://www.gnu.org/software/make/manual/make.html#Implicit-Rules

<!-- GUN make / special targets -->
[4]: http://www.gnu.org/software/make/manual/make.html#Special-Targets

<!-- GUN make / options -->
[5]: https://www.gnu.org/software/make/manual/make.html#Options-Summary